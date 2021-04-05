package isa9.Farmacy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class MedPrescription {

    @Column
    @Id
    private String code;
    @Column
    private String medName;
    @Column
    private int quantity;
    @ManyToOne
    private EPrescription eRecipe;

    public MedPrescription() {
        super();
    }

    public MedPrescription(String code, String medName, int quantity, EPrescription erecipe) {
        super();
        this.code = code;
        this.medName = medName;
        this.quantity = quantity;
        this.eRecipe = erecipe;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public EPrescription geteRecipe() {
        return eRecipe;
    }

    public void seteRecipe(EPrescription eRecipe) {
        this.eRecipe = eRecipe;
    }

    @Override
    public String toString() {
        return "MedPrescription [code=" + code + ", medName=" + medName + ", quantity=" + quantity + ", eRecipe="
                + eRecipe + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedPrescription that = (MedPrescription) o;
        return quantity == that.quantity && Objects.equals(code, that.code) && Objects.equals(medName, that.medName) && Objects.equals(eRecipe, that.eRecipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, medName, quantity, eRecipe);
    }
}
