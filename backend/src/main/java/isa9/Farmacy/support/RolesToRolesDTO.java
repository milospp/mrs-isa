package isa9.Farmacy.support;

import isa9.Farmacy.model.User;
import isa9.Farmacy.model.UserRole;
import isa9.Farmacy.model.dto.DoctorDTO;
import isa9.Farmacy.model.dto.RolesDTO;
import isa9.Farmacy.model.dto.UserDTO;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

public class RolesToRolesDTO implements Converter<UserRole, RolesDTO> {
    @Override
    public RolesDTO convert(UserRole role) {
        if (role.getName().equals("SYS_ADMIN"))
            return RolesDTO.SYS_ADMIN;
        else if (role.getName().equals("PHARMACY_ADMIN"))
            return RolesDTO.PHARMACY_ADMIN;
        else if (role.getName().equals("DERMATOLOGIST"))
            return RolesDTO.DERMATOLOGIST;
        else if (role.getName().equals("PHARMACIST"))
            return RolesDTO.PHARMACIST;
        else if (role.getName().equals("PATIENT"))
            return RolesDTO.PATIENT;
        else if (role.getName().equals("SUPPLIER"))
            return RolesDTO.SUPPLIER;
        else
            return null;
    }
}
