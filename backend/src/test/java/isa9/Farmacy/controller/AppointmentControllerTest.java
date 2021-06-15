package isa9.Farmacy.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import isa9.Farmacy.constants.AppointmentConstants;
import isa9.Farmacy.constants.MedicineConstants;
import isa9.Farmacy.model.dto.AppointmentDTO;
import isa9.Farmacy.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WithMockUser(username="p1@maildrop.cc" , authorities = {"PATIENT"})
@SpringBootTest
public class AppointmentControllerTest {

    private static final String URL_PREFIX = "/api/appointments";

    private MediaType contentType =  new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
   // @WithMockUser(roles = {"PATIENT"})
    public void testGetFreeDernatologist() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/dermatologist/free")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(1)))
		        .andExpect(jsonPath("$.[*].id").value(hasItem(AppointmentConstants.AP_FREE_ID.intValue())))
                .andExpect(jsonPath("$.[*].startTime").value(hasItem(AppointmentConstants.AP_FREE_START_TIME.toString())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(AppointmentConstants.AP_FREE_PRICE)))
                .andExpect(jsonPath("$.[*].type").value(hasItem(AppointmentConstants.AP_FREE_TYPE)))
                .andExpect(jsonPath("$.[*].doctor.id").value(hasItem(AppointmentConstants.AP_FREE_DOCTOR_ID.intValue())))
                .andExpect(jsonPath("$.[*].doctor.name").value(hasItem(AppointmentConstants.AP_FREE_DOCTOR_NAME)))
                .andExpect(jsonPath("$.[*].doctor.surname").value(hasItem(AppointmentConstants.AP_FREE_DOCTOR_SURNAME)))
                .andExpect(jsonPath("$.[*].pharmacy.id").value(hasItem(AppointmentConstants.AP_FREE_PHARMACY_ID.intValue())))
                .andExpect(jsonPath("$.[*].pharmacy.name").value(hasItem(AppointmentConstants.AP_FREE_PHARMACY_NAME.toString())))
                .andExpect(jsonPath("$.[*].durationInMins").value(hasItem(AppointmentConstants.AP_FREE_DURATION)))
                .andExpect(jsonPath("$.[*].examination").value(hasItem(AppointmentConstants.AP_FREE_EXAMINATION)));

    }

    @Test
    @Transactional
    @Rollback(true)
    public void testBookDermatologist() throws Exception {
        AppointmentDTO result = AppointmentConstants.getDermAppointmentResult();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm:ss");

        mockMvc.perform(post(URL_PREFIX + "/" + AppointmentConstants.AP_DERM_ID + "/book")
                    .contentType(contentType)
                    .content("{\"id\": \"1\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))

                .andExpect(jsonPath("$.doctor.id").value(result.getDoctor().getId()))
                .andExpect(jsonPath("$.durationInMins").value(result.getDurationInMins()))
                .andExpect(jsonPath("$.examination.patient.id").value(result.getExamination().getPatient().getId()))
                .andExpect(jsonPath("$.examination.status").value(result.getExamination().getStatus().toString()))
                .andExpect(jsonPath("$.examination.type").value(result.getExamination().getType().toString()))
                .andExpect(jsonPath("$.price").value(result.getPrice()))
                .andExpect(jsonPath("$.startTime").value(result.getStartTime().format(formatter)));

    }

    @Test
    @Transactional
    @Rollback(true)
    public void testBookPharmacist() throws Exception {
        String jsonRequest = TestUtil.toJson(AppointmentConstants.getConsulstingRequest());

        AppointmentDTO result = AppointmentConstants.getConsultingResult();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm:ss");

        mockMvc.perform(post(URL_PREFIX + "/derm-examination")
                    .contentType(contentType)
                    .content(jsonRequest)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))

                .andExpect(jsonPath("$.doctor.id").value(result.getDoctor().getId()))
                .andExpect(jsonPath("$.durationInMins").value(result.getDurationInMins()))
                .andExpect(jsonPath("$.examination.patient.id").value(result.getExamination().getPatient().getId()))
                .andExpect(jsonPath("$.examination.status").value(result.getExamination().getStatus().toString()))
                .andExpect(jsonPath("$.examination.type").value(result.getExamination().getType().toString()))
                .andExpect(jsonPath("$.price").value(result.getPrice()))
                .andExpect(jsonPath("$.startTime").value(result.getStartTime().format(formatter)));

    }

    @Test
    @WithMockUser(username="drmili@maildrop.cc" , authorities = {"PHARMACIST"})
    public void testCalendarPh() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/calendar/pharm/12"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.[*].id").value(hasItem(AppointmentConstants.AP_PH_ID.intValue())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(AppointmentConstants.AP_PH_START_DATE)))
                .andExpect(jsonPath("$.[*].price").value(hasItem(AppointmentConstants.AP_PH_PRICE)))
                .andExpect(jsonPath("$.[*].patientName").value(hasItem(AppointmentConstants.AP_PH_PATIENT_NAME)))
                .andExpect(jsonPath("$.[*].patientSurname").value(hasItem(AppointmentConstants.AP_PH_PATIENT_SURNAME)))
                .andExpect(jsonPath("$.[*].pharmacyName").value(hasItem(AppointmentConstants.AP_PH_PHARMACY_NAME)))
                .andExpect(jsonPath("$.[*].durationInMins").value(hasItem(AppointmentConstants.AP_PH_DURATION)));
    }

    @Test
    @WithMockUser(username="gordop@maildrop.cc" , authorities = {"DERMATOLOGIST"})
    public void testCalendarDerm() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/calendar/derm/11/pharmacy/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.[*].id").value(hasItem(AppointmentConstants.AP_FREE_DOCTOR_ID.intValue())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(AppointmentConstants.AP_DERM_START_DATE_TAKEN)))
                .andExpect(jsonPath("$.[*].price").value(hasItem(AppointmentConstants.AP_FREE_PRICE)))
                .andExpect(jsonPath("$.[*].patientName").value(hasItem("")))
                .andExpect(jsonPath("$.[*].patientSurname").value(hasItem("")))
                .andExpect(jsonPath("$.[*].pharmacyName").value(hasItem(AppointmentConstants.AP_PH_PHARMACY_NAME)))
                .andExpect(jsonPath("$.[*].durationInMins").value(hasItem(AppointmentConstants.AP_PH_DURATION)));
    }

    @Test
    @WithMockUser(username="gordop@maildrop.cc" , authorities = {"DERMATOLOGIST"})
    public void testCalendarFreeDerm() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/calendar/free-derm/11/pharmacy/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.[*].id").value(hasItem(AppointmentConstants.AP_FREE_DOCTOR_ID.intValue())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(AppointmentConstants.AP_DERM_START_DATE_FREE)))
                .andExpect(jsonPath("$.[*].price").value(hasItem(AppointmentConstants.AP_FREE_PRICE)))
                .andExpect(jsonPath("$.[*].patientName").value(hasItem("")))
                .andExpect(jsonPath("$.[*].patientSurname").value(hasItem("")))
                .andExpect(jsonPath("$.[*].pharmacyName").value(hasItem(AppointmentConstants.AP_PH_PHARMACY_NAME)))
                .andExpect(jsonPath("$.[*].durationInMins").value(hasItem(AppointmentConstants.AP_PH_DURATION)));

    }

}
