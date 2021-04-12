package isa9.Farmacy.service;

import isa9.Farmacy.model.MedReservation;
import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.model.dto.MedReservationFormDTO;

import java.util.List;
import java.util.Set;

public interface MedicineService extends GenericService<Medicine> {

    Boolean isCodeAvailable(String medicineId);
    Set<Medicine> idsToMedicines(List<Long> ids);
}
