package isa9.Farmacy.controller;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.VacationDTO;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.service.VacationService;
import isa9.Farmacy.support.VacationToVacationDTO;
import isa9.Farmacy.utils.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://pharmacy9.herokuapp.com"})
@RequestMapping("/api/vacations")
public class VacationController {

    private final VacationService vacationService;
    private final UserService userService;
    private final PharmacyService pharmacyService;
    private final VacationToVacationDTO vacationToVacationDTO;
    private final MailService mailService;

    @Autowired
    public VacationController(VacationService vacationService, UserService userService, PharmacyService pharmacyService, VacationToVacationDTO vacationToVacationDTO, MailService mailService) {
        this.vacationService = vacationService;
        this.userService = userService;
        this.pharmacyService = pharmacyService;
        this.vacationToVacationDTO = vacationToVacationDTO;
        this.mailService = mailService;
    }

    @PostMapping("/saveRequest")
    @PreAuthorize("hasAuthority('DERMATOLOGIST') or hasAuthority('PHARMACIST')")
    public ResponseEntity<Boolean> saveRequest(@RequestBody VacationDTO vacationDTO) {
        Vacation vacation = Vacation
                .builder()
                .startDate(vacationDTO.getStartDate())
                .endDate(vacationDTO.getEndDate())
                .doctor(userService.getDoctorById(vacationDTO.getDoctorId()))
                .pharmacy(pharmacyService.findOne(vacationDTO.getPharmacyId()))
                .admin(null)
                .whyNot(null)
                .reason(vacationDTO.getReason())
                .status(VacationRequestStatus.WAITING)
                .type(vacationDTO.getType())
                .build();
        if (vacationService.testTime(vacation)) {
            Doctor d = userService.getDoctorById(vacationDTO.getDoctorId());
            d.getVacations().add(vacation);
            vacationService.save(vacation);
            userService.save(d);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @GetMapping("/{doctorId}/{pharmacyId}")
    @PreAuthorize("hasAuthority('DERMATOLOGIST') or hasAuthority('PHARMACIST')")
    public ResponseEntity<List<VacationDTO>> getVacationsForDoctorAndPharmacy(@PathVariable Long doctorId, @PathVariable Long pharmacyId) {
        List<Vacation> vacationsDoc = vacationService.getAllForDoctor(doctorId);
        List<Vacation> vacationsPharm = vacationService.getAllForPharmacy(pharmacyId);
        List<Vacation> result = new ArrayList<>();
        for (Vacation v : vacationsDoc){
            if (vacationsPharm.contains(v) && v.getStatus() == VacationRequestStatus.ACCEPTED){
                result.add(v);
            }
        }
        return new ResponseEntity<>(vacationToVacationDTO.convert(result), HttpStatus.OK);
    }

    @GetMapping("/pharm/{doctorId}")
    @PreAuthorize("hasAuthority('DERMATOLOGIST') or hasAuthority('PHARMACIST')")
    public ResponseEntity<List<VacationDTO>> getAllVacationsForDoctor(@PathVariable Long doctorId) {
        List<Vacation> vacationsDoc = vacationService.getAllForDoctor(doctorId);
        return new ResponseEntity<>(vacationToVacationDTO.convert(vacationsDoc), HttpStatus.OK);
    }

    @GetMapping("/{idAdmina}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<List<VacationDTO>> getVacationForPharmacy(@PathVariable Long idAdmina) {
        PharmacyAdmin admin = (PharmacyAdmin) this.userService.findOne(idAdmina);
        List<Vacation> sviZahtevi = vacationService.getAllForPharmacy(admin.getPharmacy().getId());
        List<VacationDTO> povratna = vacationToVacationDTO.convert(sviZahtevi);
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @GetMapping("/all/{idAdmina}")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<List<VacationDTO>> getAllVacation(@PathVariable Long idAdmina) {
        SysAdmin admin = (SysAdmin) this.userService.findOne(idAdmina);
        List<Vacation> sviZahtevi = vacationService.getWaitnigAll();
        List<VacationDTO> povratna = vacationToVacationDTO.convert(sviZahtevi);
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/{idAdmina}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN') or hasAuthority('SYS_ADMIN')")
    public void saveVacationAdmin(@PathVariable Long idAdmina, @RequestBody VacationDTO zahtev) {
        User admin = this.userService.findOne(idAdmina);
        Vacation originalZahtev = this.vacationService.findOne(zahtev.getId());
        if (zahtev.getStatus() == VacationRequestStatus.ACCEPTED)
            this.vacationService.cancelAppointments(originalZahtev, this.mailService);
        originalZahtev.setStatus(zahtev.getStatus());
        originalZahtev.setWhyNot(zahtev.getWhyNot());
        originalZahtev.setAdmin(admin);
        this.vacationService.save(originalZahtev);
        this.mailService.sendVacationInfo(originalZahtev);
    }
}
