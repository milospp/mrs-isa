package isa9.Farmacy.support;


import isa9.Farmacy.model.MedReservation;
import isa9.Farmacy.model.dto.MedReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Component
public class MedReservationToMedReservationDTO implements Converter<MedReservation, MedReservationDTO> {

    PharmacistToPharmacistDTO pharmacistToPharmacistDTO;
    MedicineInPharmacyToMedInPharmaDTO medicineInPharmacyToMedInPharmaDTO;

    @Autowired
    public MedReservationToMedReservationDTO(PharmacistToPharmacistDTO pharmacistToPharmacistDTO, MedicineInPharmacyToMedInPharmaDTO medicineInPharmacyToMedInPharmaDTO) {
        this.pharmacistToPharmacistDTO = pharmacistToPharmacistDTO;
        this.medicineInPharmacyToMedInPharmaDTO = medicineInPharmacyToMedInPharmaDTO;
    }

    @Override
    public MedReservationDTO convert(MedReservation medReservation) {
        MedReservationDTO dto = new MedReservationDTO();

        dto.setId(medReservation.getId());
        dto.setCode(medReservation.getCode());
        dto.setLastDate(medReservation.getLastDate());
        dto.setReservationDate(medReservation.getReservationDate());
        dto.setTaken(medReservation.isTaken());
        dto.setCanceled(medReservation.isCanceled());


        dto.setMedicineInPharmacy(medicineInPharmacyToMedInPharmaDTO.convert(medReservation.getMedicineInPharmacy()));
        dto.setQuantity(medReservation.getQuantity());


        if (medReservation.getIssued() != null)
            dto.setIssued(pharmacistToPharmacistDTO.convert(medReservation.getIssued()));

        return dto;
    }

    public List<MedReservationDTO> convert(Collection<MedReservation> medReservations) {
        List<MedReservationDTO> dto = new ArrayList<>();
        for (MedReservation medReservation : medReservations){
            dto.add(convert(medReservation));
        }
        return dto;
    }
}
