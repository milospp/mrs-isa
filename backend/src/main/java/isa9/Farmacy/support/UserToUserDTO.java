package isa9.Farmacy.support;

import isa9.Farmacy.model.Doctor;
import isa9.Farmacy.model.User;
import isa9.Farmacy.model.dto.DoctorDTO;
import isa9.Farmacy.model.dto.RolesDTO;
import isa9.Farmacy.model.dto.UserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserToUserDTO implements Converter<User, UserDTO> {

    @Override
    public UserDTO convert(User user) {
        UserDTO dto = new DoctorDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setAddress(user.getAddress());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setEmail(user.getEmail());

        RolesToRolesDTO roleDtoConverter = new RolesToRolesDTO();
        dto.setRole(roleDtoConverter.convert(user.getRole()));
        return dto;
    }

    public List<UserDTO> convert(List<User> users) {
        List<UserDTO> dto = new ArrayList<>();
        for (User user : users){
            dto.add(convert(user));
        }
        return dto;
    }
}
