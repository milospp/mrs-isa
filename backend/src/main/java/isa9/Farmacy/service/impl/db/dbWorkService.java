package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.Work;
import isa9.Farmacy.repository.WorkRepository;
import isa9.Farmacy.service.WorkService;
import isa9.Farmacy.service.impl.base.WorkServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Component
@Primary
@Service
public class dbWorkService extends WorkServiceBase implements WorkService {

    private final WorkRepository workRepository;

    @Autowired
    public dbWorkService(WorkRepository workRepository) {
        this.workRepository = workRepository;
    }

    @Override
    public List<Work> findAll() {
        return this.workRepository.findAll();
    }

    @Override
    public Work findOne(Long id) {
        return this.workRepository.findById(id).orElseGet(null);
    }

    @Override
    public Work save(Work entity) {
        return this.workRepository.save(entity);
    }

    @Override
    public List<Work> getWorksByTime(LocalTime time) {
        System.out.println("time.toString() = " + time.toString());
        return workRepository.getAllWorksByTime(time.toString());
    }
}
