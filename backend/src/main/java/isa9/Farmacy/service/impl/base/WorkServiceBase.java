package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.Examination;
import isa9.Farmacy.model.ExaminationStatus;
import isa9.Farmacy.model.Work;
import isa9.Farmacy.service.ExaminationService;
import isa9.Farmacy.service.WorkService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public abstract class WorkServiceBase implements WorkService {

    @Override
    public List<Work> getWorksInInterval(LocalTime start, LocalTime end) {
        List<Work> works = getWorksByTime(start);
        List<Work> resultWorks = new ArrayList<>();

        for (Work w : works){
            if (w.getStartHour().isBefore(w.getEndHour())){
                if (w.getEndHour().isAfter(end)) resultWorks.add(w);
            } else {
                if (w.getStartHour().isBefore(end) || w.getEndHour().isAfter(end)) resultWorks.add(w);
            }
        }

        return resultWorks;
    }

}
