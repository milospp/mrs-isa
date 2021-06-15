package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.PatientIdLastDateDTO;
import isa9.Farmacy.model.dto.PatientLastAppointmentDTO;
import isa9.Farmacy.repository.DoctorRepository;
import isa9.Farmacy.repository.PatientRepository;
import isa9.Farmacy.repository.UserRepository;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.service.impl.base.UserServiceBase;
import isa9.Farmacy.support.PaginationSortSearchDTO;
import isa9.Farmacy.support.PatientsPagesDTO;
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
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

@Component
@Primary
@Service
public class dbUserService extends UserServiceBase implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;


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
    public boolean isAvaibleEmail(String em, Long id) {
        List<User> allUsers = this.findAll();

        for(User u : allUsers){
            if(u.getEmail().equals(em) && !u.getId().equals( id )) return false;
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
                    if(phAdmin.getPharmacy().getId().equals( pharmacyId )) return phAdmin;
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
    public User getByEmail(String email) {
        return userRepository.findFirstByEmail(email);
    }

    @Override
    public long getAllMyPatientsTotalCount(PaginationSortSearchDTO pssDTO) {
        return patientRepository.countFoundPatientsByDoctorIdAndSearch(Long.valueOf(pssDTO.getSearchParams().get("doctorId")), pssDTO.getSearchParams().get("name"),
                pssDTO.getSearchParams().get("surname"));
    }

    @Override
    public List<Patient> getPatientsByDoctorIdAndSearchAndSortByDateAsc(PaginationSortSearchDTO pssDTO) {
        Pageable p = PageRequest.of(pssDTO.getPageNo() - 1, pssDTO.getPageSize());
        return patientRepository.findAll();
    }

    @Override
    public PatientsPagesDTO getPatientLastAppointmentDTOsSortedSearched(PaginationSortSearchDTO pssDTO) {
        Sort.Direction d;
        if (pssDTO.isAscending()) d = Sort.Direction.ASC;
        else d = Sort.Direction.DESC;
        Pageable p = PageRequest.of(pssDTO.getPageNo() - 1, pssDTO.getPageSize(), d, pssDTO.getSortBy());
        Page<PatientIdLastDateDTO> pagedResult = patientRepository.findNotSorted(
                Long.valueOf(pssDTO.getSearchParams().get("doctorId")),
                pssDTO.getSearchParams().get("name"),
                pssDTO.getSearchParams().get("surname"),
                p
        );




        List<PatientLastAppointmentDTO> patientLastAppointmentDTOS = new ArrayList<PatientLastAppointmentDTO>();
        List<PatientIdLastDateDTO> tuples;
        if(pagedResult.hasContent()) {
            tuples = pagedResult.getContent();
            for (PatientIdLastDateDTO t : tuples) {
                System.out.println(t.getId());
                System.out.println(t.getLast());
                Patient patient = (Patient) this.findOne(t.getId());
                patientLastAppointmentDTOS.add(new PatientLastAppointmentDTO(patient, t.getLast()));

            }

        } else {
            patientLastAppointmentDTOS = new ArrayList<PatientLastAppointmentDTO>();
        }
        long count = patientRepository.findNotSortedCount(
                Long.valueOf(pssDTO.getSearchParams().get("doctorId")),
                pssDTO.getSearchParams().get("name"),
                pssDTO.getSearchParams().get("surname"));
        System.out.println("count: " + count);
        return new PatientsPagesDTO(count, patientLastAppointmentDTOS);
    }

    @Override
    public Doctor getDoctorById(Long id) {
        return this.doctorRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Doctor getDoctorByIdLocked(Long id) {
        return this.doctorRepository.findDoctorByIdLocked(id);
    }

    @Override
    public Patient getPatientById(Long id) {
        return this.patientRepository.findById(id).orElse(null);
    }

    @Override
    public Patient getPatientByIdLocked(Long id) {
        return this.patientRepository.findPatientByIdLocked(id);
    }

    @Override
    public List<Patient> howSucribePharmacy(Long pharmacyId) {
        List<Patient> pacijenti = new ArrayList<>();
        for (User korisnik : findAll()) {
            if (korisnik.getClass() == Patient.class) {
                Patient pacijent = (Patient) korisnik;
                for (Pharmacy apoteka : pacijent.getSubscriptions()) {
                    if (apoteka.getId().equals( pharmacyId )) {
                        pacijenti.add(pacijent);
                        break;
                    }
                }
            }
        }
        return pacijenti;
    }
}
