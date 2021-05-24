package isa9.Farmacy.constants;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.ConsultingAppointmentReqDTO;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AppointmentConstants {

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
