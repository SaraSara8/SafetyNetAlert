package com.safetynetAlert.controller;


import com.safetynetAlert.model.FireStation;
import com.safetynetAlert.model.MedicalRecord;
import com.safetynetAlert.model.Person;
import com.safetynetAlert.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;


@RestController
public class UrlController {

    @Autowired
    final private AlertService alertService;

    public UrlController(AlertService alertService) {
        this.alertService = alertService;
    }


    /**
     * URL pour obtenir la liste des personnes couvertes par une caserne de pompiers spécifique.
     *
     * @param stationNumber Le numéro de la caserne de pompiers.
     * @return Une liste de personnes et leurs détails.
     */
    @GetMapping("/firestation")
    public ResponseEntity<?> getPersonsCoveredByStation(@RequestParam int stationNumber) {

        Map<String, Object> mapResult = alertService.getPersonsCoveredByStation(stationNumber);

        if (mapResult.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Liste des personnes est vide...");

        } else {
            return ResponseEntity.ok(mapResult);
        }
    }

    /**
     * URL pour obtenir la liste des enfants résidant à une adresse spécifique.
     *
     * @param address L'adresse pour rechercher des enfants.
     * @return Une liste d'enfants et des membres de leur foyer.
     */
    @GetMapping("/childAlert")
    public ResponseEntity<?> getChildrenByAddress(@RequestParam String address) {

        Map<String, Object> mapResult = alertService.getChildrenByAddress(address);

        if (mapResult.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Liste des enfants est vide...");

        } else {
            return ResponseEntity.ok(mapResult);
        }


    }

    /**
     * URL pour obtenir la liste des numéros de téléphone des résidents couverts par une caserne de pompiers spécifique.
     *
     * @param stationNumber Le numéro de la caserne de pompiers.
     * @return Une liste de numéros de téléphone.
     */
    @GetMapping("/phoneAlert")
    public ResponseEntity<?> getPhoneNumbersByStation(@RequestParam int stationNumber) {

        Map<String, Object> mapResult = alertService.getPhoneNumbersByStation(stationNumber);

        if (mapResult.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Liste des phones est vide...");

        } else {
            return ResponseEntity.ok(mapResult);
        }
    }

    /**
     * URL pour obtenir les détails des résidents vivant à une adresse spécifique.
     *
     * @param address L'adresse pour rechercher des résidents.
     * @return Une liste de résidents et leurs détails.
     */
    @GetMapping("/fire")
    public ResponseEntity<?> getPersonsByAddress(@RequestParam String address) {

        Map<String, Object> mapResult = alertService.getPersonsByAddress(address);

        if (mapResult.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La liste est vide...");

        } else {
            return ResponseEntity.ok(mapResult);
        }
    }

    /**
     * URL pour obtenir la liste des foyers couverts par des casernes de pompiers spécifiques.
     *
     * @param stations Une liste de numéros de casernes de pompiers.
     * @return Une liste de foyers et leurs résidents.
     */
    @GetMapping("/flood/stations")
    public ResponseEntity<?> getHouseholdsByStations(@RequestParam String[] stations) {

        Map<String, Object> mapResult = alertService.getHouseholdsByStations(stations);

        if (mapResult.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La liste est vide...");

        } else {
            return ResponseEntity.ok(mapResult);
        }
    }

    /**
     * URL pour obtenir des informations personnelles pour une personne par son nom de famille.
     *
     * @param lastName Le nom de famille à rechercher.
     * @return Une liste de personnes et leurs détails.
     */
    @GetMapping("/personInfo")
    public ResponseEntity<?> getPersonsByLastName(@RequestParam String lastName) {

        Map<String, Object> mapResult = alertService.getPersonsByLastName(lastName);

        if (mapResult.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La liste est vide...");

        } else {
            return ResponseEntity.ok(mapResult);
        }
    }


    /**
     * URL pour obtenir une liste d'adresses e-mail des résidents d'une ville spécifique.
     *
     * @param city La ville à rechercher.
     * @return Une liste d'adresses e-mail.
     */
    @GetMapping("/cummunityEmail")
    public ResponseEntity<?> getEmailsByCity(@RequestParam String city) {

        Map<String, Object> mapResult = alertService.getEmailsByCity(city);

        if (mapResult.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La liste est vide...");

        } else {
            return ResponseEntity.ok(mapResult);
        }

    }

}
