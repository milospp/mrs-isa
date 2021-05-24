package isa9.Farmacy.constants;

import isa9.Farmacy.model.DispencingMedicine;
import isa9.Farmacy.model.MedSpecification;
import isa9.Farmacy.model.Medicine;

import java.util.HashSet;

public class MedicineConstants {

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
