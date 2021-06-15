package isa9.Farmacy.service;

import isa9.Farmacy.constants.MedicineConstants;
import isa9.Farmacy.constants.MedicineInPharmacyConstants;
import isa9.Farmacy.constants.PharmacyConstants;
import isa9.Farmacy.constants.UserConstants;
import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.MedReservationFormDTO;
import isa9.Farmacy.repository.MedReservationRepository;
import isa9.Farmacy.repository.VacationRepository;
import isa9.Farmacy.service.impl.db.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static isa9.Farmacy.constants.MedReservationConstants.*;
import static isa9.Farmacy.constants.MedReservationConstants.DB_QUANTITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VacationServiceTest {

    @Mock
    private VacationRepository vacationRepositoryMock;

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
    private dbVacationService vacationService;

    @Test
    public void testGetAcceptedForDoctor() {
        vacationService.setUserService(userService);

        Doctor doctor = UserConstants.getDermatologist();
        Pharmacy pharmacy = PharmacyConstants.getPharmacyExample();

        List<Vacation> vacations = new ArrayList<>();
        vacations.add(Vacation.builder()
                .type(TypeOfRest.REST)
                .status(VacationRequestStatus.ACCEPTED)
                .reason("reason1")
                .pharmacy(pharmacy)
                .doctor(doctor)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(10))
                .build());

        when(userService.getDoctorById(1L)).thenReturn(doctor);
        when(vacationRepositoryMock.findByDoctor(doctor)).thenReturn(vacations);

        List<Vacation> accepted = vacationService.getAcceptedForDoctor(1L);

        assertThat(accepted.size() == 1).isTrue();
        assertThat(accepted.get(0).getReason().equals("reason1")).isTrue();

    }

    @Test
    public void testGetIfAcceptedInIntervalForDoctor() {
        vacationService.setUserService(userService);

        Doctor doctor = UserConstants.getDermatologist();
        Pharmacy pharmacy = PharmacyConstants.getPharmacyExample();

        List<Vacation> vacations = new ArrayList<>();
        vacations.add(Vacation.builder()
                .type(TypeOfRest.REST)
                .status(VacationRequestStatus.ACCEPTED)
                .reason("reason1")
                .pharmacy(pharmacy)
                .doctor(doctor)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(10))
                .build());

        when(userService.getDoctorById(1L)).thenReturn(doctor);
        when(vacationRepositoryMock.findByDoctor(doctor)).thenReturn(vacations);

        List<Vacation> accepted = vacationService.getAcceptedForDoctor(1L);

        assertThat(accepted.size() == 1).isTrue();
        assertThat(accepted.get(0).getReason().equals("reason1")).isTrue();

        boolean acceptedBool = vacationService.getIfAcceptedInIntervalForDoctor(1L, LocalDate.now().minusDays(1), LocalDate.now().plusDays(3));

        assertThat(acceptedBool).isTrue();

    }

    @Test
    public void testGetIfAcceptedInIntervalForDoctorWaiting() {
        vacationService.setUserService(userService);

        Doctor doctor = UserConstants.getDermatologist();
        Pharmacy pharmacy = PharmacyConstants.getPharmacyExample();

        List<Vacation> vacations = new ArrayList<>();
        vacations.add(Vacation.builder()
                .type(TypeOfRest.REST)
                .status(VacationRequestStatus.WAITING)
                .reason("reason1")
                .pharmacy(pharmacy)
                .doctor(doctor)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(10))
                .build());

        when(userService.getDoctorById(1L)).thenReturn(doctor);
        when(vacationRepositoryMock.findByDoctor(doctor)).thenReturn(vacations);

        List<Vacation> accepted = vacationService.getAcceptedForDoctor(1L);

        assertThat(accepted.isEmpty()).isTrue();

        boolean acceptedBool = vacationService.getIfAcceptedInIntervalForDoctor(1L, LocalDate.now().minusDays(1), LocalDate.now().plusDays(3));

        assertThat(acceptedBool).isFalse();

    }

}
