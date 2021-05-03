package isa9.Farmacy.service;

import isa9.Farmacy.model.Work;

import java.time.LocalTime;
import java.util.Set;

public interface WorkService extends GenericService<Work>{

    Set<Work> getWorksByTime(LocalTime time);
}
