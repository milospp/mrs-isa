package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.DispencingMedicine;
import isa9.Farmacy.model.Medicine;

import java.util.List;

public class MedicineDTO {
    private String code;
    private String name;
    private String structure;
    private String manufacturer;
    private String note;
    private int points;
    private String shape;
    private String type;
    private DispencingMedicine perscription;
    private List<String> replacementMedicationIds;

    public MedicineDTO() {
    }

    public MedicineDTO(String code, String name, String structure, String manufacturer, String note, int points, String shape, String type, DispencingMedicine perscription, List<String> replacementMedicationIds) {
        this.code = code;
        this.name = name;
        this.structure = structure;
        this.manufacturer = manufacturer;
        this.note = note;
        this.points = points;
        this.shape = shape;
        this.type = type;
        this.perscription = perscription;
        this.replacementMedicationIds = replacementMedicationIds;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getStructure() {
        return structure;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getNote() {
        return note;
    }

    public int getPoints() {
        return points;
    }

    public String getShape() {
        return shape;
    }

    public String getType() {
        return type;
    }

    public DispencingMedicine getPerscription() {
        return perscription;
    }

    public List<String> getReplacementMedicationIds() {
        return replacementMedicationIds;
    }
}



