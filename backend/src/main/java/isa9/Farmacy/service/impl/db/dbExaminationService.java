package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.Appointment;
import isa9.Farmacy.model.Examination;
import isa9.Farmacy.model.ExaminationStatus;
import isa9.Farmacy.repository.AppointmentRepository;
import isa9.Farmacy.repository.ExaminationRepository;
import isa9.Farmacy.service.AppointmentService;
import isa9.Farmacy.service.ExaminationService;
import isa9.Farmacy.service.impl.base.AppointmentServiceBase;
import isa9.Farmacy.service.impl.base.ExaminationServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
@Service
public class dbExaminationService extends ExaminationServiceBase implements ExaminationService {

    private final ExaminationRepository examinationRepository;

    @Autowired
    public dbExaminationService(ExaminationRepository examinationRepository) {
        this.examinationRepository = examinationRepository;
    }

    @Override
    public List<Examination> findAll() {
        return this.examinationRepository.findAll();
    }

    @Override
    public Examination findOne(Long id) {
        return this.examinationRepository.findById(id).orElseGet(null);
    }

    @Override
    public Examination save(Examination entity) {
        return this.examinationRepository.save(entity);
    }


    @Override
    public List<Examination> getFutureExaminations(Long idfarmaceuta) {
        return super.getFutureExaminations(idfarmaceuta);
    }

//    @Override
//    public void delete(Examination examination) {
//        this.examinationRepository.delete(examination);
//    }
}
