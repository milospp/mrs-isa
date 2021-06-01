package isa9.Farmacy.support;

import isa9.Farmacy.model.PharmacyAdmin;
import isa9.Farmacy.model.dto.PharmacyAdminDTO;
import org.springframework.core.convert.converter.Converter;

public class PAdminToPAdminDTO implements Converter<PharmacyAdmin, PharmacyAdminDTO> {
    @Override
    public PharmacyAdminDTO convert(PharmacyAdmin pharmacyAdmin) {
        PharmacyAdminDTO povratna = new PharmacyAdminDTO();
        povratna.setId(pharmacyAdmin.getId());
        povratna.setAddress(pharmacyAdmin.getAddress());
        povratna.setEmail(pharmacyAdmin.getEmail());
        povratna.setName(pharmacyAdmin.getName());
        povratna.setPhoneNumber(pharmacyAdmin.getPhoneNumber());
        povratna.setSurname(pharmacyAdmin.getSurname());
        povratna.setPharmacyId(pharmacyAdmin.getPharmacy().getId());
        return povratna;
    }
}
