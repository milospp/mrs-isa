package isa9.Farmacy.controller;

import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.model.User;
import isa9.Farmacy.model.dto.MedicineDTO;
import isa9.Farmacy.model.dto.UserDTO;
import isa9.Farmacy.service.MedicineService;
import isa9.Farmacy.service.impl.base.MedicineServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/medicines")
public class MedicineController {

    private final MedicineService medicineService;

    @Autowired
    public MedicineController(MedicineService medicineService){
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


}
