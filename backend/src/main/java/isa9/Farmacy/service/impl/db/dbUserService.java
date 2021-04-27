package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.*;
import isa9.Farmacy.repository.DoctorRepository;
import isa9.Farmacy.repository.PatientRepository;
import isa9.Farmacy.repository.UserRepository;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.service.impl.base.UserServiceBase;
import isa9.Farmacy.support.PaginationSortSearchDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

@Component
@Primary
@Service
public class dbUserService extends UserServiceBase implements UserService, UserDetailsService {

    private UserRepository userRepository;
    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;


    @Autowired
    public dbUserService(UserRepository userRepository, PatientRepository p, DoctorRepository d) {
        this.userRepository = userRepository;
        this.patientRepository = p;
        this.doctorRepository = d;
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User findOne(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User entity) { return this.userRepository.save(entity); }

    @Override
    public boolean isAvaibleEmail(String em) {
        List<User> allUsers = this.findAll();

        for(User u : allUsers){
            if(u.getEmail().equals(em)) return false;
        }

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = null;

        List<User> allUsers = this.findAll();

        for(User u : allUsers){
            if(email.equals(u.getEmail())) user = u;
        }

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with e-mail '%s'.", email));
        } else {
            return user;
        }
    }

    @Override
    public PharmacyAdmin findPharmacyAdmin(Long pharmacyId) {
        PharmacyAdmin phAdmin = null;

        List<User> allUsers = this.findAll();
        for(User u : allUsers){
            if(u.getRole().getName().equals("PHARMACY_ADMIN")){
                phAdmin = (PharmacyAdmin) u;
                try{
                    if(phAdmin.getPharmacy().getId() == pharmacyId) return phAdmin;
                }catch(NullPointerException e){
                    continue;
                }
            }
        }
        phAdmin = null;
        return phAdmin;
    }

    @Override
    public List<Patient> getAllMyPatientsPaged(PaginationSortSearchDTO pssDTO)
    {
        Pageable paging;
        if (pssDTO.isAscending()){
            paging = PageRequest.of(pssDTO.getPageNo() - 1, pssDTO.getPageSize(), Sort.by(pssDTO.getSortBy()).ascending());
        } else {
            paging = PageRequest.of(pssDTO.getPageNo() - 1, pssDTO.getPageSize(), Sort.by(pssDTO.getSortBy()).descending());
        }

        Page<Patient> pagedResult = patientRepository.findPatientsByDoctorIdAndSearch(
                Long.valueOf(pssDTO.getSearchParams().get("doctorId")),
                pssDTO.getSearchParams().get("name"),
                pssDTO.getSearchParams().get("surname"), paging);//findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Patient>();
        }
    }

    @Override
    public long getAllMyPatientsTotalCount(PaginationSortSearchDTO pssDTO) {
        return patientRepository.countFoundPatientsByDoctorIdAndSearch(Long.valueOf(pssDTO.getSearchParams().get("doctorId")), pssDTO.getSearchParams().get("name"),
                pssDTO.getSearchParams().get("surname"));
    }

    @Override
    public Doctor getDoctorById(Long id) {
        return this.doctorRepository.findById(id).orElse(null);
    }

    @Override
    public Patient getPatientById(Long id) {
        return this.patientRepository.findById(id).orElse(null);
    }
}
