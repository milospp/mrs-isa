package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.MedReservationFormDTO;
import isa9.Farmacy.service.MedicineService;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Random;

public abstract class MedicineServiceBase implements MedicineService {

    protected PharmacyService pharmacyService;
    protected UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String generateString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        System.out.println(generatedString);
        return generatedString;
    }

    @Autowired
    public void setPharmacyService(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }

    @Override
    public MedReservation reserveMedicine(MedReservationFormDTO reservationFormDTO) {
        Pharmacy pharmacy = pharmacyService.findOne(reservationFormDTO.getPharmacyId());
        Medicine medicine = findOne(reservationFormDTO.getMedicineId());
        User user = userService.findOne(reservationFormDTO.getPatientId());
        if (!user.getRole().equals(UserRole.PATIENT)) return null;
        Patient patient = (Patient) user;

        MedicineInPharmacy mip = pharmacyService.gedMedicineInPharmacy(pharmacy, medicine);

        MedReservation medReservation = MedReservation.builder()
                .id(null)
                .code(generateString())
                .patient(patient)
                .lastDate(reservationFormDTO.getExpirityDate())
                .reservationDate(LocalDate.now())
                .taken(false)
                .canceled(false)
                .medicineInPharmacy(mip)
                .quantity(reservationFormDTO.getQuantity())
                .build();

        patient.getReservations().add(medReservation);
        boolean inStock = pharmacyService.reduceQuantity(pharmacy, medicine, reservationFormDTO.getQuantity());

        if (!inStock) return null;

        userService.save(patient);
        return medReservation;

    }
}
