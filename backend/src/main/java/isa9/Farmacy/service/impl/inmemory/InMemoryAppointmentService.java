package isa9.Farmacy.service.impl.inmemory;

import isa9.Farmacy.model.*;
import isa9.Farmacy.service.AppointmentService;
import isa9.Farmacy.service.ExaminationService;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.service.impl.base.AppointmentServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Component
public class InMemoryAppointmentService extends AppointmentServiceBase implements AppointmentService {
    private final Map<Long, Appointment> appointments = new HashMap<>();
    private final ExaminationService examinationService;
    private final UserService userService;
    private final PharmacyService pharmacyService;

    @Autowired
    InMemoryAppointmentService(ExaminationService examinationService, UserService userService, PharmacyService pharmacyService) {
        this.userService = userService;
        this.pharmacyService = pharmacyService;
        this.examinationService = examinationService;
        Appointment a1 = new Appointment(1L, LocalDateTime.now().plusDays(2), 20, 30, TypeOfReview.EXAMINATION ,new Dermatologist(), new Pharmacy(), null);
        Appointment a2 = new Appointment(2L, LocalDateTime.now().plusDays(2), 20, 30, TypeOfReview.EXAMINATION ,new Dermatologist(), new Pharmacy(), null);
        Appointment a3 = new Appointment(3L, LocalDateTime.now().plusDays(2), 20, 30, TypeOfReview.EXAMINATION ,new Pharmacist(), new Pharmacy(), null);

        Examination e1 = new Examination(1L, (Patient) userService.findOne(1L), a1, ExaminationStatus.NOT_HELD, "test", "diagnose", new HashMap<>());
        a1.setExamination(e1);

        appointments.put(a1.getId(), a1);
        appointments.put(a2.getId(), a2);
        appointments.put(a3.getId(), a3);

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


    @Override
    public List<Appointment> getAllFreeDermatologist() {
        List<Appointment> allAppointments;
        allAppointments = findAll().stream().filter(x -> isFreeDermAppointment(x)).collect(Collectors.toList());
        return allAppointments;
    }
}
