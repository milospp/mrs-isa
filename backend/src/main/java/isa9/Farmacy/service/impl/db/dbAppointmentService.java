package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.*;
import isa9.Farmacy.repository.AppointmentRepository;
import isa9.Farmacy.service.AppointmentService;
import isa9.Farmacy.service.impl.base.AppointmentServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Primary
@Service
public class dbAppointmentService extends AppointmentServiceBase implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public dbAppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<Appointment> findAll() {
        return this.appointmentRepository.findAll();
    }

    @Override
    public Appointment findOne(Long id) {
        return this.appointmentRepository.findById(id).orElseGet(null);
    }

    @Override
    public Appointment save(Appointment entity) {
        return this.appointmentRepository.save(entity);
    }


}
