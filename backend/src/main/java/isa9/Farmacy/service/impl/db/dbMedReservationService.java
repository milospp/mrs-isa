package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.MedReservation;
import isa9.Farmacy.repository.MedReservationRepository;
import isa9.Farmacy.repository.PharmacyRepository;
import isa9.Farmacy.service.MedReservationService;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.impl.base.MedReservationServiceBase;
import isa9.Farmacy.service.impl.base.PharmacyServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Primary
@Service
public class dbMedReservationService extends MedReservationServiceBase implements MedReservationService {

    private final MedReservationRepository medReservationRepository;

    @Autowired
    public dbMedReservationService(MedReservationRepository medReservationRepository) {
        this.medReservationRepository = medReservationRepository;
    }

    @Override
    public List<MedReservation> findAll() {
        return this.medReservationRepository.findAll();
    }

    @Override
    public MedReservation findOne(Long id) {
        return this.medReservationRepository.findById(id).orElseGet(null);
    }

    @Override
    public MedReservation save(MedReservation entity) {
        return this.medReservationRepository.save(entity);
    }

}
