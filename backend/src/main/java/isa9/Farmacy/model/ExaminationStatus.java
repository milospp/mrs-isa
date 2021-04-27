package isa9.Farmacy.model;

public enum ExaminationStatus {
    PENDING, // examination didn't start, patient didn't yet come
    HELD, // patient show up
    NOT_HELD, // patient didn't show up
    CANCELED // patient canceled the examination
}
