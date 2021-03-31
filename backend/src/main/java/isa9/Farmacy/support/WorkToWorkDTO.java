package isa9.Farmacy.support;

import isa9.Farmacy.model.Pharmacist;
import isa9.Farmacy.model.Work;
import isa9.Farmacy.model.dto.PharmacistDTO;
import isa9.Farmacy.model.dto.WorkDTO;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

public class WorkToWorkDTO implements Converter<Work, WorkDTO> {

    @Override
    public WorkDTO convert(Work work) {
        WorkDTO dto = new WorkDTO();

        dto.setId(work.getId());
        dto.setDoctorId(work.getDoctor().getId());
        dto.setPharmacyId(work.getPharmacy().getId());
        dto.setStartHour(work.getStartHour());
        dto.setEndHour(work.getEndHour());
        return dto;
    }

    public List<WorkDTO> convert(List<Work> works) {
        List<WorkDTO> dto = new ArrayList<>();
        for (Work work : works){
            dto.add(convert(work));
        }
        return dto;
    }
}
