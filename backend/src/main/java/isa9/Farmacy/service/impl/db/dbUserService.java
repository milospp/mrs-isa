package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.Patient;
import isa9.Farmacy.model.PharmacyAdmin;
import isa9.Farmacy.model.User;
import isa9.Farmacy.repository.PatientRepository;
import isa9.Farmacy.repository.UserRepository;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.service.impl.base.UserServiceBase;
import isa9.Farmacy.support.PaginationSortSearchDTO;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

@Primary
@Service
public class dbUserService extends UserServiceBase implements UserService {

    private UserRepository userRepository;
    private PatientRepository patientRepository;

    @Autowired
    public dbUserService(UserRepository userRepository, PatientRepository p) {
        this.userRepository = userRepository;
        this.patientRepository = p;
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User findOne(Long id) {
        return this.userRepository.findById(id).orElseGet(null);
    }

    @Override
    public User save(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public boolean isAvaibleEmail(String em) {
        return true;
//        throw new NotYetImplementedException();
//        return false;
    }

    @Override
    public PharmacyAdmin findPharmacyAdmin(Long pharmacyId) {
//        return true;
        System.out.println("FindPharmacyAdmin Not implemented");
        throw new NotYetImplementedException();
//        return null;
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

        Page<Patient> pagedResult = patientRepository.findByNameIgnoreCaseContainingAndSurnameIgnoreCaseContaining(pssDTO.getSearchParams().get("name"),
                pssDTO.getSearchParams().get("surname"), paging);//findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Patient>();
        }
    }

    @Override
    public long getAllMyPatientsTotalCount(PaginationSortSearchDTO pssDTO) {
        return patientRepository.countByNameIgnoreCaseContainingAndSurnameIgnoreCaseContaining(pssDTO.getSearchParams().get("name"),
                pssDTO.getSearchParams().get("surname"));
    }
}
