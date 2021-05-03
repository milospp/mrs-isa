package isa9.Farmacy.service;

import isa9.Farmacy.model.Work;

import java.time.LocalTime;
import java.util.List;

public interface WorkService extends GenericService<Work>{

    List<Work> getWorksByTime(LocalTime time);
    List<Work> getWorksInInterval(LocalTime start, LocalTime end);

}
