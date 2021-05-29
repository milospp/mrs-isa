package isa9.Farmacy.controller;

import isa9.Farmacy.model.Vacation;
import isa9.Farmacy.model.VacationRequestStatus;
import isa9.Farmacy.model.dto.VacationDTO;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.service.VacationService;
import isa9.Farmacy.support.VacationToVacationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/vacations")
public class VacationController {

    private final VacationService vacationService;
    private final UserService userService;
    private final PharmacyService pharmacyService;
    private final VacationToVacationDTO vacationToVacationDTO;

    @Autowired
    public VacationController(VacationService vacationService, UserService userService, PharmacyService pharmacyService, VacationToVacationDTO vacationToVacationDTO) {
        this.vacationService = vacationService;
        this.userService = userService;
        this.pharmacyService = pharmacyService;
        this.vacationToVacationDTO = vacationToVacationDTO;
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
                .pharmacyAdmin(null)
                .whyNot(null)
                .reason(vacationDTO.getReason())
                .status(VacationRequestStatus.WAITING)
                .type(vacationDTO.getType())
                .build();
        if (vacationService.testTime(vacation)) {
            vacationService.save(vacation);
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
}
