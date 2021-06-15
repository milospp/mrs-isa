package isa9.Farmacy.service;


import isa9.Farmacy.constants.*;
import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.ConsultingAppointmentReqDTO;
import isa9.Farmacy.model.dto.MedReservationFormDTO;
import isa9.Farmacy.repository.AppointmentRepository;
import isa9.Farmacy.repository.ExaminationRepository;
import isa9.Farmacy.repository.WorkRepository;
import isa9.Farmacy.service.impl.db.*;
import isa9.Farmacy.support.DateTimeDTO;
import isa9.Farmacy.utils.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.PartialResultException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppointmentSerivceTest {

    @Mock
    private AppointmentRepository appointmentRepositoryMock;

    @Mock
    private ExaminationRepository examinationRepository;

    @Mock
    private WorkRepository workRepositoryMock;

    @Mock
    private MailService mailServiceMock;

    @Mock
    private dbWorkService workService;

    @Mock
    private dbUserService userService;

    @Mock
    private dbExaminationService examinationService;

    @Mock
    private dbPharmacyService pharmacyService;

    @InjectMocks
    private dbAppointmentService appointmentService;

    @Test
    public void testConsultinAppointment() {
        appointmentService.setUserService(userService);
        appointmentService.setPharmacyService(pharmacyService);
        appointmentService.setMailService(mailServiceMock);
//        appointmentService.setMailService(mailServiceMock);
        appointmentService.setExaminationService(examinationService);

        Pharmacy pharmacy = PharmacyConstants.getPharmacyExample();
        Patient patient = UserConstants.getPatient();

        ConsultingAppointmentReqDTO consultingAppointmentReqDTO = AppointmentConstants.getConsultingReq();

        when(pharmacyService.findOne(1L)).thenReturn(pharmacy);
        when(userService.getPatientByIdLocked(1L)).thenReturn(patient);
        when(userService.findOne(1L)).thenReturn(patient);
        when(userService.isPatientBlocked(patient)).thenReturn(false);
        when(userService.getDoctorByIdLocked(11L)).thenReturn(UserConstants.getPharmacist());

//
        LocalDateTime start = consultingAppointmentReqDTO.getStartTime();
        LocalDateTime end = consultingAppointmentReqDTO.getStartTime().plusMinutes(consultingAppointmentReqDTO.getDurationInMins());

        when(workService.getWorksInInterval(start.toLocalTime(),end.toLocalTime())).thenReturn(AppointmentConstants.getWorksListExample());
//        when(appointmentService.getFreePharmacist(consultingAppointmentReqDTO)).thenReturn(AppointmentConstants.getWorksSetExample());

        Appointment appointment = appointmentService.bookConsultingAppointment(consultingAppointmentReqDTO, patient);

        Appointment expectedAppointment = AppointmentConstants.getExpectedAppointment();
        assertThat(appointment).isEqualTo(expectedAppointment);

    }

    @Test
    public void testConsultinAppointmentPatientHasPenalties() {
        appointmentService.setUserService(userService);
        appointmentService.setPharmacyService(pharmacyService);
        appointmentService.setMailService(mailServiceMock);

        Pharmacy pharmacy = PharmacyConstants.getPharmacyExample();
        Patient patient = UserConstants.getPatient();

        ConsultingAppointmentReqDTO consultingAppointmentReqDTO = AppointmentConstants.getConsultingReq();

        when(pharmacyService.findOne(1L)).thenReturn(pharmacy);
        when(userService.getPatientByIdLocked(1L)).thenReturn(patient);
        when(userService.isPatientBlocked(patient)).thenReturn(true);
        when(userService.getDoctorById(11L)).thenReturn(UserConstants.getPharmacist());
//
        LocalDateTime start = consultingAppointmentReqDTO.getStartTime();
        LocalDateTime end = consultingAppointmentReqDTO.getStartTime().plusMinutes(consultingAppointmentReqDTO.getDurationInMins());

        when(workService.getWorksInInterval(start.toLocalTime(),end.toLocalTime())).thenReturn(AppointmentConstants.getWorksListExample());
//        when(appointmentService.getFreePharmacist(consultingAppointmentReqDTO)).thenReturn(AppointmentConstants.getWorksSetExample());

        Appointment appointment = appointmentService.bookConsultingAppointment(consultingAppointmentReqDTO, patient);

        Appointment expectedAppointment = AppointmentConstants.getExpectedAppointment();
        assertThat(appointment).isNull();
    }


    @Test
    public void testConsultinAppointmentDateBefore() {
        appointmentService.setUserService(userService);
        appointmentService.setPharmacyService(pharmacyService);
        appointmentService.setMailService(mailServiceMock);

        Pharmacy pharmacy = PharmacyConstants.getPharmacyExample();
        Patient patient = UserConstants.getPatient();

        ConsultingAppointmentReqDTO consultingAppointmentReqDTO = AppointmentConstants.getConsultingReqDateBefore();

        when(pharmacyService.findOne(1L)).thenReturn(pharmacy);
        when(userService.findOne(1L)).thenReturn(patient);
        when(userService.isPatientBlocked(patient)).thenReturn(false);
        when(userService.getDoctorById(11L)).thenReturn(UserConstants.getPharmacist());
//
        LocalDateTime start = consultingAppointmentReqDTO.getStartTime();
        LocalDateTime end = consultingAppointmentReqDTO.getStartTime().plusMinutes(consultingAppointmentReqDTO.getDurationInMins());

        when(workService.getWorksInInterval(start.toLocalTime(),end.toLocalTime())).thenReturn(AppointmentConstants.getWorksListExample());
//        when(appointmentService.getFreePharmacist(consultingAppointmentReqDTO)).thenReturn(AppointmentConstants.getWorksSetExample());

        Appointment appointment = appointmentService.bookConsultingAppointment(consultingAppointmentReqDTO, patient);

        Appointment expectedAppointment = AppointmentConstants.getExpectedAppointment();
        assertThat(appointment).isNull();
    }

    @Test
    public void testConsultinAppointmentNoDuration() {
        appointmentService.setUserService(userService);
        appointmentService.setPharmacyService(pharmacyService);
        appointmentService.setMailService(mailServiceMock);

        Pharmacy pharmacy = PharmacyConstants.getPharmacyExample();
        Patient patient = UserConstants.getPatient();

        ConsultingAppointmentReqDTO consultingAppointmentReqDTO = AppointmentConstants.getConsultingReqNoDuration();

        when(pharmacyService.findOne(1L)).thenReturn(pharmacy);
        when(userService.findOne(1L)).thenReturn(patient);
        when(userService.isPatientBlocked(patient)).thenReturn(false);
        when(userService.getDoctorById(11L)).thenReturn(UserConstants.getPharmacist());
//
        LocalDateTime start = consultingAppointmentReqDTO.getStartTime();
        LocalDateTime end = consultingAppointmentReqDTO.getStartTime().plusMinutes(consultingAppointmentReqDTO.getDurationInMins());

        when(workService.getWorksInInterval(start.toLocalTime(),end.toLocalTime())).thenReturn(AppointmentConstants.getWorksListExample());
//        when(appointmentService.getFreePharmacist(consultingAppointmentReqDTO)).thenReturn(AppointmentConstants.getWorksSetExample());

        Appointment appointment = appointmentService.bookConsultingAppointment(consultingAppointmentReqDTO, patient);

        Appointment expectedAppointment = AppointmentConstants.getExpectedAppointment();
        assertThat(appointment).isNull();
    }

    @Test
    public void testConsultinAppointmentNoResult1() {
        appointmentService.setUserService(userService);
        appointmentService.setPharmacyService(pharmacyService);
        appointmentService.setMailService(mailServiceMock);

        Pharmacy pharmacy = PharmacyConstants.getPharmacyExample();
        Patient patient = UserConstants.getPatient();

        ConsultingAppointmentReqDTO consultingAppointmentReqDTO = AppointmentConstants.getConsultingReq();
        consultingAppointmentReqDTO.setPharmacistId(0L);

        when(pharmacyService.findOne(1L)).thenReturn(pharmacy);
        when(userService.getPatientByIdLocked(1L)).thenReturn(patient);
        when(userService.isPatientBlocked(patient)).thenReturn(false);
        when(userService.getDoctorById(11L)).thenReturn(UserConstants.getPharmacist());
//
        LocalDateTime start = consultingAppointmentReqDTO.getStartTime();
        LocalDateTime end = consultingAppointmentReqDTO.getStartTime().plusMinutes(consultingAppointmentReqDTO.getDurationInMins());

        when(workService.getWorksInInterval(start.toLocalTime(),end.toLocalTime())).thenReturn(AppointmentConstants.getWorksListExample());
//        when(appointmentService.getFreePharmacist(consultingAppointmentReqDTO)).thenReturn(AppointmentConstants.getWorksSetExample());

        Appointment appointment = appointmentService.bookConsultingAppointment(consultingAppointmentReqDTO, patient);

        Appointment expectedAppointment = AppointmentConstants.getExpectedAppointment();
        assertThat(appointment).isNull();
    }

    @Test
    public void testConsultinAppointmentNoResult2() {
        appointmentService.setUserService(userService);
        appointmentService.setPharmacyService(pharmacyService);
        appointmentService.setMailService(mailServiceMock);

        Pharmacy pharmacy = PharmacyConstants.getPharmacyExample();
        Patient patient = UserConstants.getPatient();

        ConsultingAppointmentReqDTO consultingAppointmentReqDTO = AppointmentConstants.getConsultingReqNoDuration();

        when(pharmacyService.findOne(1L)).thenReturn(pharmacy);
        when(userService.findOne(1L)).thenReturn(patient);
        when(userService.isPatientBlocked(patient)).thenReturn(false);
        when(userService.getDoctorById(11L)).thenReturn(UserConstants.getPharmacist());
//
        LocalDateTime start = consultingAppointmentReqDTO.getStartTime();
        LocalDateTime end = consultingAppointmentReqDTO.getStartTime().plusMinutes(consultingAppointmentReqDTO.getDurationInMins());

        when(workService.getWorksInInterval(start.toLocalTime(),end.toLocalTime())).thenReturn(new ArrayList<>());
//        when(appointmentService.getFreePharmacist(consultingAppointmentReqDTO)).thenReturn(AppointmentConstants.getWorksSetExample());

        Appointment appointment = appointmentService.bookConsultingAppointment(consultingAppointmentReqDTO, patient);

        Appointment expectedAppointment = AppointmentConstants.getExpectedAppointment();
        assertThat(appointment).isNull();
    }

    @Test
    public void testDermAppointment() {
        appointmentService.setUserService(userService);
        appointmentService.setMailService(mailServiceMock);
        appointmentService.setExaminationService(examinationService);

        Pharmacy pharmacy = PharmacyConstants.getPharmacyExample();
        Patient patient = UserConstants.getPatient();
        Appointment appointment = AppointmentConstants.getFreeAppointment();

        when(appointmentRepositoryMock.findById(1L)).thenReturn(Optional.of(appointment));
        when(pharmacyService.findOne(1L)).thenReturn(pharmacy);
        when(userService.findOne(1L)).thenReturn(patient);
        when(userService.countActivePenalties(patient)).thenReturn(2);

        Appointment appointmentResult = appointmentService.bookAnAppointment(1L, 1L);

        Appointment expectedAppointment = AppointmentConstants.getFreeAppointment();
        Examination expectedExamination = new Examination();
        expectedExamination.setPatient(patient);
        expectedExamination.setStatus(ExaminationStatus.PENDING);
        expectedAppointment.setExamination(expectedExamination);

        assertThat(appointmentResult).isEqualTo(expectedAppointment);
    }



    @Test
    public void testDermAppointmentWithAppointment() {
        appointmentService.setUserService(userService);
        appointmentService.setMailService(mailServiceMock);
        appointmentService.setExaminationService(examinationService);

        Pharmacy pharmacy = PharmacyConstants.getPharmacyExample();
        Patient patient = UserConstants.getPatient();
        Appointment appointment = AppointmentConstants.getFreeAppointmentWithExamination();

        when(appointmentRepositoryMock.findById(1L)).thenReturn(Optional.of(appointment));
        when(pharmacyService.findOne(1L)).thenReturn(pharmacy);
        when(userService.findOne(1L)).thenReturn(patient);
        when(userService.countActivePenalties(patient)).thenReturn(2);

        Appointment appointmentResult = appointmentService.bookAnAppointment(1L, 1L);

        Appointment expectedAppointment = AppointmentConstants.getFreeAppointment();
        Examination expectedExamination = new Examination();
        expectedExamination.setPatient(patient);
        expectedExamination.setStatus(ExaminationStatus.PENDING);
        expectedAppointment.setExamination(expectedExamination);

        assertThat(appointmentResult).isEqualTo(expectedAppointment);
    }


    @Test
    public void testDermAppointmentTaken() {
        appointmentService.setUserService(userService);
        appointmentService.setMailService(mailServiceMock);

        Pharmacy pharmacy = PharmacyConstants.getPharmacyExample();
        Patient patient = UserConstants.getPatient();
        Appointment appointment = AppointmentConstants.getHeldAppointment();

        when(appointmentRepositoryMock.findById(1L)).thenReturn(Optional.of(appointment));
        when(pharmacyService.findOne(1L)).thenReturn(pharmacy);
        when(userService.findOne(1L)).thenReturn(patient);

        Appointment appointmentResult = appointmentService.bookAnAppointment(1L, 1L);


        assertThat(appointmentResult).isNull();
    }


    @Test
    public void testDermAppointmentPatientBlocked() {
        appointmentService.setUserService(userService);
        appointmentService.setMailService(mailServiceMock);

        Pharmacy pharmacy = PharmacyConstants.getPharmacyExample();
        Patient patient = UserConstants.getPatient();
        Appointment appointment = AppointmentConstants.getFreeAppointment();

        when(appointmentRepositoryMock.findById(1L)).thenReturn(Optional.of(appointment));
        when(pharmacyService.findOne(1L)).thenReturn(pharmacy);
        when(userService.findOne(1L)).thenReturn(patient);
        when(userService.isPatientBlocked(patient)).thenReturn(true);

        Appointment appointmentResult = appointmentService.bookAnAppointment(1L, 1L);
        assertThat(appointmentResult).isNull();
    }

    @Test
    public void testDermAppointmentDoctorAsPatient() {
        appointmentService.setUserService(userService);
        appointmentService.setMailService(mailServiceMock);

        Pharmacy pharmacy = PharmacyConstants.getPharmacyExample();
        Patient patient = UserConstants.getPatient();
        Appointment appointment = AppointmentConstants.getFreeAppointment();

        when(appointmentRepositoryMock.findById(1L)).thenReturn(Optional.of(appointment));
        when(pharmacyService.findOne(1L)).thenReturn(pharmacy);
        when(userService.findOne(1L)).thenReturn(patient);
        when(userService.countActivePenalties(patient)).thenReturn(2);

        Appointment appointmentResult = appointmentService.bookAnAppointment(11L, 1L);


        assertThat(appointmentResult).isNull();
    }

}
