package isa9.Farmacy.constants;

import isa9.Farmacy.model.DispencingMedicine;
import isa9.Farmacy.model.MedSpecification;
import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.model.dto.*;
import isa9.Farmacy.model.dto.MedReservationDTO;

import java.time.LocalDate;
import java.util.HashSet;

public class MedicineConstants {

    public static MedReservationFormDTO getReservationForm() {
        MedReservationFormDTO medReservationFormDTO = new MedReservationFormDTO();
        medReservationFormDTO.setExpirityDate(LocalDate.of(2021,6,18));
        medReservationFormDTO.setMedicineId(3L);
        medReservationFormDTO.setPatientId(1L);
        medReservationFormDTO.setPharmacyId(1L);
        medReservationFormDTO.setQuantity(1);
        return medReservationFormDTO;
    }


    public static MedReservationDTO getReservationResult() {
        MedReservationDTO medReservationDTO = new MedReservationDTO();
        medReservationDTO.setReservationDate(LocalDate.now());
        medReservationDTO.setLastDate(getReservationForm().getExpirityDate());
        medReservationDTO.setMedicineInPharmacy(MedInPharmaDTO.builder().id(3L).build());
        medReservationDTO.setStatus(0);
        return medReservationDTO;
    }

    public static MedReservationDTO getReservedMedicine(){
        MedReservationDTO medReservationDTO = new MedReservationDTO();
        medReservationDTO.setId(2L);
        medReservationDTO.setMedicineInPharmacy(
                MedInPharmaDTO.builder().id(2L)
                        .medicine(MedicineDTO.builder().id(2L).build())
                        .pharmacy(PharmacyDTO.builder().id(1L).build())
                        .build()
        );


        return medReservationDTO;
    }

    public static Medicine getMedicineExample() {
        Medicine medicine = new Medicine();
        medicine.setId(1L);
        medicine.setCode("code");
        medicine.setName("Medicine1");
        medicine.setSpecification(new MedSpecification(1L, "SE1", "DI1", "STR1"));
        medicine.setManufacturer("Man1");
        medicine.setNote("Note1");
        medicine.setPoints(2);
        medicine.setShape("Shape1");
        medicine.setType("Type1");
        medicine.setPerscription(DispencingMedicine.WITHOUT_RECEIPT);
        medicine.setReplacementMedication(new HashSet<>());

        return medicine;
    }
}
