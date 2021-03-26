package isa9.Farmacy.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Medicine {
    private String code;
    private String name;
    private String structure;
    private String manufacturer;
    private String note;
    private int points;
    private String shape;
    private String type;
    private DispencingMedicine perscription;
    private Set<Medicine> replacementMedication;

    // TODO Medicine Stock


    public Medicine(String code) {
        this.code = code;
    }

    public Medicine(String code, String name, String structure, String manufacturer, String note, int points, String shape, String type, DispencingMedicine perscription, Set<Medicine> replacementMedication) {
        this.code = code;
        this.name = name;
        this.structure = structure;
        this.manufacturer = manufacturer;
        this.note = note;
        this.points = points;
        this.shape = shape;
        this.type = type;
        this.perscription = perscription;
        if (replacementMedication == null) {
            this.replacementMedication = new HashSet<Medicine>();
        } else {
            this.replacementMedication = replacementMedication;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DispencingMedicine getPerscription() {
        return perscription;
    }

    public void setPerscription(DispencingMedicine perscription) {
        this.perscription = perscription;
    }

    public Set<Medicine> getReplacementMedication() {
        return replacementMedication;
    }

    public void setReplacementMedication(Set<Medicine> replacementMedication) {
        this.replacementMedication = replacementMedication;
    }
}
