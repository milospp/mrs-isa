package isa9.Farmacy.support;

import isa9.Farmacy.model.Patient;
import isa9.Farmacy.model.User;
import isa9.Farmacy.model.dto.UserDTO;
import isa9.Farmacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDTOToUser implements Converter<UserDTO, User> {
    private UserService userService;

    @Autowired
    public UserDTOToUser(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User convert(UserDTO userDTO) {
        User user;

        if (userDTO.getId() == null){
            user = new Patient();
        } else {
            user = userService.findOne(userDTO.getId());
        }

        if (userDTO.getEmail() != null) user.setEmail(userDTO.getEmail());
        if (userDTO.getAddress() != null) user.setAddress(userDTO.getAddress());
        if (userDTO.getName() != null) user.setName(userDTO.getName());
        if (userDTO.getSurname() != null) user.setSurname(userDTO.getSurname());
        if (userDTO.getPhoneNumber() != null) user.setPhoneNumber(userDTO.getPhoneNumber());

        return user;

    }

    public List<User> convert(List<UserDTO> usersDTO) {
        List<User> entities = new ArrayList<>();
        for (UserDTO userDTO : usersDTO){
            entities.add(convert(userDTO));
        }
        return entities;
    }
}
