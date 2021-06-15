package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.Complaint;
import isa9.Farmacy.repository.ComplaintRepository;
import isa9.Farmacy.service.ComplaintService;
import isa9.Farmacy.service.impl.base.ComplaintServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Primary
@Service
public class dbComplaintService extends ComplaintServiceBase implements ComplaintService {

    private final ComplaintRepository complaintRepository;

    @Autowired
    public dbComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    @Override
    public List<Complaint> findAll() {
        return this.complaintRepository.findAll();
    }

    @Override
    //@Transactional
    public Complaint findOne(Long id) {
        return this.complaintRepository.findById(id).orElse(null);
    }

    @Override
    public Complaint save(Complaint entity) {
        return this.complaintRepository.save(entity);
    }

    @Override
    @Transactional
    public Complaint findOneLocked(Long complaintId) {
        return complaintRepository.findByIdLocked(complaintId);
    }
}
