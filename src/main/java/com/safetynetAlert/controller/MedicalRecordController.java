package com.safetynetAlert.controller;



import com.safetynetAlert.model.MedicalRecord;


import com.safetynetAlert.service.MedicalRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;


/**
 * Le contrôleur REST de l'application de services d'alerte.
 * Gère les requêtes HTTP entrantes pour les opérations CRUD sur les personnes, les dossiers médicaux,
 * les caserne de pompiers et les informations de sécurité.
 * @author Equipe de développement
 * @version 1.0
 */
@RestController
public class MedicalRecordController {



    // Injection de dépendance de AlertService
    @Autowired
    final private MedicalRecordService medicalRecordService;

    private static final Logger logger = LogManager.getLogger(MedicalRecordController.class);


    /**
     * Constructeur du contrôleur REST.
     * Injecte le service d'alerte pour l'utilisation dans les méthodes du contrôleur.
     * @param medicalRecordService le service de Medical Record
     */
    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    /**
     * Endpoint pour ajouter un nouveau dossier médical.
     * @param medicalRecord L'objet dossier médical à ajouter.
     * @return Une réponse indiquant le statut de l'opération.
     */
    @PostMapping("/medicalRecord")
    public ResponseEntity<?> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        
        Optional <MedicalRecord> medicalRecordAdded = medicalRecordService.addMedicalRecord(medicalRecord);
        
        if(medicalRecordAdded.isPresent()) {
            logger.info("Le dossier médical de {} {} est déjà présent", medicalRecord.getFirstName(), medicalRecord.getLastName());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le medical record de " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName() + " est déja present ou la personne n'est pas presente...");
        } else {
            //System.out.println("Caserne de pompiers non trouvée");
            logger.info("Dossier médical ajouté avec succès pour {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
            return ResponseEntity.ok(medicalRecord);
            
        }

    }

    /**
     * Endpoint pour mettre à jour un dossier médical existant.
     * @param medicalRecord L'objet dossier médical mis à jour.
     * @return Une réponse indiquant le statut de l'opération.
     */
    @PutMapping("/medicalRecord")
    public ResponseEntity<?> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        
        Optional<MedicalRecord> medicalRecordUpdated = medicalRecordService.updateMedicalRecord(medicalRecord);

        if(medicalRecordUpdated.isPresent()) {
            logger.info("Dossier médical mis à jour avec succès pour {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
            return ResponseEntity.ok(medicalRecord);
        } else {
            //System.out.println("Caserne de pompiers non trouvée");
            logger.warn("Aucun dossier médical trouvé pour {} {} à mettre à jour", medicalRecord.getFirstName(), medicalRecord.getLastName());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le medical record de " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName() + " non trouvé...");
        }

    }

    /**
     * Endpoint pour supprimer un dossier médical.
     * @param firstName Le prénom de la personne dont le dossier doit être supprimé.
     * @param lastName Le nom de famille de la personne dont le dossier doit être supprimé.
     * @return Une réponse indiquant le statut de l'opération.
     */
    @DeleteMapping("/medicalRecord")
    public ResponseEntity<?> deleteMedicalRecord(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        
        
        Optional<MedicalRecord> medicalRecordDeleted = medicalRecordService.deleteMedicalRecord(firstName, lastName);
       
        if(medicalRecordDeleted.isPresent()) {
            logger.info("Dossier médical supprimé avec succès pour {} {}", firstName, lastName);
            return ResponseEntity.ok(medicalRecordDeleted.get());
        } else {
            //System.out.println("Caserne de pompiers non trouvée");
            logger.warn("Aucun dossier médical trouvé pour {} {} à supprimer", firstName, lastName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le medical record de " + firstName + " " + lastName + " non trouvé...");
        }



    }


}
