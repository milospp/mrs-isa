package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.EPrescriptionDTO;
import isa9.Farmacy.model.dto.MedInPharmaDTO;
import isa9.Farmacy.model.dto.MedReservationDTO;
import isa9.Farmacy.model.dto.MedReservationFormDTO;
import isa9.Farmacy.service.*;
import isa9.Farmacy.utils.MailService;
import isa9.Farmacy.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class MedReservationServiceBase implements MedReservationService {
    protected PharmacyService pharmacyService;
    protected MedicineService medicineService;
    protected UserService userService;
    protected InquiryService inquiryService;
    protected MailService mailService;
    protected LoyaltyProgramService loyaltyProgramService;
    protected EPrescriptionService ePrescriptionService;

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

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Autowired
    public void setLoyaltyProgramService(LoyaltyProgramService loyaltyProgramService) {
        this.loyaltyProgramService = loyaltyProgramService;
    }

    @Autowired
    public void setePrescriptionService(EPrescriptionService e) {
        this.ePrescriptionService = e;
    }

    @Override
    @Transactional
    public MedReservation dispenseMedicine(String code) {
        MedReservation medReservation = getByCodeLocked(code);

        if (medReservation == null || !medReservation.getStatus().equals(MedReservationStatus.PENDING)){
            System.out.println("Cannot dispense this");
            return null;
        }

        medReservation.setStatus(MedReservationStatus.TAKEN);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
            System.out.println("nema ulogovanog farmaceuta");

        User user = (User) authentication.getPrincipal();
        System.out.println(user.getEmail());

        Pharmacist pharmacist = (Pharmacist) userService.getDoctorById(user.getId());//new Pharmacist(); // = (Pharmacist) reservation.getMedicineInPharmacy().getPharmacy().getStaff().iterator().next().getDoctor();
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
        mailService.sendDispensedReservationInfo(medReservation);

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
        Random random = Utils.getRand();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        System.out.println(generatedString);
        return generatedString;
    }

    @Override
    @Transactional
    public MedReservation reserveMedicine(MedReservationFormDTO reservationFormDTO, Long doctorId) {
        if (!isDateAfter(reservationFormDTO)) return null;
        if (reservationFormDTO.getQuantity() < 1) return null;

        Pharmacy pharmacy = pharmacyService.findOne(reservationFormDTO.getPharmacyId());
        Medicine medicine = medicineService.findOne(reservationFormDTO.getMedicineId());
        User user = userService.findOne(reservationFormDTO.getPatientId());
        if (!user.getRole().getName().equals("PATIENT")) return null;
        if (medicine.getPerscription().equals(DispencingMedicine.WITH_RECEIPT)) return null;
        Patient patient = (Patient) user;

        if (isPatientAlergic(patient, medicine)) return null;
        if (isPatientBlocked(patient)) return null;

        MedicineInPharmacy mip = pharmacyService.gedMedicineInPharmacy(pharmacy, medicine);

        LoyaltyProgram category = this.loyaltyProgramService.getCategoryOfUser(patient);

        MedReservation medReservation = MedReservation.builder()
                .id(null)
                .code(generateString())
                .patient(patient)
                .lastDate(reservationFormDTO.getExpirityDate())
                .reservationDate(LocalDate.now())
                .status(MedReservationStatus.PENDING)
                .medicineInPharmacy(mip)
                .quantity(reservationFormDTO.getQuantity())
                .loyaltyDiscount((category != null) ? category.getDiscount() : null)
                .build();

        patient.getReservations().add(medReservation);
        int inStock = pharmacyService.reduceQuantity(pharmacy, medicine, reservationFormDTO.getQuantity());

        if (inStock == -1) return null;         // na stanju < poruceno
        if (doctorId != null && inStock == 1) {                     // na stanju == 0
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
        mailService.sendReservationInfo(medReservation);

        userService.save(patient);
        return medReservation;

    }


    @Override
    @Transactional
    public MedReservation reserveMedicine(MedReservationFormDTO reservationFormDTO) {
        return this.reserveMedicine(reservationFormDTO, null);
    }

    @Override
    public List<MedReservation> getPatientsPurchases(Patient patient) {
        List<MedReservation> purchases = new ArrayList<>();

        for(MedReservation mr : patient.getReservations()){
            if(mr.getStatus().equals(MedReservationStatus.TAKEN)) purchases.add(mr);
        }

        return purchases;
    }

    @Override
    @Transactional
    public Integer eReserveMedicines(EPrescriptionDTO ePrescriptionDTO) {
        Patient patient = (Patient) this.userService.getLoggedInUser();
        Map<String, Integer> reservations = ePrescriptionDTO.getMedicines();
        Pharmacy pharmacy = this.pharmacyService.findOne(ePrescriptionDTO.getPharmacy().getId());
        int index = 0;

        List<MedReservation> medReservations = new ArrayList<>();

        for(Map.Entry<String, Integer> entry : reservations.entrySet()){
            Medicine medicine = null;
            for(Medicine m : this.medicineService.findAll()){
                if(m.getCode().equals(entry.getKey())) medicine = m;
            }

            if(isPatientAlergic(patient, medicine)) return 1;

            MedicineInPharmacy medicineInPharmacy = this.pharmacyService.gedMedicineInPharmacy(pharmacy, medicine);

            MedReservation medReservation = new MedReservation();

            medReservation.setReservationDate(LocalDate.now());
            medReservation.setLastDate(LocalDate.now());
            medReservation.setPatient(patient);
            medReservation.setMedicineInPharmacy(medicineInPharmacy);
            medReservation.setStatus(MedReservationStatus.TAKEN);
            medReservation.setCode(ePrescriptionDTO.getCode()+"_"+ ++index);
            medReservation.setQuantity(entry.getValue());

            LoyaltyProgram category = this.loyaltyProgramService.getCategoryOfUser(patient);
            medReservation.setLoyaltyDiscount((category != null) ?  category.getDiscount() : null);

            patient.getReservations().add(medReservation);
            medicineInPharmacy.setInStock(medicineInPharmacy.getInStock() - entry.getValue());
            medReservations.add(medReservation);
        }
        EPrescription ePrescription = EPrescription.builder()
                .id(null)
                .code(ePrescriptionDTO.getCode())
                .medicines(medReservations)
                .issueDate(LocalDate.now())
                .patient(patient)
                .build();

        mailService.sendEPrescriptionInfo(ePrescription);

        //this.ePrescriptionService.save(ePrescription);
        this.pharmacyService.save(pharmacy);
        this.userService.save(patient);

        return 0;
    }
}