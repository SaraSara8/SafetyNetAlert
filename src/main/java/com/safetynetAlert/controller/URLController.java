package com.safetynetAlert.controller;

import com.safetynetAlert.service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.Map;

/**
 * Le contrôleur REST de l'application de services d'alerte.
 * Gère les requêtes HTTP entrantes pour les opérations CRUD sur les personnes, les dossiers médicaux,
 * les caserne de pompiers et les informations de sécurité.
 * @author Equipe de développement
 * @version 1.0
 */
@RestController
public class URLController {

    // Injection de dépendance de AlertService
    @Autowired
    final private URLService urlService;

    private static final Logger logger = LogManager.getLogger(URLController.class);


    /**
     * Constructeur du contrôleur REST.
     * Injecte le service d'alerte pour l'utilisation dans les méthodes du contrôleur.
     * @param alertService le service d'alerte
     */
    public URLController(URLService firestationService) {
        this.urlService = firestationService;
    }


    /**
     * URL pour obtenir la liste des personnes couvertes par une caserne de pompiers spécifique.
     *
     * @param stationNumber Le numéro de la caserne de pompiers.
     * @return Une liste de personnes et leurs détails.
     */
    @GetMapping("/firestation")
    public ResponseEntity<?> getPersonsCoveredByStation(@RequestParam String stationNumber) {

        Map<String, Object> mapResult = urlService.getPersonsCoveredByStation(stationNumber);

        if (mapResult.isEmpty()) {
            logger.info("La liste des personnes couvertes par la caserne {} est vide", stationNumber);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Liste des personnes est vide...");

        } else {
            logger.info("Liste des personnes couvertes par la caserne {}", stationNumber);
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

        Map<String, Object> mapResult = urlService.getChildrenByAddress(address);

        if (mapResult.isEmpty()) {
            logger.info("La liste des enfants résidant à l'adresse {} est vide", address);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Liste des enfants est vide...");

        } else {
            logger.info("Liste des enfants résidant à l'adresse {}", address);
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
    public ResponseEntity<?> getPhoneNumbersByStation(@RequestParam String stationNumber) {

        Map<String, Object> mapResult = urlService.getPhoneNumbersByStation(stationNumber);

        if (mapResult.isEmpty()) {
            logger.info("La liste des numéros de téléphone couverts par la caserne {} est vide", stationNumber);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Liste des phones est vide...");

        } else {
            logger.info("Liste des numéros de téléphone couverts par la caserne {}", stationNumber);
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

        Map<String, Object> mapResult = urlService.getPersonsByAddress(address);

        if (mapResult.isEmpty()) {
            logger.info("La liste des résidents vivant à l'adresse {} est vide", address);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La liste est vide...");

        } else {
            logger.info("Liste des résidents vivant à l'adresse {}", address);
            return ResponseEntity.ok(mapResult);
        }
    }

    /**
     * Endpoint pour obtenir la liste des foyers couverts par des casernes de pompiers spécifiques.
     *
     * @param stations Une liste de numéros de casernes de pompiers.
     * @return Une liste de foyers et leurs résidents.
     */
    @GetMapping("/flood/stations")
    public ResponseEntity<?> getHouseholdsByStations(@RequestParam String[] stations) {

        Map<String, Object> mapResult = urlService.getHouseholdsByStations(stations);

        if (mapResult.isEmpty()) {
            logger.info("La liste des foyers couverts par les casernes {} est vide", stations.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La liste est vide...");

        } else {
            logger.info("Liste des foyers couverts par les casernes {}", stations.toString());
            return ResponseEntity.ok(mapResult);
        }
    }

    /**
     * Endpoint pour obtenir des informations personnelles pour une personne par son nom de famille.
     *
     * @param lastName Le nom de famille à rechercher.
     * @return Une liste de personnes et leurs détails.
     */
    @GetMapping("/personInfo")
    public ResponseEntity<?> getPersonsByLastName(@RequestParam String lastName) {

        Map<String, Object> mapResult = urlService.getPersonsByLastName(lastName);

        if (mapResult.isEmpty()) {
            logger.info("La liste des personnes par nom de famille {} est vide", lastName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La liste est vide...");

        } else {
            logger.info("Liste des personnes par nom de famille {}", lastName);
            return ResponseEntity.ok(mapResult);
        }
    }


    /**
     * Endpoint pour obtenir une liste d'adresses e-mail des résidents d'une ville spécifique.
     *
     * @param city La ville à rechercher.
     * @return Une liste d'adresses e-mail.
     */
    @GetMapping("/cummunityEmail")
    public ResponseEntity<?> getEmailsByCity(@RequestParam String city) {

        Map<String, Object> mapResult = urlService.getEmailsByCity(city);

        if (mapResult.isEmpty()) {
            logger.info("La liste des adresses e-mail des résidents de la ville {} est vide", city);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La liste est vide...");

        } else {
            logger.info("Liste des adresses e-mail des résidents de la ville {}", city);
            return ResponseEntity.ok(mapResult);
        }

    } 

}
