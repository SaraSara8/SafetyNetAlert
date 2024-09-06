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
public class EndPointController {

    @Autowired
    final private AlertService alertService;

    public EndPointController(AlertService firestationService) {
        this.alertService = firestationService;
    }



    /**
     * Endpoint pour ajouter une nouvelle personne.
     *
     * @param person L'objet personne à ajouter.
     * @return Une réponse indiquant le statut de l'opération.
     */
    @PostMapping("/person")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {

        Person personadded= alertService.addPerson(person);
        return ResponseEntity.ok(personadded);

    }

    /**
     * Endpoint pour mettre à jour une personne existante.
     *
     * @param person L'objet personne mis à jour.
     * @return Une réponse indiquant le statut de l'opération.
     */
    @PutMapping("/person")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
    	 Person personupdated = alertService.updatePerson(person);
        return ResponseEntity.ok(personupdated);
    }

    /**
     * Endpoint pour supprimer une personne.
     *
     * @param firstName Le prénom de la personne.
     * @param lastName  Le nom de famille de la personne.
     * @return Une réponse indiquant le statut de l'opération.
     */
    @DeleteMapping("/person")
    public ResponseEntity<Person> deletePerson(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        Person Persondeleted = alertService.deletePerson(firstName, lastName);
        return ResponseEntity.ok(Persondeleted);
    }



    /**
     * Endpoint pour ajouter une nouvelle cartographie de caserne de pompiers.
     * @param fireStation L'objet cartographie de caserne de pompiers à ajouter.
     * @return Une réponse indiquant le statut de l'opération.
     */
    @PostMapping("/firestation")
    public ResponseEntity<Void> addFireStation(@RequestBody FireStation fireStation) {
        alertService.addFireStation(fireStation);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint pour mettre à jour une cartographie de caserne de pompiers existante.
     * @param fireStation L'objet cartographie de caserne de pompiers mis à jour.
     * @return Une réponse indiquant le statut de l'opération.
     */
    @PutMapping("/firestation")
    public ResponseEntity<Void> updateFireStation(@RequestBody FireStation fireStation) {
        alertService.updateFireStation(fireStation);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint pour supprimer une cartographie de caserne de pompiers.
     * @param address L'adresse de la cartographie à supprimer.
     * @param stationNumber Le numéro de la caserne de pompiers de la cartographie à supprimer.
     * @return Une réponse indiquant le statut de l'opération.
     */
    @DeleteMapping("/firestation")
    public ResponseEntity<Void> deleteFireStation(@RequestParam("address") String address, @RequestParam("stationNumber") String stationNumber) {
        alertService.deleteFireStation(address, stationNumber);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint pour supprimer une cartographie de caserne de pompiers à partir de l'adresse
     * @param address L'adresse de la cartographie à supprimer.
     * @return Une réponse indiquant le statut de l'opération.
     */
    @DeleteMapping("/firestation/address")
    public ResponseEntity<Void> deleteFireStationByAddress(@RequestParam("address") String address) {
        alertService.deleteFireStationByAddress(address);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint pour supprimer une cartographie de caserne de pompiers à partir du numero de station
     * @param stationNumber Le numéro de la caserne de pompiers de la cartographie à supprimer.
     * @return Une réponse indiquant le statut de l'opération.
     */
    @DeleteMapping("/firestation/station")
    public ResponseEntity<Void> deleteFireStationByStation(@RequestParam("stationNumber") String stationNumber) {
        alertService.deleteFireStationByStation(stationNumber);
        return ResponseEntity.ok().build();
    }





    /**
     * Endpoint pour ajouter un nouveau dossier médical.
     * @param medicalRecord L'objet dossier médical à ajouter.
     * @return Une réponse indiquant le statut de l'opération.
     */
    @PostMapping("/medicalRecord")
    public ResponseEntity<Void> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        alertService.addMedicalRecord(medicalRecord);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint pour mettre à jour un dossier médical existant.
     * @param medicalRecord L'objet dossier médical mis à jour.
     * @return Une réponse indiquant le statut de l'opération.
     */
    @PutMapping("/medicalRecord")
    public ResponseEntity<Void> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        alertService.updateMedicalRecord(medicalRecord);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint pour supprimer un dossier médical.
     * @param firstName Le prénom de la personne dont le dossier doit être supprimé.
     * @param lastName Le nom de famille de la personne dont le dossier doit être supprimé.
     * @return Une réponse indiquant le statut de l'opération.
     */
    @DeleteMapping("/medicalRecord")
    public ResponseEntity<Void> deleteMedicalRecord(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        alertService.deleteMedicalRecord(firstName, lastName);
        return ResponseEntity.ok().build();
    }


}
