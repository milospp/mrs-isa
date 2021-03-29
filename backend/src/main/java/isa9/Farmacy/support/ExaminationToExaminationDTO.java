package isa9.Farmacy.support;


import isa9.Farmacy.model.Examination;
import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.model.dto.ExaminationDTO;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ExaminationToExaminationDTO implements Converter<Examination, ExaminationDTO> {

    @Override
    public ExaminationDTO convert(Examination examination) {
        if (examination == null) return null;

        ExaminationDTO dto = new ExaminationDTO();

        dto.setId(examination.getId());
        dto.setPatient(examination.getPatient().getId());
        dto.setAppointment(examination.getAppointment().getId());
        dto.setStatus(examination.getStatus());
        dto.setExaminationInfo(examination.getExaminationInfo());
        dto.setDiagnose(examination.getDiagnose());


        // TO DO: Check if this works
        dto.setTherapy(
                examination.getTherapy().entrySet().stream()
                        .collect(Collectors.toMap(
                                e -> e.getKey().getCode(),
                                Map.Entry::getValue
                        ))
        );

        
        return dto;
    }

    public List<ExaminationDTO> convert(List<Examination> examinations) {
        List<ExaminationDTO> dto = new ArrayList<>();
        for (Examination examination : examinations){
            dto.add(convert(examination));
        }
        return dto;
    }
    public List<ExaminationDTO> convert(Set<Examination> examinations) {
        List<ExaminationDTO> dto = new ArrayList<>();
        for (Examination examination : examinations){
            dto.add(convert(examination));
        }
        return dto;
    }

}
