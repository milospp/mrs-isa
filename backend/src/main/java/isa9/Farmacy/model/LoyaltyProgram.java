package isa9.Farmacy.model;

import java.util.Map;
import java.util.Objects;

public class LoyaltyProgram {private Map<Integer, String> Category;
    private int ExaminationPoints;
    private int CounselingPoints;
    private Map<String, Integer> Discount;

    public LoyaltyProgram() {
        super();
    }

    public LoyaltyProgram(Map<Integer, String> category, int examinationPoints, int counselingPoints,
                          Map<String, Integer> discount) {
        super();
        Category = category;
        ExaminationPoints = examinationPoints;
        CounselingPoints = counselingPoints;
        Discount = discount;
    }

    public Map<Integer, String> getCategory() {
        return Category;
    }

    public void setCategory(Map<Integer, String> category) {
        Category = category;
    }

    public int getExaminationPoints() {
        return ExaminationPoints;
    }

    public void setExaminationPoints(int examinationPoints) {
        ExaminationPoints = examinationPoints;
    }

    public int getCounselingPoints() {
        return CounselingPoints;
    }

    public void setCounselingPoints(int counselingPoints) {
        CounselingPoints = counselingPoints;
    }

    public Map<String, Integer> getDiscount() {
        return Discount;
    }

    public void setDiscount(Map<String, Integer> discount) {
        Discount = discount;
    }

    @Override
    public String toString() {
        return "LoyaltyProgram [Category=" + Category + ", ExaminationPoints=" + ExaminationPoints
                + ", CounselingPoints=" + CounselingPoints + ", Discount=" + Discount + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoyaltyProgram that = (LoyaltyProgram) o;
        return ExaminationPoints == that.ExaminationPoints && CounselingPoints == that.CounselingPoints && Objects.equals(Category, that.Category) && Objects.equals(Discount, that.Discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Category, ExaminationPoints, CounselingPoints, Discount);
    }
}
