package isa9.Farmacy.service.impl.inmemory;

import isa9.Farmacy.model.*;
import isa9.Farmacy.service.AppointmentService;
import isa9.Farmacy.service.ExaminationService;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.impl.base.AppointmentServiceBase;
import isa9.Farmacy.service.impl.base.PharmacyServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Component
public class InMemoryAppointmentService extends AppointmentServiceBase implements AppointmentService {
    private final Map<Long, Appointment> appointments = new HashMap<>();
    private final ExaminationService examinationService;

    @Autowired
    InMemoryAppointmentService(ExaminationService examinationService) {
        this.examinationService = examinationService;
        Appointment a1 = new Appointment(1L, LocalDateTime.now().plusDays(2), 20, 30, TypeOfReview.EXAMINATION ,new Dermatologist(), new Pharmacy(), null);

        appointments.put(a1.getId(), a1);

    }

    @Override
    public List<Appointment> findAll() {
        return new ArrayList<>(this.appointments.values());
    }

    @Override
    public Appointment findOne(Long id) {
        return this.appointments.getOrDefault(id, null);
    }

    @Override
    public Appointment save(Appointment entity) {
        long id = this.appointments.size() + 1;
        entity.setId(id);
        this.appointments.put(id, entity);
        return entity;
    }


}
