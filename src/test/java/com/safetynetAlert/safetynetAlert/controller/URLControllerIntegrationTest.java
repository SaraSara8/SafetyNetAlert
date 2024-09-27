package com.safetynetAlert.safetynetAlert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetAlert.SafetynetAlertApplication;

import com.safetynetAlert.service.URLService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.mockito.MockitoAnnotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest(classes = SafetynetAlertApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class URLControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private URLService urlService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testGetPersonsCoveredByStation_Success() throws Exception {

        // Envoie requette GET : firestation?stationNumber=1
        mockMvc.perform(get("/firestation")
                .param("stationNumber", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.adults").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.children").exists());
    }

    @Test
    void testGetPersonsCoveredByStation_NotFound() throws Exception {

        // Envoie requette GET : firestation?stationNumber=1111
        mockMvc.perform(get("/firestation")
                .param("stationNumber", "1111")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Liste des personnes est vide..."));
    }

    @Test
    void testGetChildrenByAddress_Success() throws Exception {

        // Envoie requette GET : childAlert?address=1509 Culver St
        mockMvc.perform(get("/childAlert")
                .param("address", "1509 Culver St")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.children").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.children").isArray());
    }

    @Test
    void testGetChildrenByAddress_NotFound() throws Exception {

        // Envoie requette GET : childAlert?address=1509 rue de Paris
        mockMvc.perform(get("/childAlert")
                .param("address", "1509 rue de Paris")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Liste des enfants est vide..."));
    }

    @Test
    void testGetPhoneNumbersByStation_Success() throws Exception {

        // Envoie requette GET : phoneAlert?stationNumber=1
        mockMvc.perform(get("/phoneAlert")
                .param("stationNumber", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phones").exists());
    }

    @Test
    void testGetPhoneNumbersByStation_NotFound() throws Exception {

        // Envoie requette GET : phoneAlert?stationNumber=1
        mockMvc.perform(get("/phoneAlert")
                .param("stationNumber", "1111")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Liste des phones est vide..."));
    }

    @Test
    void testGetPersonsByAddress_Success() throws Exception {

        // Envoie requette GET : fire?address=1509 Culver St2
        mockMvc.perform(get("/fire")
                .param("address", "29 15th St")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persons").exists());
    }

    @Test
    void testGetPersonsByAddress_NotFound() throws Exception {

        // Envoie requette GET : fire?address=1509 Culver St2
        mockMvc.perform(get("/fire")
                .param("address", "29 rue de Paris")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("La liste est vide..."));
    }

    @Test
    void testGetHouseholdsByStations_Success() throws Exception {

        // Envoie requette GET : flood/stations?stations=1,2
        mockMvc.perform(get("/flood/stations")
                .param("stations", "1,2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.households").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.households").isArray());
    }

    @Test
    void testGetHouseholdsByStations_NotFound() throws Exception {

        // Envoie requette GET : flood/stations?stations=1,2
        mockMvc.perform(get("/flood/stations")
                .param("stations", "1111,2222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("La liste est vide..."));
    }

    @Test
    void testGetPersonsByLastName_Success() throws Exception {

        // Envoie requette GET : personInfo?lastName=Boyd
        mockMvc.perform(get("/personInfo")
                .param("lastName", "Boyd")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persons").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.persons").isArray());
    }

    @Test
    void testGetPersonsByLastName_NotFound() throws Exception {

        // Envoie requette GET : personInfo?lastName=Boyd
        mockMvc.perform(get("/personInfo")
                .param("lastName", "Dupond")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("La liste est vide..."));
    }

    @Test
    void testGetEmailsByCity_Success() throws Exception {

        // Envoie requette GET :cummunityEmail?city=Culver
        mockMvc.perform(get("/cummunityEmail")
                .param("city", "Culver")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.emails").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.emails").isArray());
    }

    @Test
    void testGetEmailsByCity_NotFound() throws Exception {

        // Envoie requette GET :cummunityEmail?city=Culver
        mockMvc.perform(get("/cummunityEmail")
                .param("city", "Paris")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("La liste est vide..."));
    }

}
