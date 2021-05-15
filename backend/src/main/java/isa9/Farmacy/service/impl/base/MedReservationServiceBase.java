package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.MedInPharmaDTO;
import isa9.Farmacy.model.dto.MedReservationDTO;
import isa9.Farmacy.model.dto.MedReservationFormDTO;
import isa9.Farmacy.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

public abstract class MedReservationServiceBase implements MedReservationService {
    protected PharmacyService pharmacyService;
    protected MedicineService medicineService;
    protected UserService userService;
    protected InquiryService inquiryService;

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

    @Autowired
    public void setInquiryService(InquiryService inquiryService) { this.inquiryService = inquiryService; }

    @Override
    public MedReservation dispenseMedicine(MedReservation medReservation) {
        medReservation.setStatus(MedReservationStatus.TAKEN);

        // TODO: hardcoded pharmacist who 'issued', this will be fixed when authorisation is added

        Pharmacist pharmacist = new Pharmacist(); // = (Pharmacist) reservation.getMedicineInPharmacy().getPharmacy().getStaff().iterator().next().getDoctor();
        for (Work work : medReservation.getMedicineInPharmacy().getPharmacy().getStaff()){
            if (work.getDoctor().getRole().getName().equals("PHARMACIST")){
                pharmacist = (Pharmacist) work.getDoctor();
                break;
            }
        }
        medReservation.setWhoDispenses(pharmacist);

        int quantity = medReservation.getQuantity();
        Medicine medicine = medReservation.getMedicineInPharmacy().getMedicine();
        Patient patient = medReservation.getPatient();

        int points = patient.getPoints();
        points += quantity * medicine.getPoints();

        patient.setPoints(points);
        userService.save(patient);

        return save(medReservation);
    }

    @Override
    public Boolean isCancelable(MedReservation medReservation) {
        if (medReservation.getStatus() != MedReservationStatus.PENDING) return false;

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

        medReservation.setStatus(MedReservationStatus.CANCELED);
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
    public MedReservation reserveMedicine(MedReservationFormDTO reservationFormDTO, Long doctorId) {
        if (!isDateAfter(reservationFormDTO)) return null;
        if (reservationFormDTO.getQuantity() < 1) return null;

        Pharmacy pharmacy = pharmacyService.findOne(reservationFormDTO.getPharmacyId());
        Medicine medicine = medicineService.findOne(reservationFormDTO.getMedicineId());
        User user = userService.findOne(reservationFormDTO.getPatientId());
        if (!user.getRole().getName().equals("PATIENT")) return null;
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
                .status(MedReservationStatus.PENDING)
                .medicineInPharmacy(mip)
                .quantity(reservationFormDTO.getQuantity())
                .build();

        patient.getReservations().add(medReservation);
        int inStock = pharmacyService.reduceQuantity(pharmacy, medicine, reservationFormDTO.getQuantity());

        if (inStock == -1) return null;         // na stanju < poruceno
        if (inStock == 1) {                     // na stanju == 0
            User korisnik = userService.findOne(doctorId);
            if (korisnik.getClass() != Pharmacist.class && korisnik.getClass() != Dermatologist.class)
                return null;
            Pharmacy apoteka = pharmacyService.findOne(reservationFormDTO.getPharmacyId());
            Medicine lek = medicineService.findOne(reservationFormDTO.getMedicineId());

            InquiryMedicine upitZaLek = new InquiryMedicine();
            upitZaLek.setMedicine(lek);
            upitZaLek.setInquiryDate(LocalDateTime.now());
            upitZaLek.setDoctor((Doctor) korisnik);
            upitZaLek.setPharmacyId(reservationFormDTO.getPharmacyId());
            this.inquiryService.save(upitZaLek);
            apoteka.getInquiryMedicines().add(upitZaLek);
            pharmacyService.save(apoteka);
            return null;
        }
                                                // na stanju > poruceno
        userService.save(patient);
        return medReservation;

    }
}