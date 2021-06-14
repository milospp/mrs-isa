package isa9.Farmacy.controller;

import isa9.Farmacy.constants.AppointmentConstants;
import isa9.Farmacy.constants.MedicineConstants;
import isa9.Farmacy.model.dto.MedReservationDTO;
import isa9.Farmacy.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.Rollback;


import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WithMockUser(username="p1@maildrop.cc" , authorities = {"PATIENT"})
@SpringBootTest
public class MedicineControllerTest {

    private static final String URL_PREFIX = "/api/medicines";

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
    @WithMockUser(username="p1@maildrop.cc" , authorities = {"PATIENT"})
    @Transactional
    @Rollback(true)
    public void testReserveMedicineAsPatient() throws Exception {
        String jsonRequest = TestUtil.toJson(MedicineConstants.getReservationForm());
        MedReservationDTO result = MedicineConstants.getReservationResult();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        mockMvc.perform(
                post(URL_PREFIX + "/" + MedicineConstants.getReservationForm().getMedicineId() + "/pharmacy/"+ MedicineConstants.getReservationForm().getPharmacyId() + "/reserve")
                .contentType(contentType)
                .content(jsonRequest)
                )

                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))

                .andExpect(jsonPath("$.reservationDate").value(result.getReservationDate().format(formatter)))
                .andExpect(jsonPath("$.lastDate").value(result.getLastDate().format(formatter)))
                .andExpect(jsonPath("$.status").value(result.getStatus()))
                .andExpect(jsonPath("$.medicineInPharmacy.id").value(result.getMedicineInPharmacy().getId()));

    }


}
