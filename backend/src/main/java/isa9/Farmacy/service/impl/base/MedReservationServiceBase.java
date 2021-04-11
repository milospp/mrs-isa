package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.MedReservationDTO;
import isa9.Farmacy.model.dto.MedReservationFormDTO;
import isa9.Farmacy.service.MedReservationService;
import isa9.Farmacy.service.MedicineService;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Random;

public abstract class MedReservationServiceBase implements MedReservationService {
    protected PharmacyService pharmacyService;
    protected MedicineService medicineService;
    protected UserService userService;

    @Autowired
    public void setMedicineService(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPharmacyService(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }

    @Override
    public Boolean isCancelable(MedReservation medReservation) {
        if (medReservation.isCanceled()) return false;
        if (medReservation.isTaken()) return false;

        LocalDate lastDate = medReservation.getLastDate();
        LocalDate limitDate = lastDate.minusDays(1);
        if (limitDate.isBefore(LocalDate.now())) return false;


        return true;
    }

    @Override
    public MedReservation cancel(Long medReservationId) {
        MedReservation medReservation = findOne(medReservationId);
        Boolean cancelable = isCancelable(medReservation);
        if (!cancelable) return medReservation;

        medReservation.setCanceled(true);
        MedicineInPharmacy mip = medReservation.getMedicineInPharmacy();
        Pharmacy pharmacy = mip.getPharmacy();
        mip.setInStock(mip.getInStock() + medReservation.getQuantity());
        pharmacyService.save(pharmacy);
        save(medReservation);

        return medReservation;
    }

    public boolean isDateAfter(MedReservationFormDTO reservationFormDTO) {
        if (reservationFormDTO.getExpirityDate() == null) return false;
        if (reservationFormDTO.getExpirityDate().isBefore(LocalDate.now().plusDays(1))) return false;

        return true;
    }

    public boolean isPatientAlergic(Patient patient, Medicine medicine){
        for(Medicine m : patient.getAllergies()){
           if (m.equals(medicine)){
               return true;
           }
        }
        return false;
    }

    public boolean isPatientBlocked(Patient patient) {
        if (userService.countActivePenalties(patient) >= 3) return true;

        return false;
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

    @Override
    public MedReservation reserveMedicine(MedReservationFormDTO reservationFormDTO) {
        if (!isDateAfter(reservationFormDTO)) return null;
        if (reservationFormDTO.getQuantity() < 1) return null;

        Pharmacy pharmacy = pharmacyService.findOne(reservationFormDTO.getPharmacyId());
        Medicine medicine = medicineService.findOne(reservationFormDTO.getMedicineId());
        User user = userService.findOne(reservationFormDTO.getPatientId());
        if (!user.getRole().equals(UserRole.PATIENT)) return null;
        Patient patient = (Patient) user;

        if (isPatientAlergic(patient, medicine)) return null;
        if (isPatientBlocked(patient)) return null;

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