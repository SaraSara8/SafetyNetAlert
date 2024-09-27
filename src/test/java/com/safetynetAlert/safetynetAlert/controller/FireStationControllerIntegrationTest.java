package com.safetynetAlert.safetynetAlert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetAlert.SafetynetAlertApplication;
import com.safetynetAlert.model.FireStation;
import com.safetynetAlert.service.FireStationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;

import org.mockito.MockitoAnnotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = SafetynetAlertApplication.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
class FireStationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private ObjectMapper objectMapper;

    private FireStation fireStationApha;
    private FireStation fireStationBeta;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        
        fireStationApha = new FireStation();
        fireStationApha.setAddress("62 rue des Moulineaux");
        fireStationApha.setStation("5");

        fireStationBeta = new FireStation();
        fireStationBeta.setAddress("9 avenue Marechal");
        fireStationBeta.setStation("6");

    }

    @Test
    @Order(1)
    void testAddFireStationApha_Success() throws Exception {
        
        // Envoie de requette POST d'ajout d'une nouvelle caserne qui n'exste pas
        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fireStationApha)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value(fireStationApha.getAddress()));
    }
 
    @Test
    @Order(2)
    void testAddFireStationApha_AlreadyExists() throws Exception {
        
        // Envoie de requette POST d'ajout d'une caserne qui exste déja 
        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fireStationApha)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("La firestation est déja presente..."));
    }

   

    @Test
    @Order(3)
    void testUpdateFireStationAlpha_Success() throws Exception {
        
        // Envoie de requette PUT d'une caserne qui exste déja 
        mockMvc.perform(put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fireStationApha)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value(fireStationApha.getAddress()));
    }

    @Test
    @Order(4)
    void testUpdateFireStationBeta_NotFound() throws Exception {
        
        // Envoie de requette PUT d'une caserne qui n'existe pas 
        mockMvc.perform(put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fireStationBeta)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Caserne de pompiers non trouvée..."));
    }

    @Test
    @Order(5)
    void testAddFireStationBeta_Success() throws Exception {
        
        // Envoie de requette POST d'ajout d'une nouvelle caserne qui n'exste pas
        mockMvc.perform(post("/firestation")
        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fireStationBeta)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value(fireStationBeta.getAddress()));
    }

    @Test
    @Order(6)
    void testDeleteFireStationAplphaByAddress_Success() throws Exception {
        
        // Envoie de requette DELETE d'une caserne qui existe déja via l'adresse
        mockMvc.perform(delete("/firestation/address")
                .param("address", fireStationApha.getAddress()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value(fireStationApha.getAddress()));
    }

    @Test
    @Order(7)
    void testDeleteFireStationAlphaByAddress_NotFound() throws Exception {
        
        // Envoie de requette DELETE d'une caserne qui n'existe pas via l'address
        mockMvc.perform(delete("/firestation/address")
                .param("address", fireStationApha.getAddress()))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Caserne de pompiers non trouvée..."));
    }

    @Test
    @Order(8)
    void testDeleteFireStationBetaByStation_Success() throws Exception {
       
        // Envoie de requette DELETE d'une caserne qui existe déja via le num de station
        mockMvc.perform(delete("/firestation/station")
                .param("stationNumber", fireStationBeta.getStation()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.station").value(fireStationBeta.getStation()));
    }

    @Test
    @Order(9)
    void testDeleteFireStationBetaByStation_NotFound() throws Exception {
       
        // Envoie de requette DELETE d'une caserne qui n'existe pas via le num de station
        mockMvc.perform(delete("/firestation/station")
                .param("stationNumber", fireStationBeta.getStation()))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Caserne de pompiers non trouvée..."));
    }
                
           
}
