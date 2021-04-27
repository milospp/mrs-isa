package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.Examination;
import isa9.Farmacy.model.ExaminationStatus;
import isa9.Farmacy.service.ExaminationService;

import java.util.ArrayList;
import java.util.List;


public abstract class ExaminationServiceBase implements ExaminationService {

    @Override
    public List<Examination> getFutureExaminations(Long idfarmaceuta) {
        List<Examination> svi = findAll();
        List<Examination> povratnaVrednost = new ArrayList<>();
        for (Examination e : svi) if (e.getAppointment().getDoctor().getId().equals(idfarmaceuta)) {
            if (e.getStatus().equals(ExaminationStatus.NOT_HELD)) povratnaVrednost.add(e);}
        return povratnaVrednost;
    }

}
