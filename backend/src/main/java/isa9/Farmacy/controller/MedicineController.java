package isa9.Farmacy.controller;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.MedInPharmaDTO;
import isa9.Farmacy.model.dto.MedicineDTO;
import isa9.Farmacy.model.dto.MedicineQuantityDTO;
import isa9.Farmacy.service.MedicineService;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.support.MedicineInPharmacyToMedInPharmaDTO;
import isa9.Farmacy.support.MedicineToMedicineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/medicines")
public class MedicineController {

    private final MedicineService medicineService;
    private final UserService userService;
    private final PharmacyService pharmacyService;

    @Autowired
    public MedicineController(MedicineService medicineService, UserService us, PharmacyService ps){
        this.userService = us;
        this.pharmacyService = ps;
        this.medicineService = medicineService;
    }

    @GetMapping("")
    public ResponseEntity<List<MedicineDTO>> test(){

        List<MedicineDTO> resultDTOS = new ArrayList<>();
        for (Medicine medicine : this.medicineService.findAll()) {
            resultDTOS.add(new MedicineDTO(
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
        MedicineInPharmacyToMedInPharmaDTO konverter = new MedicineInPharmacyToMedInPharmaDTO(new MedicineToMedicineDTO());
        List<MedicineInPharmacy> sviLekovi = ((PharmacyAdmin) user).getPharmacy().getMedicines();
        List<MedInPharmaDTO> povratna = konverter.convert(sviLekovi);
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }
    @GetMapping("/pharmacy/{id}")
    public ResponseEntity<List<MedInPharmaDTO>> getAllMedicinePharmacy(@PathVariable Long id) {
        Pharmacy apoteka = pharmacyService.findOne(id);
        MedicineInPharmacyToMedInPharmaDTO konverter = new MedicineInPharmacyToMedInPharmaDTO(new MedicineToMedicineDTO());
        List<MedInPharmaDTO> povratna = konverter.convert(apoteka.getMedicines());
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }
}
