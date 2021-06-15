package isa9.Farmacy.support;

import isa9.Farmacy.model.Vacation;
import isa9.Farmacy.model.dto.PharmacyAdminDTO;
import isa9.Farmacy.model.dto.VacationDTO;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

@Component
public class VacationToVacationDTO implements Converter<Vacation, VacationDTO> {

    @Override
    public VacationDTO convert(Vacation vacation) {
        VacationDTO dto = new VacationDTO();

        dto.setId(vacation.getId());
        dto.setDoctorId(vacation.getDoctor().getId());
        dto.setPharmacyId(vacation.getPharmacy().getId());
        if (vacation.getAdmin() != null) {
            dto.setPharmacyAdminId(vacation.getAdmin().getId());
            dto.setAdmin((new UserToUserDTO()).convert(vacation.getAdmin()));
        }
        else {
            dto.setPharmacyAdminId(null);
            dto.setAdmin(null);
        }
        dto.setPharmacyName(vacation.getPharmacy().getName());
        dto.setEndDate(vacation.getEndDate());
        dto.setStartDate(vacation.getStartDate());
        dto.setReason(vacation.getReason());
        dto.setWhyNot(vacation.getWhyNot());
        dto.setStatus(vacation.getStatus());
        dto.setType(vacation.getType());
        dto.setDoctor((new DoctorToDoctorDTO()).convert(vacation.getDoctor()));

        return dto;
    }

    public List<VacationDTO> convert(List<Vacation> vacations) {
        List<VacationDTO> dto = new ArrayList<>();
        for (Vacation vacation : vacations){
            dto.add(convert(vacation));
        }
        return dto;
    }

}
