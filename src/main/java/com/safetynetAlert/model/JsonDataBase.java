package com.safetynetAlert.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.*;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/*
 * Cette classe permet de representer les données du fichier Json
 */
@Getter
@Setter
@Component
public class JsonDataBase {

    final static Logger logger = LogManager.getLogger(JsonDataBase.class);
    private Data data;

    @Value("${json.file.path}")
    private String filePath;

    @PostConstruct
    private void init() {
        try {
            loadData();
        } catch (IOException e) {
            // Handle the exception appropriately, e.g., log it and rethrow as a runtime exception
            throw new RuntimeException("Faild to load JSON data", e);
        }

    }

    /**
     * Charge les données depuis un fichier JSON spécifié.
     *
     *  jsonFilePath Le chemin du fichier JSON.
     */
    private void loadData() throws IOException {

        byte[] jsonData = Files.readAllBytes(Paths.get(filePath));

        Any any = JsonIterator.deserialize(jsonData);


        // Parse persons

        List<Person> persons = new ArrayList<>();
        any.get("persons").forEach(person -> persons.add(person.as(Person.class)));

        logger.info("chargement des personnes ..", persons.size());

        // Parse medical records
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        any.get("medicalrecords").forEach(medicalRecord -> medicalRecords.add(medicalRecord.as(MedicalRecord.class)));

        // Parse firestations
        List<FireStation> firestations = new ArrayList<>();
        any.get("firestations").forEach(firestation -> firestations.add(firestation.as(FireStation.class)));

        // Associer les persones aux stations
        //firestations.forEach(fireStation -> fireStation.setPersonsByStation(persons.stream().filter(person -> person.getAddress().equalsIgnoreCase(fireStation.getAddress())).collect(Collectors.toList())));

        data = new Data(persons, firestations, medicalRecords);

    }


    /**
     * Sauvegarde les données dans un fichier JSON avec des retours à la ligne pour chaque objet.
     *
     * @param data l'objet les données .
     * @throws IOException Si une erreur survient lors de l'écriture du fichier.
     */
    public void saveData(Data data) {

        try{
            // Création d'une Map pour stocker les données
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("persons", data.getPersons());
            dataMap.put("firestations", data.getFirestations());
            dataMap.put("medicalrecords", data.getMedicalrecords());

            // Création de l'ObjectMapper pour la conversion en JSON
            ObjectMapper objectMapper = new ObjectMapper();

            // Configurer l'ObjectMapper pour formater le JSON avec des retours à la ligne
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            // Écrire les données dans le fichier
            objectMapper.writeValue(new File(filePath), dataMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
