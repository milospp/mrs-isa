package isa9.Farmacy.support;

import isa9.Farmacy.model.Pharmacist;
import isa9.Farmacy.model.Work;
import isa9.Farmacy.model.dto.PharmacistDTO;
import isa9.Farmacy.model.dto.WorkDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PharmacistToPharmacistDTO implements Converter<Pharmacist, PharmacistDTO> {

    @Override
    public PharmacistDTO convert(Pharmacist farmaceut) {
        PharmacistDTO dto = new PharmacistDTO();

        dto.setId(farmaceut.getId());
        dto.setName(farmaceut.getName());
        dto.setSurname(farmaceut.getSurname());
        dto.setAddress(farmaceut.getAddress());
        dto.setPhoneNumber(farmaceut.getPhoneNumber());
        //dto.setPharmacy(farmaceut.getPharmacy());
        // vrv ovo treba da se menja da ima samo id a ne celu apoteku
        return dto;
    }

    public List<PharmacistDTO> convert(List<Pharmacist> farmaceuti) {
        List<PharmacistDTO> dto = new ArrayList<>();
        for (Pharmacist f : farmaceuti){
            dto.add(convert(f));
        }
        return dto;
    }

    public PharmacistDTO convert(Pharmacist farmaceut, Work workInPharmacy) {
        PharmacistDTO dto = new PharmacistDTO();

        dto.setId(farmaceut.getId());
        dto.setName(farmaceut.getName());
        dto.setSurname(farmaceut.getSurname());
        dto.setAddress(farmaceut.getAddress());
        dto.setPhoneNumber(farmaceut.getPhoneNumber());

        WorkDTO posaoUApoteci = new WorkDTO();
        posaoUApoteci.setEndHour(workInPharmacy.getEndHour());
        posaoUApoteci.setStartHour(workInPharmacy.getStartHour());
        posaoUApoteci.setPharmacyId(workInPharmacy.getPharmacy().getId());
        posaoUApoteci.setPharmacyName(workInPharmacy.getPharmacy().getName());
        dto.setPharmacyWork(posaoUApoteci);
        return dto;
    }
}
