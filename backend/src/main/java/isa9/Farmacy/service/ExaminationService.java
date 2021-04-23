package isa9.Farmacy.service;

import isa9.Farmacy.model.Examination;

import java.util.List;

public interface ExaminationService extends GenericService<Examination>{

    List<Examination> getPharmacistFutureExaminations(Long idfarmaceuta);

}
