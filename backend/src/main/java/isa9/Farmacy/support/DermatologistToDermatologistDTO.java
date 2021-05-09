package isa9.Farmacy.support;

import isa9.Farmacy.model.Dermatologist;
import isa9.Farmacy.model.Work;
import isa9.Farmacy.model.dto.DermatologistDTO;
import isa9.Farmacy.model.dto.WorkDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DermatologistToDermatologistDTO implements Converter<Dermatologist, DermatologistDTO> {

    @Override
    public DermatologistDTO convert(Dermatologist dermatolog) {
        DermatologistDTO dto = new DermatologistDTO();

        dto.setId(dermatolog.getId());
        dto.setName(dermatolog.getName());
        dto.setSurname(dermatolog.getSurname());
        dto.setAddress(dermatolog.getAddress());
        dto.setPhoneNumber(dermatolog.getPhoneNumber());
        //dto.setPharmacies(dermatolog.getPharmacies());
        // vrv ovo treba da se menja da ima samo listu id-eva a ne celu apoteku
        return dto;
    }

    public List<DermatologistDTO> convert(List<Dermatologist> dermatolozi) {
        List<DermatologistDTO> dto = new ArrayList<>();
        for (Dermatologist d : dermatolozi){
            dto.add(convert(d));
        }
        return dto;
    }

    public DermatologistDTO convert(Dermatologist dermatolog, Work workInPharmacy) {
        DermatologistDTO dto = new DermatologistDTO();

        dto.setId(dermatolog.getId());
        dto.setName(dermatolog.getName());
        dto.setSurname(dermatolog.getSurname());
        dto.setAddress(dermatolog.getAddress());
        dto.setPhoneNumber(dermatolog.getPhoneNumber());

        WorkDTO posaoUApoteci = new WorkDTO();
        posaoUApoteci.setEndHour(workInPharmacy.getEndHour());
        posaoUApoteci.setStartHour(workInPharmacy.getStartHour());
        posaoUApoteci.setPharmacyId(workInPharmacy.getPharmacy().getId());
        posaoUApoteci.setSalaryPerHour(workInPharmacy.getSalaryPerHour());
        dto.setPharmacyWork(posaoUApoteci);
        return dto;
    }
}