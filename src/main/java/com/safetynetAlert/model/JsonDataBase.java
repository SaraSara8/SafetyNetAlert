package com.safetynetAlert.model;



import com.jsoniter.any.Any;
import com.safetynetAlert.Util.FileUtil;

import jakarta.annotation.PostConstruct;


import java.io.IOException;


import java.util.*;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Cette classe permet de representer les données du fichier Json
 * @author  Your Name
 * @version 1.0
 */
@Getter
@Setter
@Component
public class JsonDataBase {

    final static Logger logger = LogManager.getLogger(JsonDataBase.class);
    private Data data;

    @Value("${json.file.path}")
    private String filePath;

    @Autowired
    FileUtil fileUtil;

    public JsonDataBase() {
        
    }

    /**
     * Constructeur 
     */
    public JsonDataBase(String filePath) {
        this.filePath = filePath;
    }


    @PostConstruct
    public void init() {
        
            loadData();
        

    }

    /**
     * Charge les données depuis un fichier JSON spécifié.
     *
     *  jsonFilePath Le chemin du fichier JSON.
     */
    public void loadData() {

        try {
        // Read all bytes from the JSON file
        byte[] jsonData = fileUtil.readFile(filePath);

        // Deserialize the JSON data by converting it into an Any object
         Any any = fileUtil.deserializeFile(jsonData);


        // Parse persons

        List<Person> persons = new ArrayList<>();
        any.get("persons").forEach(person -> persons.add(person.as(Person.class)));

        // Log the number of persons loaded from the JSON data
        logger.info("chargement des personnes {}..", persons.size());

        // Parse medical records
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        any.get("medicalrecords").forEach(medicalRecord -> medicalRecords.add(medicalRecord.as(MedicalRecord.class)));
        logger.info("chargement des dossiers medicales {}..", medicalRecords.size());

        // Parse firestations
        List<FireStation> firestations = new ArrayList<>();
        any.get("firestations").forEach(firestation -> firestations.add(firestation.as(FireStation.class)));
        logger.info("chargement des casernes {}..", firestations.size());
        // Associer les persones aux stations
       
        data = new Data(persons, firestations, medicalRecords);
        logger.info("chargement des données dans le fihcier Json");
        } catch (IOException e) {
            // Handle the exception appropriately, e.g., log it and rethrow as a runtime exception
            logger.error("Échec de la lecture des données JSON", e);
            throw new RuntimeException("Faild to load JSON data", e);
        }


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
            logger.info("debut de sauvegarde des données JSON");
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("persons", data.getPersons());
            dataMap.put("firestations", data.getFirestations());
            dataMap.put("medicalrecords", data.getMedicalrecords());


            fileUtil.writeFile(filePath, dataMap);
            /* 
            // Création de l'ObjectMapper pour la conversion en JSON
            ObjectMapper objectMapper = new ObjectMapper();

            // Configurer l'ObjectMapper pour formater le JSON avec des retours à la ligne
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            // Écrire les données dans le fichier
            objectMapper.writeValue(new File(filePath), dataMap);
            */
            logger.info("fin de sauvegarde des données JSON");
        } catch (IOException e) {
            logger.error("Échec de sauvegarde des données JSON", e);
            e.printStackTrace();
        }
    }



}
