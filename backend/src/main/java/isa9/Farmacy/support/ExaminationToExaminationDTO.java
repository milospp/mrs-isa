package isa9.Farmacy.support;


import isa9.Farmacy.model.Examination;
import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.model.dto.ExaminationDTO;
import isa9.Farmacy.model.dto.PatientDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

    PatientToPatientDTO patientToPatientDTO;

    @Autowired
    public ExaminationToExaminationDTO(PatientToPatientDTO patientToPatientDTO) {
        this.patientToPatientDTO = patientToPatientDTO;
    }

    @Override
    public ExaminationDTO convert(Examination examination) {
        if (examination == null) return null;

        ExaminationDTO dto = new ExaminationDTO();

        dto.setId(examination.getId());
        dto.setPatient(patientToPatientDTO.convert(examination.getPatient()));
        dto.setAppointment(examination.getAppointment().getId());
        dto.setStatus(examination.getStatus());
        dto.setExaminationInfo(examination.getExaminationInfo());
        dto.setDiagnose(examination.getDiagnose());

        dto.setTherapy(examination.getTherapy());
        dto.setType(examination.getAppointment().getType());
        
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
