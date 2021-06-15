package isa9.Farmacy.constants;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AppointmentConstants {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm:ss");
    private static final DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final Long AP_FREE_ID = 6L;
    public static final String AP_FREE_START_TIME = LocalDate.now().atTime(15,0).plusDays(20).format(formatter);
    public static final double AP_FREE_PRICE = 350.0;
    public static final int AP_FREE_DURATION = 30;
    public static final String AP_FREE_TYPE = "EXAMINATION";
    public static final Long AP_FREE_DOCTOR_ID = 11L;
    public static final String AP_FREE_DOCTOR_NAME = "Gordan";
    public static final String AP_FREE_DOCTOR_SURNAME = "Popović";
    public static final Long AP_FREE_PHARMACY_ID = 1L;
    public static final String AP_FREE_PHARMACY_NAME = "Prima";
    public static final Object AP_FREE_EXAMINATION = null;


    public static final Long AP_DERM_ID = 6L;
    public static final LocalDateTime AP_DERM_START_TIME = LocalDate.now().plusDays(20).atTime(15,0);
    public static final LocalDateTime AP_CONS_START_TIME = LocalDate.now().plusDays(60).atTime(11,0);


    public static final Long AP_PH_ID = 7L;
    public static final String AP_PH_START_TIME = LocalTime.of(8,30).format(timeformatter);
    public static final String AP_PH_START_DATE = LocalDate.now().plusDays(1).format(dateformatter);
    public static final double AP_PH_PRICE = 300.0;
    public static final int AP_PH_DURATION = 30;
    public static final String AP_PH_TYPE_FOR_CLASS = "counseling";
    public static final Long AP_PH_DOCTOR_ID = 12L;
    public static final String AP_PH_DOCTOR_NAME = "Milisav";
    public static final String AP_PH_DOCTOR_SURNAME = "Nikolić";
    public static final String AP_PH_PATIENT_NAME = "Maja";
    public static final String AP_PH_PATIENT_SURNAME = "Marković";
    public static final Long AP_PH_PHARMACY_ID = 1L;
    public static final String AP_PH_PHARMACY_NAME = "Prima";
    public static final Object AP_PH_EXAMINATION_STATUS = "PENDING";
    public static final String AP_DERM_START_DATE_FREE = LocalDate.of(2021,6,7).toString();
    public static final String AP_DERM_START_DATE_TAKEN = LocalDate.now().plusDays(3).toString();



    public static ConsultingAppointmentReqDTO getConsulstingRequest() {
        ConsultingAppointmentReqDTO request = new ConsultingAppointmentReqDTO();
        request.setDurationInMins(45);
        request.setPharmacistId(12L);
        request.setPharmacyId(1L);
        request.setStartTime(AP_CONS_START_TIME);
        return request;
    }

    public static AppointmentDTO getConsultingResult() {
        PatientDTO patient = new PatientDTO();
        patient.setId(1L);
        DoctorDTO doctotr = new DoctorDTO();
        doctotr.setId(12L);

        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setDoctor(doctotr);
        appointmentDTO.setDurationInMins(45);
        appointmentDTO.setExamination(ExaminationDTO.builder().patient( patient ).status(ExaminationStatus.PENDING).type(TypeOfReview.COUNSELING).build() );
        appointmentDTO.setPharmacy(PharmacyDTO.builder().id(1L).build());
        appointmentDTO.setPrice(30);
        appointmentDTO.setStartTime(AP_CONS_START_TIME);
        appointmentDTO.setType(TypeOfReview.COUNSELING);
        return appointmentDTO;
    }


    public static AppointmentDTO getDermAppointmentResult() {

        PatientDTO patient = new PatientDTO();
        patient.setId(1L);
        DoctorDTO doctotr = new DoctorDTO();
        doctotr.setId(11L);

        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setDoctor(doctotr);
        appointmentDTO.setDurationInMins(30);
        appointmentDTO.setExamination(ExaminationDTO.builder().patient( patient ).status(ExaminationStatus.PENDING).type(TypeOfReview.EXAMINATION).build() );
        appointmentDTO.setPharmacy(PharmacyDTO.builder().id(1L).build());
        appointmentDTO.setPrice(350);
        appointmentDTO.setStartTime(AP_DERM_START_TIME);
        appointmentDTO.setType(TypeOfReview.EXAMINATION);
        return appointmentDTO;
    }


    public static ConsultingAppointmentReqDTO getConsultingReq(){
        ConsultingAppointmentReqDTO consultingAppointmentReqDTO = new ConsultingAppointmentReqDTO();
        consultingAppointmentReqDTO.setPharmacistId(1L);
        consultingAppointmentReqDTO.setPharmacyId(1L);
        consultingAppointmentReqDTO.setDurationInMins(30);
        consultingAppointmentReqDTO.setPharmacistId(11L);
        consultingAppointmentReqDTO.setStartTime(LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.MINUTES));

        return consultingAppointmentReqDTO;
    }

    public static ConsultingAppointmentReqDTO getConsultingReqDateBefore(){
        ConsultingAppointmentReqDTO consultingAppointmentReqDTO = getConsultingReq();
        consultingAppointmentReqDTO.setStartTime(LocalDateTime.now().minusDays(1));
        return consultingAppointmentReqDTO;
    }


    public static ConsultingAppointmentReqDTO getConsultingReqNoDuration(){
        ConsultingAppointmentReqDTO consultingAppointmentReqDTO = getConsultingReq();
        consultingAppointmentReqDTO.setDurationInMins(0);
        return consultingAppointmentReqDTO;
    }


        public static Set<Work> getWorksSetExample() {
        Set<Work> workSet = new HashSet<>();

        Work w1 = new Work();
        w1.setId(1L);
        w1.setDoctor(UserConstants.getPharmacist());
        w1.setPharmacy(PharmacyConstants.getPharmacyExample());
        w1.setStartHour(LocalTime.now().minusHours(3));
        w1.setEndHour(LocalTime.now().plusHours(3));

        workSet.add(w1);
        return workSet;

    }

    public static List<Work> getWorksListExample() {
        List<Work> workSet = new ArrayList<>();

        Work w1 = new Work();
        w1.setId(1L);
        w1.setDoctor(UserConstants.getPharmacist());
        w1.setPharmacy(PharmacyConstants.getPharmacyExample());
        w1.setStartHour(LocalTime.now().minusHours(3));
        w1.setEndHour(LocalTime.now().plusHours(3));

        workSet.add(w1);
        return workSet;
    }

    public static Appointment getFreeAppointment() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setDoctor(UserConstants.getPharmacist());
        appointment.setPharmacy(PharmacyConstants.getPharmacyExample());

        return appointment;
    }

    public static Appointment getHeldAppointment() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setDoctor(UserConstants.getPharmacist());
        appointment.setPharmacy(PharmacyConstants.getPharmacyExample());
        Examination examination = new Examination();
        examination.setPatient(UserConstants.getPatient());
        examination.setStatus(ExaminationStatus.HELD);
        appointment.setExamination(examination);

        return appointment;
    }

    public static Appointment getFreeAppointmentWithExamination() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setDoctor(UserConstants.getPharmacist());
        appointment.setPharmacy(PharmacyConstants.getPharmacyExample());
        appointment.setExamination(new Examination());

        return appointment;
    }

    public static Appointment getExpectedAppointment() {
        Appointment appointment = new Appointment();
        appointment.setStartTime(LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.MINUTES));
        appointment.setDurationInMins(30);
        appointment.setPrice(250);
        appointment.setType(TypeOfReview.COUNSELING);
        appointment.setDoctor(UserConstants.getPharmacist());
        appointment.setPharmacy(PharmacyConstants.getPharmacyExample());

        Examination examination = new Examination();
        examination.setPatient(UserConstants.getPatient());
        examination.setStatus(ExaminationStatus.PENDING);
        examination.setTherapy(new HashSet<>());
        appointment.setExamination(examination);
        return appointment;
    }
}
