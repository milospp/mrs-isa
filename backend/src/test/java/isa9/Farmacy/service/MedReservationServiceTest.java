package isa9.Farmacy.service;

import isa9.Farmacy.constants.*;
import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.MedReservationFormDTO;
import isa9.Farmacy.repository.MedReservationRepository;
import isa9.Farmacy.service.impl.db.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.time.LocalDate;
import java.util.*;

import static isa9.Farmacy.constants.MedReservationConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MedReservationServiceTest {
    @Mock
    private MedReservationRepository medReservationRepositoryMock;

    @Mock
    private dbMedicineService medicineService;

    @Mock
    private dbPharmacyService pharmacyService;

    @Mock
    private dbUserService userService;

    @Mock
    private dbLoyaltyProgramService loyaltyProgramService;

    @Mock
    private MedReservation medReservation;


    @InjectMocks
    private dbMedReservationService medReservationService;

    @Test
    public void testFindAll() {

        when(medReservationRepositoryMock.findAll()).thenReturn(Arrays.asList(new MedReservation(DB_ID, DB_CODE, null, DB_RES_DATE, DB_LAST_DATE, DB_STATUS, null, DB_QUANTITY, null)));

        List<MedReservation> medReservationList = medReservationService.findAll();

        assertThat(medReservationList).hasSize(1);

        verify(medReservationRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(medReservationRepositoryMock);

    }

    @Test
    public void testReservation() {
        medReservationService.setPharmacyService(pharmacyService);
        medReservationService.setMedicineService(medicineService);
        medReservationService.setUserService(userService);
        medReservationService.setLoyaltyProgramService(loyaltyProgramService);

        Medicine medicine = MedicineConstants.getMedicineExample();
        Pharmacy pharmacy = PharmacyConstants.getPharmacyExample();
        Patient patient = UserConstants.getPatient();

        MedicineInPharmacy mip = MedicineInPharmacyConstants.getMIP();

        pharmacy.setMedicines(new HashSet<>());
        pharmacy.getMedicines().add(mip);

        //when(medicineService.findAll()).thenReturn(Arrays.asList(MedicineConstants.getMedicineExample())));
        when(medicineService.findOne(1L)).thenReturn(medicine);

        //when(pharmacyService.findAll()).thenReturn(Arrays.asList(PharmacyConstants.getPharmacyExample()));
        when(pharmacyService.findOne(1L)).thenReturn(pharmacy);

        //when(userService.findAll()).thenReturn(Arrays.asList(UserConstants.getPatient());
        when(userService.findOne(1L)).thenReturn(patient);
        when(userService.getPatientByIdLocked(1L)).thenReturn(patient);
        when(userService.countActivePenalties(patient)).thenReturn(2);

        when(pharmacyService.gedMedicineInPharmacy(pharmacy, medicine)).thenReturn(mip);
        when(pharmacyService.reduceQuantity(pharmacy, medicine, 2)).thenReturn(0);

        MedReservationFormDTO medReservationFormDTO = new MedReservationFormDTO(1L,1L, 1L, 2, LocalDate.now().plusDays(10));
        MedReservation medReservation = medReservationService.reserveMedicine(medReservationFormDTO);

        MedReservation expectedMedRes = new MedReservation(null,medReservation.getCode(), (Patient) userService.findOne(1L), LocalDate.now(),LocalDate.now().plusDays(10), MedReservationStatus.PENDING, mip, 2, null);
        expectedMedRes.setLoyaltyDiscount(null);
        assertThat(medReservation).isEqualTo(expectedMedRes);

    }

    @Test
    public void testMedicineReservationNoAvailableAmount() {
        medReservationService.setPharmacyService(pharmacyService);
        medReservationService.setMedicineService(medicineService);
        medReservationService.setUserService(userService);

        Medicine medicine = MedicineConstants.getMedicineExample();
        Pharmacy pharmacy = PharmacyConstants.getPharmacyExample();
        Patient patient = UserConstants.getPatient();

        MedicineInPharmacy mip = MedicineInPharmacyConstants.getMIP();

        pharmacy.setMedicines(new HashSet<>());
        pharmacy.getMedicines().add(mip);

        //when(medicineService.findAll()).thenReturn(Arrays.asList(MedicineConstants.getMedicineExample())));
        when(medicineService.findOne(1L)).thenReturn(medicine);

        //when(pharmacyService.findAll()).thenReturn(Arrays.asList(PharmacyConstants.getPharmacyExample()));
        when(pharmacyService.findOne(1L)).thenReturn(pharmacy);

        //when(userService.findAll()).thenReturn(Arrays.asList(UserConstants.getPatient());
        when(userService.findOne(1L)).thenReturn(patient);

        when(pharmacyService.gedMedicineInPharmacy(pharmacy, medicine)).thenReturn(mip);
        when(pharmacyService.reduceQuantity(pharmacy, medicine, mip.getInStock() + 1)).thenReturn(-1);

        MedReservationFormDTO medReservationFormDTO = new MedReservationFormDTO(1L,1L, 1L, mip.getInStock() + 1, LocalDate.of(2021,6,10));
        MedReservation medReservation = medReservationService.reserveMedicine(medReservationFormDTO);

        assertThat(medReservation).isNull();

    }

    @Test
    public void testMedicineReservationDateBefore() {
        medReservationService.setPharmacyService(pharmacyService);
        medReservationService.setMedicineService(medicineService);
        medReservationService.setUserService(userService);

        Medicine medicine = MedicineConstants.getMedicineExample();
        Pharmacy pharmacy = PharmacyConstants.getPharmacyExample();
        Patient patient = UserConstants.getPatient();

        MedicineInPharmacy mip = MedicineInPharmacyConstants.getMIP();

        pharmacy.setMedicines(new HashSet<>());
        pharmacy.getMedicines().add(mip);

        //when(medicineService.findAll()).thenReturn(Arrays.asList(MedicineConstants.getMedicineExample())));
        when(medicineService.findOne(1L)).thenReturn(medicine);

        //when(pharmacyService.findAll()).thenReturn(Arrays.asList(PharmacyConstants.getPharmacyExample()));
        when(pharmacyService.findOne(1L)).thenReturn(pharmacy);

        //when(userService.findAll()).thenReturn(Arrays.asList(UserConstants.getPatient());
        when(userService.findOne(1L)).thenReturn(patient);

        when(pharmacyService.gedMedicineInPharmacy(pharmacy, medicine)).thenReturn(mip);
        when(pharmacyService.reduceQuantity(pharmacy, medicine, 2)).thenReturn(0);

        MedReservationFormDTO medReservationFormDTO = new MedReservationFormDTO(1L,1L, 1L, 2, LocalDate.now().minusDays(1));
        MedReservation medReservation = medReservationService.reserveMedicine(medReservationFormDTO);

        assertThat(medReservation).isNull();

    }

    @Test
    public void testMedicineReservationWithPenalties() {
        medReservationService.setPharmacyService(pharmacyService);
        medReservationService.setMedicineService(medicineService);
        medReservationService.setUserService(userService);

        Medicine medicine = MedicineConstants.getMedicineExample();
        Pharmacy pharmacy = PharmacyConstants.getPharmacyExample();
        Patient patient = UserConstants.getPatient();

        MedicineInPharmacy mip = MedicineInPharmacyConstants.getMIP();

        pharmacy.setMedicines(new HashSet<>());
        pharmacy.getMedicines().add(mip);

        //when(medicineService.findAll()).thenReturn(Arrays.asList(MedicineConstants.getMedicineExample())));
        when(medicineService.findOne(1L)).thenReturn(medicine);

        //when(pharmacyService.findAll()).thenReturn(Arrays.asList(PharmacyConstants.getPharmacyExample()));
        when(pharmacyService.findOne(1L)).thenReturn(pharmacy);

        //when(userService.findAll()).thenReturn(Arrays.asList(UserConstants.getPatient());
        when(userService.findOne(1L)).thenReturn(patient);
        when(userService.countActivePenalties(patient)).thenReturn(5);


        when(pharmacyService.gedMedicineInPharmacy(pharmacy, medicine)).thenReturn(mip);
        when(pharmacyService.reduceQuantity(pharmacy, medicine, 2)).thenReturn(0);

        MedReservationFormDTO medReservationFormDTO = new MedReservationFormDTO(1L,1L, 1L, 2, LocalDate.of(2021,6,10));
        MedReservation medReservation = medReservationService.reserveMedicine(medReservationFormDTO);

        assertThat(medReservation).isNull();

    }

    @Test
    public void testCheckForExpiredReservationsInPast() {
        MedReservation mr = new MedReservation();
        mr.setStatus(MedReservationStatus.PENDING);
        mr.setLastDate(LocalDate.now().minusDays(1));

        List<MedReservation> mrs = new ArrayList<>();
        mrs.add(mr);

        when(medReservationRepositoryMock.findAllByStatusAndLastDateBefore(MedReservationStatus.PENDING, LocalDate.now())).thenReturn(mrs);

        medReservationService.checkForExpiredReservations();

        assertThat(mr.getStatus().equals(MedReservationStatus.EXPIRED)).isTrue();
    }

    @Test
    public void testCheckForExpiredReservationsInFuture() {
        MedReservation mr = new MedReservation();
        mr.setStatus(MedReservationStatus.PENDING);
        mr.setLastDate(LocalDate.now().plusDays(3));

        List<MedReservation> mrs = new ArrayList<>();
        //mrs.add(mr);

        when(medReservationRepositoryMock.findAllByStatusAndLastDateBefore(MedReservationStatus.PENDING, LocalDate.now())).thenReturn(mrs);

        medReservationService.checkForExpiredReservations();

        assertThat(mr.getStatus().equals(MedReservationStatus.EXPIRED)).isFalse();
    }



}
