package com.safetynetAlert.safetynetAlert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetAlert.SafetynetAlertApplication;
import com.safetynetAlert.model.MedicalRecord;
import com.safetynetAlert.service.MedicalRecordService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.junit.jupiter.api.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = SafetynetAlertApplication.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class MedicalRecordControllerIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private MedicalRecordService medicalRecordService;

        @Autowired
        private ObjectMapper objectMapper;

        private MedicalRecord medicalRecordAlpha = new MedicalRecord();
        private MedicalRecord medicalRecordBeta = new MedicalRecord();

        @BeforeEach
        public void setup() {

                List<String> medicationsAlpha = Arrays.asList("ptradoxidine:400mg");
                List<String> allergiesAlpha = Arrays.asList("allergy3", "allergy3");
               
                // dossier medical d'une personne qui existe dans la base de donnée
                medicalRecordAlpha.setFirstName("Eric");
                medicalRecordAlpha.setLastName("Cadigan");
                medicalRecordAlpha.setBirthdate("08/06/1945");
                medicalRecordAlpha.setMedications(medicationsAlpha);
                medicalRecordAlpha.setAllergies(allergiesAlpha);

                List<String> medicationsBeta = Arrays.asList("aznol:350mg", "hydrapermazol:100mg");
                List<String> allergiesBeta = Arrays.asList("allergy1", "allergy2");
                
                // dossier medical d'une persone qui n'existe pas dans la base de donnée
                medicalRecordBeta.setFirstName("John");
                medicalRecordBeta.setLastName("Doe");
                medicalRecordBeta.setBirthdate("01/01/2014");
                medicalRecordBeta.setMedications(medicationsBeta);
                medicalRecordBeta.setAllergies(allergiesBeta);

        }

        @Test
        @Order(1)
        public void testAddMedicalRecordAlpha_Success() throws Exception {

                // Envoie de la requette POST d'un nouveau dossier Medical d'une personne
                // existante
                mockMvc.perform(post("/medicalRecord")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(medicalRecordAlpha)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.firstName").value(medicalRecordAlpha.getFirstName()))
                                .andExpect(jsonPath("$.lastName").value(medicalRecordAlpha.getLastName()));

        }

        @Test
        @Order(2)
        public void testAddMedicalRecordAplaha_AlreadyExists() throws Exception {

                // Envoie de la requette POST d'un dossier Medical qui existe
                mockMvc.perform(post("/medicalRecord")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(medicalRecordAlpha)))
                                .andExpect(status().isNotFound())
                                .andExpect(content().string("Le medical record de " + medicalRecordAlpha.getFirstName()
                                                + " "
                                                + medicalRecordAlpha.getLastName()
                                                + " est déja present ou la personne n'est pas presente..."));
        }

        @Test
        @Order(3)
        public void testAddMedicalRecordBeta_PersonNotExists() throws Exception {

                // Envoie de la requette POST d'un dossier Medical d'une personne qui n'existe
                // pas
                mockMvc.perform(post("/medicalRecord")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(medicalRecordBeta)))
                                .andExpect(status().isNotFound())
                                .andExpect(content().string("Le medical record de " + medicalRecordBeta.getFirstName()
                                                + " "
                                                + medicalRecordBeta.getLastName()
                                                + " est déja present ou la personne n'est pas presente..."));
        }

        @Test
        @Order(4)
        void testUpdateMedicalRecordAlpha_Success() throws Exception {

                // Envoie de requette PUT d'une dossier medical qui existe
                mockMvc.perform(put("/medicalRecord")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(medicalRecordAlpha)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.firstName").value(medicalRecordAlpha.getFirstName()))
                                .andExpect(jsonPath("$.lastName").value(medicalRecordAlpha.getLastName()));
        }

        @Test
        @Order(5)
        void testUpdateMedicalRecordBeta_NotFound() throws Exception {

                // Envoie de requette PUT d'un dossier medical qui n'exsite pas
                mockMvc.perform(put("/medicalRecord")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(medicalRecordBeta)))
                                .andExpect(status().isNotFound())
                                .andExpect(content()
                                                .string("Le medical record de " + medicalRecordBeta.getFirstName() + " "
                                                                + medicalRecordBeta.getLastName() + " non trouvé..."));
        }

        @Test
        @Order(6)
        void testDeleteMedicalRecordAlpha_Success() throws Exception {

                // Envoie de la requette DELETE d'un dossier existante
                mockMvc.perform(delete("/medicalRecord")
                                .param("firstName", medicalRecordAlpha.getFirstName())
                                .param("lastName", medicalRecordAlpha.getLastName()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.firstName").value(medicalRecordAlpha.getFirstName()))
                                .andExpect(jsonPath("$.lastName").value(medicalRecordAlpha.getLastName()));

        }

        @Test
        @Order(7)
        void testDeleteMedicalRecord_NotFound() throws Exception {

                // Envoie de requette DELETE d'un dossier qui n'existe pas
                mockMvc.perform(delete("/medicalRecord")
                                .param("firstName", medicalRecordAlpha.getFirstName())
                                .param("lastName", medicalRecordAlpha.getLastName()))
                                .andExpect(status().isNotFound())
                                .andExpect(content().string(
                                                "Le medical record de " + medicalRecordAlpha.getFirstName() + " "
                                                                + medicalRecordAlpha.getLastName() + " non trouvé..."));
        }
}