package isa9.Farmacy.controller;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.*;
import isa9.Farmacy.service.MedReservationService;
import isa9.Farmacy.service.MedicineService;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.support.MedReservationToMedReservationDTO;
import isa9.Farmacy.support.MedicineInPharmacyToMedInPharmaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/medicines")
public class MedicineController {

    private final MedicineService medicineService;
    private final UserService userService;
    private final PharmacyService pharmacyService;
    private final MedReservationService medReservationService;
    private final MedReservationToMedReservationDTO medReservationToMedReservationDTO;
    private final MedicineInPharmacyToMedInPharmaDTO medicineInPharmacyToMedInPharmaDTO;

    @Autowired
    public MedicineController(MedicineService medicineService, UserService userService, PharmacyService pharmacyService, MedReservationService medReservationService, MedReservationToMedReservationDTO medReservationToMedReservationDTO, MedicineInPharmacyToMedInPharmaDTO medicineInPharmacyToMedInPharmaDTO) {
        this.medicineService = medicineService;
        this.userService = userService;
        this.pharmacyService = pharmacyService;
        this.medReservationService = medReservationService;
        this.medReservationToMedReservationDTO = medReservationToMedReservationDTO;
        this.medicineInPharmacyToMedInPharmaDTO = medicineInPharmacyToMedInPharmaDTO;
    }

    @GetMapping("tmp-test")
    public ResponseEntity<Boolean> debug(){
        Pharmacy pharmacy = pharmacyService.findOne(1L);
        Medicine medicine = medicineService.findOne(1L);

        MedicineInPharmacy mip = new MedicineInPharmacy(1L,null , medicine,20, pharmacy);
        MedPrice mp1 = new MedPrice(1L, LocalDateTime.now(), 20.0, mip);
        mip.setCurrentPrice(mp1);

        pharmacy.getMedicines().add(mip);
        pharmacyService.save(pharmacy);
        return new ResponseEntity<>(true, HttpStatus.OK);

    }

    @PostMapping("{medId}/pharmacy/{pharmacyId}/reserve")
    public ResponseEntity<MedReservationDTO> reserveMedicine(@PathVariable Long medId, @PathVariable Long pharmacyId, @RequestBody MedReservationFormDTO form){
        // TODO: Get patient from session
        form.setPatientId(1L);

        form.setMedicineId(medId);
        form.setPharmacyId(pharmacyId);
        MedReservation medReservation = medReservationService.reserveMedicine(form);

        if (medReservation == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);


        MedReservationDTO dto = medReservationToMedReservationDTO.convert(medReservation);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping("")
    public ResponseEntity<List<MedicineDTO>> test(){

        List<MedicineDTO> resultDTOS = new ArrayList<>();
        for (Medicine medicine : this.medicineService.findAll()) {
            resultDTOS.add(new MedicineDTO(
                    medicine.getId(),
                    medicine.getCode(),
                    medicine.getName(),
                    medicine.getStructure(),
                    medicine.getManufacturer(),
                    medicine.getNote(),
                    medicine.getPoints(),
                    medicine.getShape(),
                    medicine.getType(),
                    medicine.getPerscription(),
                    medicine.getReplacementMedication().stream()
                            .map(Medicine::getCode)
                            .collect(Collectors.toList())
            ));
        }

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);
    }


    @GetMapping("/pharmacyAdmin/{id}")
    public ResponseEntity<List<MedInPharmaDTO>> getAllMedicinePharmacyAdmin(@PathVariable Long id) {
        User user = userService.findOne(id);
        if (user.getClass() != PharmacyAdmin.class) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        Set<MedicineInPharmacy> sviLekovi = ((PharmacyAdmin) user).getPharmacy().getMedicines();
        List<MedInPharmaDTO> povratna = medicineInPharmacyToMedInPharmaDTO.convert(sviLekovi);
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }
    @GetMapping("/pharmacy/{id}")
    public ResponseEntity<List<MedInPharmaDTO>> getAllMedicinePharmacy(@PathVariable Long id) {
        Pharmacy apoteka = pharmacyService.findOne(id);
        List<MedInPharmaDTO> povratna = medicineInPharmacyToMedInPharmaDTO.convert(apoteka.getMedicines());
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/newMedicine")
    public ResponseEntity<Integer> addMedicine(@RequestBody MedicineDTO medicineDTO){
        List<Long> replacementMedsIds = medicineDTO.getReplacementMedicationIds().stream().map(Long::parseLong).collect(Collectors.toList());
        Medicine newMedicine = new Medicine(0L, medicineDTO.getCode(), medicineDTO.getName(), medicineDTO.getStructure(),
                medicineDTO.getManufacturer(), medicineDTO.getNote(), medicineDTO.getPoints(), medicineDTO.getShape(),
                medicineDTO.getType(), medicineDTO.getPerscription(), medicineService.idsToMedicines(replacementMedsIds));
        boolean checkCode = this.medicineService.isCodeAvailable(newMedicine.getCode());
        if(checkCode){
            this.medicineService.save(newMedicine);
            return new ResponseEntity<>(0, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(1, HttpStatus.OK); // 0 - sve uredu, 1 - sifra leka je zauzeta
        }
    }
}
