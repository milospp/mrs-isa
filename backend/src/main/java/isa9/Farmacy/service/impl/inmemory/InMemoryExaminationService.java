package isa9.Farmacy.service.impl.inmemory;

import isa9.Farmacy.model.Appointment;
import isa9.Farmacy.model.Examination;
import isa9.Farmacy.model.ExaminationStatus;
import isa9.Farmacy.service.AppointmentService;
import isa9.Farmacy.service.ExaminationService;
import isa9.Farmacy.service.impl.base.AppointmentServiceBase;
import isa9.Farmacy.service.impl.base.ExaminationServiceBase;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InMemoryExaminationService extends ExaminationServiceBase implements ExaminationService {
    private final Map<Long, Examination> examinations = new HashMap<>();

    InMemoryExaminationService() {
        Examination a1 = new Examination();


    }

    @Override
    public List<Examination> findAll() {
        return new ArrayList<>(this.examinations.values());
    }

    @Override
    public Examination findOne(Long id) {
        return this.examinations.getOrDefault(id, null);
    }

    @Override
    public Examination save(Examination entity) {
        long id = this.examinations.size() + 1;
        entity.setId(id);
        this.examinations.put(id, entity);
        return entity;
    }


    @Override
    public List<Examination> getFutureExaminations(Long idfarmaceuta) {
        return super.getFutureExaminations(idfarmaceuta);
    }
}
