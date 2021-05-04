package isa9.Farmacy.support;

import isa9.Farmacy.model.Work;
import isa9.Farmacy.model.dto.WorkDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class WorkToWorkDTO implements Converter<Work, WorkDTO> {

    private final PharmacyToPharmacyDTO pharmacyToPharmacyDTO;
    private final DoctorToDoctorDTO doctorToDoctorDTO;

    @Autowired
    public WorkToWorkDTO(PharmacyToPharmacyDTO pharmacyToPharmacyDTO, DoctorToDoctorDTO doctorToDoctorDTO) {
        this.pharmacyToPharmacyDTO = pharmacyToPharmacyDTO;
        this.doctorToDoctorDTO = doctorToDoctorDTO;
    }

    @Override
    public WorkDTO convert(Work work) {
        WorkDTO dto = new WorkDTO();

        dto.setId(work.getId());
        dto.setDoctorId(work.getDoctor().getId());

        dto.setPharmacyId(work.getPharmacy().getId());
        dto.setPharmacyName(work.getPharmacy().getName());
        dto.setPharmacyAddr(work.getPharmacy().getAddress());
        dto.setPharmacyDesc(work.getPharmacy().getDescription());
        dto.setPharmacyDTO(pharmacyToPharmacyDTO.convert(work.getPharmacy()));
        dto.setDoctorDTO(doctorToDoctorDTO.convert(work.getDoctor()));


        dto.setStartHour(work.getStartHour());
        dto.setEndHour(work.getEndHour());
        return dto;
    }

    public List<WorkDTO> convert(Collection<Work> works) {
        List<WorkDTO> dto = new ArrayList<>();
        for (Work work : works){
            dto.add(convert(work));
        }
        return dto;
    }
}
