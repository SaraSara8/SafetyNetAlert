package com.safetynetAlert.safetynetAlert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetAlert.SafetynetAlertApplication;

import com.safetynetAlert.model.Person;

import com.safetynetAlert.service.PersonService;

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
class PersonControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonService personService;

    @Autowired
    private ObjectMapper objectMapper;

    private Person personToto;
    private Person personTiti;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        
        personToto = new Person("John", "Daniel", "123 Main St", "New York", "97451", "841-874-6512", "jaboyd@email.com");
        personTiti = new Person("Lily", "Jackson", "456 Pine St", "Chicago", "97455", "841-874-6544", "licooper@email.com");

    }

    @Test
    @Order(1)
    public void testAddPersonToto_Success() throws Exception {
        
         // Envoie de requette POST d'une nouvelle personne
        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personToto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(personToto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(personToto.getLastName()));
    }

    @Test
    @Order(2)
    public void testAddPersonToto_AlreadyExists() throws Exception {
       
         // Envoie de requette POST d'une personne qui existe 
        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personToto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("La personne " + personToto.getFirstName() + " " + personToto.getLastName() + " est déja presente..."));
    }

    @Test
    @Order(3)
    public void testUpdatePersonToto_Success() throws Exception {

         // Envoie de requette PUT d'une personne qui exsite
        mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personToto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(personToto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(personToto.getLastName()));
    }

    @Test
    @Order(4)
    void testUpdatePersonnTiti_NotFound() throws Exception {

         // Envoie de requette PUT d'une personne qui n'existe pas
        mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personTiti)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("La personne "+personTiti.getFirstName() + " "+ personTiti.getLastName() + " non trouvée..."));

    }


    @Test
    @Order(5)
    public void testDeletePersonToto_Success() throws Exception {

         // Envoie de requette DELETE qui existe
        mockMvc.perform(delete("/person")
                .param("firstName", personToto.getFirstName())
                .param("lastName", personToto.getLastName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(personToto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(personToto.getLastName()));
    
    }

    @Test
    @Order(6)
    public void testDeletePersonToto_NotFound() throws Exception {
        
         // Envoie de requette DELETE pour une personne qui n'existe pas
        mockMvc.perform(delete("/person")
                .param("firstName", personToto.getFirstName())
                .param("lastName", personToto.getLastName()))
                .andExpect(status().isNotFound())
                .andExpect(content().string("La personne " + personToto.getFirstName() + " " + personToto.getLastName() + " non trouvée..."));
    }

}
