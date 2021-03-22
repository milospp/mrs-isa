package isa9.Farmacy.controller;

import com.sun.mail.iap.Response;
import isa9.Farmacy.model.Pharmacist;
import isa9.Farmacy.model.User;
import isa9.Farmacy.model.dto.UserDTO;
import isa9.Farmacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("all-users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> resultDTOS = new ArrayList<>();
        for (User user : this.userService.findAll()){
            resultDTOS.add(new UserDTO(user.getId(), user.getUsername(), user.getName(), user.getSurname(), user.getAddress().toString(), user.getPhoneNumber()));
        }

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id){
        User user = userService.findOne(id);

        UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getName(), user.getSurname(), user.getAddress().toString(), user.getPhoneNumber());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);

    }


    @PostMapping("register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(
                new UserDTO(user.getId(), user.getUsername(), user.getName(), user.getSurname(), user.getAddress().toString(), user.getPhoneNumber()),
                HttpStatus.OK
        );

    }

    @GetMapping(path = "is-available-username/{us}", produces = "application/json")
    public ResponseEntity<Boolean> isAvaibleUsername(@PathVariable String us) {
        System.out.println(us);
        boolean povratna = userService.isAvaibleUsername(us);
       return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @GetMapping(path = "is-available-email/{em}", produces = "application/json")
    public ResponseEntity<Boolean> isAvaibleEmaile(@PathVariable String em) {
        System.out.println(em);
        boolean povratna = userService.isAvaibleEmail(em);
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/register/pharmacist")
    public ResponseEntity<Integer> createPharmacist(@RequestBody Pharmacist user) {
        int povratna = 0;
        if (!userService.isAvaibleUsername(user.getUsername())) povratna = 1;
        if (!userService.isAvaibleEmail(user.getEmail())) povratna += 2;
        if (povratna > 0) return new ResponseEntity<>(povratna, HttpStatus.OK);
        User createdUser = userService.save(user);
        System.out.println(createdUser);
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }
}
