package com.safetynetAlert.service;

import com.safetynetAlert.model.*;


import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService {

    @Setter
    @Getter
    @Autowired
    private JsonDataBase jsonData;
    private PersonService personService;

    private static final Logger logger = LogManager.getLogger(MedicalRecordService.class);

    public MedicalRecordService(JsonDataBase jsonData, PersonService personService) {
        this.jsonData = jsonData;
        this.personService = personService;
    }

    /**
     * Service pour ajouter un nouveau dossier médical.
     * 
     * @param medicalRecord L'objet dossier médical à ajouter.
     */
    public Optional<MedicalRecord> addMedicalRecord(MedicalRecord medicalRecord) {

        Data data = jsonData.getData();

        // verrifier si la personne existe déja dans la base de données afin d'ajouter
        // un dossier medical
        Optional<Person> existingPerson = personService.findPersonByName(medicalRecord.getFirstName(),
                medicalRecord.getLastName());

        // Si la personne n'existe pas ajouter la personne à la base de données

        // Vérifier si le dossier médical existe déjà dans la base de données
        Optional<MedicalRecord> existingMedicalRecord = getMedicalRecordByName(medicalRecord.getFirstName(),
                medicalRecord.getLastName());

        // Si le dossier médical n'existe pas : ajouter le dossier dans la base de
        // données
        if (!existingMedicalRecord.isPresent()) {

            if (existingPerson.isPresent()) {
                data.getMedicalrecords().add(medicalRecord);
                jsonData.saveData(data);
                logger.info("Dossier médical ajouté avec succès pour {} {}", medicalRecord.getFirstName(),
                        medicalRecord.getLastName());
            } else {
                logger.warn("Il existe aucune personne dans la base pour le dossier médical pour {} {} ",
                        medicalRecord.getFirstName(),
                        medicalRecord.getLastName());

                existingMedicalRecord =  Optional.ofNullable(medicalRecord);    
                
            }

        } else {
            logger.warn("Le dossier médical pour {} {} existe déjà", medicalRecord.getFirstName(),
                    medicalRecord.getLastName());
        }

        return existingMedicalRecord;

    }

    /**
     * Service pour mettre à jour un dossier médical existant.
     * 
     * @param medicalRecord L'objet dossier médical mis à jour.
     */
    public Optional<MedicalRecord> updateMedicalRecord(MedicalRecord medicalRecord) {
        Data data = jsonData.getData();

        Optional<MedicalRecord> medicalRecordToUpdate = getMedicalRecordByName(medicalRecord.getFirstName(),
                medicalRecord.getLastName());

        if (medicalRecordToUpdate.isPresent()) {
            List<MedicalRecord> updateMedicalRecord = data.getMedicalrecords().stream()
                    .map(m -> (m.getFirstName().equals(medicalRecord.getFirstName()) &&
                            m.getLastName().equals(medicalRecord.getLastName())) ? medicalRecord : m)
                    .collect(Collectors.toList());
            data.setMedicalrecords(updateMedicalRecord);
            jsonData.saveData(data);
            logger.info("Dossier médical mis à jour avec succès pour {} {}", medicalRecord.getFirstName(),
                    medicalRecord.getLastName());
        } else {
            logger.warn("Aucun dossier médical trouvé pour {} {} à mettre à jour", medicalRecord.getFirstName(),
                    medicalRecord.getLastName());
        }
        return medicalRecordToUpdate;

    }

    /**
     * Service pour supprimer un dossier médical.
     * 
     * @param firstName Le prénom de la personne dont le dossier doit être supprimé.
     * @param lastName  Le nom de famille de la personne dont le dossier doit être
     *                  supprimé.
     */
    public Optional<MedicalRecord> deleteMedicalRecord(String firstName, String lastName) {
        Data data = jsonData.getData();

        // Vérifier le medicalRecord existe déjà dans la base de données
        Optional<MedicalRecord> existingMedicalRecord = getMedicalRecordByName(firstName, lastName);

        // Si la personne est trouvée, la supprimer de la liste des personnes
        if (existingMedicalRecord.isPresent()) {

            data.getMedicalrecords().remove(existingMedicalRecord.get());
            jsonData.saveData(data);
            logger.info("Dossier médical supprimé avec succès pour {} {}", firstName, lastName);
        } else {
            logger.warn("Aucun dossier médical trouvé pour {} {} à supprimer", firstName, lastName);
        }

        return existingMedicalRecord;

    }

    /**
     * Recherche un dossier médical par le prénom et le nom de famille de la
     * personne.
     *
     * @param firstName le prénom de la personne.
     * @param lastName  le nom de famille de la personne.
     * @return le dossier médical trouvé ou un Optional vide si aucun dossier ne
     *         correspond.
     */
    public Optional<MedicalRecord> getMedicalRecordByName(String firstName, String lastName) {

        Data data = jsonData.getData();
        Optional<MedicalRecord> medicalRecord = data.getMedicalrecords().stream()
                .filter(m -> m.getFirstName().equalsIgnoreCase(firstName) && m.getLastName().equalsIgnoreCase(lastName))
                .findFirst();
        if (medicalRecord.isPresent()) {
            logger.info("Dossiers médicaux récupérés avec succès pour {} {}", firstName, lastName);
        } else {
            logger.warn("Aucun dossier médical trouvé pour {} {}", firstName, lastName);
        }
        return medicalRecord;
    }

    /**
     * Obtenir les dossiers médicaux d'une personne.
     *
     * @param firstName Le prénom de la personne.
     * @param lastName  Le nom de famille de la personne.
     * @return Une map des dossiers médicaux.
     */
   /*
    public Map<String, List<String>> getMedicalRecords(String firstName, String lastName) {
        Map<String, List<String>> medicalInfos = jsonData.getData().getMedicalrecords().stream()
                .filter(m -> m.getFirstName().equalsIgnoreCase(firstName) && m.getLastName().equalsIgnoreCase(lastName))
                .map(m -> {
                    Map<String, List<String>> medicalInfo = new HashMap<>();
                    medicalInfo.put("medications", m.getMedications());
                    medicalInfo.put("allergies", m.getAllergies());
                    return medicalInfo;
                })
                .findFirst()
                .orElse(new HashMap<>());

        if (!medicalInfos.isEmpty()) {
            logger.info("Dossiers médicaux récupérés avec succès pour {} {}", firstName, lastName);
        } else {
            logger.warn("Aucun dossier médical trouvé pour {} {}", firstName, lastName);
        }
        return medicalInfos;
    }
*/
}
