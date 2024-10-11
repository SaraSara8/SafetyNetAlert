package com.safetynetAlert.controller;


import com.safetynetAlert.model.FireStation;
import com.safetynetAlert.service.FireStationService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Le contrôleur REST de l'application de services d'alerte
 * Gère les requêtes HTTP entrantes pour les opérations CRUD sur l'objet FireStation 
 * les caserne de pompiers et les informations de sécurité.
 * @author Equipe de développement
 * @version 1.0
 */

@RestController
public class FireStationController {


    @Autowired
    final private FireStationService fireStationService;

    private static final Logger logger = LogManager.getLogger(FireStationController.class);


    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }


/**
     * Endpoint pour ajouter une nouvelle cartographie de caserne de pompiers.
     * @param fireStation L'objet cartographie de caserne de pompiers à ajouter.
     * @return Une réponse indiquant le statut de l'opération.
     */
    @PostMapping("/firestation")
    public ResponseEntity<?> addFireStation(@RequestBody FireStation fireStation) {
        Optional<FireStation> fireStationAdded = fireStationService.addFireStation(fireStation);
        
        if(fireStationAdded.isPresent()) {
            logger.info("La caserne de pompiers avec l'adresse {} est déjà présente", fireStation.getAddress());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La firestation est déja presente...");
        } else {
            logger.info("Caserne de pompiers ajoutée avec succès pour l'adresse {}", fireStation.getAddress());
            //System.out.println("Caserne de pompiers non trouvée");
            return ResponseEntity.ok(fireStation);
            
        }

    }

    /**
     * Endpoint pour mettre à jour une cartographie de caserne de pompiers existante.
     * @param fireStation L'objet cartographie de caserne de pompiers mis à jour.
     * @return Une réponse indiquant le statut de l'opération.
     */
    @PutMapping("/firestation")
    public ResponseEntity<?> updateFireStation(@RequestBody FireStation fireStation) {
        
        Optional<FireStation> fireStationUpdated = fireStationService.updateFireStation(fireStation);
        
        
        if(fireStationUpdated.isPresent()) {
            logger.info("Caserne de pompiers mise à jour avec succès pour l'adresse {}", fireStation.getAddress());
            return ResponseEntity.ok(fireStation);
        } else {
            //System.out.println("Caserne de pompiers non trouvée");
            logger.warn("Aucune caserne de pompiers trouvée pour l'adresse {} à mettre à jour", fireStation.getAddress());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Caserne de pompiers non trouvée...");
        }


    }

    /**
     * Endpoint pour supprimer une cartographie de caserne de pompiers à partir de l'adresse
     * @param address L'adresse de la cartographie à supprimer.
     * @return une reposne indiquant l'objet {@link FireStation} supprimé.
     */
    @DeleteMapping("/firestation/address")
    public ResponseEntity<?> deleteFireStationByAddress(@RequestParam("address") String address) {
        
        Optional<FireStation> fireStationdeleted = fireStationService.deleteFireStationByAddress(address);

        if(fireStationdeleted.isPresent()) {
            logger.info("Caserne de pompiers supprimée avec succès pour l'adresse {}", address);
            return ResponseEntity.ok(fireStationdeleted.get());
        } else {
            //System.out.println("Caserne de pompiers non trouvée");
            logger.warn("Aucune caserne de pompiers trouvée pour l'adresse {} à supprimer", address);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Caserne de pompiers non trouvée...");
        }


       
    }

    /**
     * Endpoint pour supprimer une cartographie de caserne de pompiers à partir du numero de station
     * @param stationNumber Le numéro de la caserne de pompiers de la cartographie à supprimer.
     * @return une reposne indiquant l'objet {@link FireStation} supprimé.
     */
    @DeleteMapping("/firestation/station")
    public ResponseEntity<?> deleteFireStationByStation(@RequestParam("stationNumber") String stationNumber) {

        Optional<FireStation> fireStationdeleted = fireStationService.deleteFireStationByStation(stationNumber);

        if(fireStationdeleted.isPresent()) {
            logger.info("Caserne de pompiers supprimée avec succès pour le numéro de station {}", stationNumber);
            return ResponseEntity.ok(fireStationdeleted.get());
        } else {
            //System.out.println("Caserne de pompiers non trouvée");
            logger.warn("Aucune caserne de pompiers trouvée pour le numéro de station {} à supprimer", stationNumber);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Caserne de pompiers non trouvée...");
        }
    }



}
