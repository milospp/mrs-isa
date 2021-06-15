package isa9.Farmacy.controller;

import isa9.Farmacy.model.User;
import isa9.Farmacy.model.dto.UserDTO;
import isa9.Farmacy.model.dto.UserTokenState;
import isa9.Farmacy.security.auth.JwtAuthenticationRequest;
import isa9.Farmacy.service.impl.db.dbUserService;
import isa9.Farmacy.support.UserToUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import isa9.Farmacy.security.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://pharmacy-tim9.herokuapp.com", "https://pharmacy9.herokuapp.com"})
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    private TokenUtils tokenUtils;

    private AuthenticationManager authenticationManager;

    private dbUserService userService;
    private final UserToUserDTO userToUserDTO;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(TokenUtils t, AuthenticationManager aM, dbUserService us,
                                    UserToUserDTO userToUserDTO, PasswordEncoder pe){
        this.tokenUtils = t;
        this.authenticationManager = aM;
        this.userService = us;
        this.userToUserDTO = userToUserDTO;
        this.passwordEncoder = pe;
    }


    // Prvi endpoint koji pogadja korisnik kada se loguje.
    // Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                                                    HttpServletResponse response) {

        UsernamePasswordAuthenticationToken u = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                authenticationRequest.getPassword());


        //
        Authentication authentication = authenticationManager.authenticate(u);


        // Ubaci korisnika u trenutni security kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getEmail());
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }




    // U slucaju isteka vazenja JWT tokena, endpoint koji se poziva da se token osvezi
    @PostMapping(value = "/refresh")
    public ResponseEntity<UserTokenState> refreshAuthenticationToken(HttpServletRequest request) {

        String token = tokenUtils.getToken(request);
        String username = this.tokenUtils.getUsernameFromToken(token);
        User user = (User) this.userService.loadUserByUsername(username);

        if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = tokenUtils.refreshToken(token);
            int expiresIn = tokenUtils.getExpiredIn();

            return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn));
        } else {
            UserTokenState userTokenState = new UserTokenState();
            return ResponseEntity.badRequest().body(userTokenState);
        }
    }

    @GetMapping("/getLoggedIn")
    public ResponseEntity<UserDTO> getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        User user = (User) authentication.getPrincipal();
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        UserDTO dto = userToUserDTO.convert(user);

        return new ResponseEntity<>(dto,  HttpStatus.OK);

    }

    @GetMapping("/getPasswordResetDate/{id}")
    public ResponseEntity<Timestamp> getPasswordResetDate(@PathVariable Long id){
        User user = this.userService.findOne(id);
        return new ResponseEntity<>(user.getLastPasswordResetDate(), HttpStatus.OK);
    }

    @PostMapping("/changePassword/{id}")
    public ResponseEntity<Boolean> changePassword(@PathVariable Long id, @RequestBody String newPassword){
        // TODO do we need to check if id is same as logged in user?
        newPassword = newPassword.substring(0, newPassword.length() - 1);

        return new ResponseEntity<>(this.userService.changePassword(id, passwordEncoder.encode(newPassword)),
                                    HttpStatus.OK);
    }

}
