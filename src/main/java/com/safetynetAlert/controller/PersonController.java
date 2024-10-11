package com.safetynetAlert.controller;


import com.safetynetAlert.model.Person;
import com.safetynetAlert.service.PersonService;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Le contrôleur REST de l'application de services d'alerte.
 * Gère les requêtes HTTP entrantes pour les opérations CRUD sur les personness
 * @author Equipe de développement
 * @version 1.0
 */
@RestController
public class PersonController {

    @Autowired
    final private PersonService personService;

    private static final Logger logger = LogManager.getLogger(PersonController.class);


    public PersonController(PersonService personService) {
        this.personService = personService;
    }



    /**
     * Endpoint pour ajouter une nouvelle personne.
     *
     * @param person L'objet personne à ajouter.
     * @return Une réponse indiquant le statut de l'opération.
     */
    @PostMapping("/person")
    public ResponseEntity<?> addPerson(@RequestBody Person person) {

        Optional <Person>  personadded= personService.addPerson(person);

        if(personadded.isPresent()) {
            logger.info("La personne {} {} est déjà présente", person.getFirstName(), person.getLastName());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La personne " + person.getFirstName() + " " + person.getLastName() + " est déja presente...");
        } else {
            //System.out.println("Caserne de pompiers non trouvée");
            logger.info("Personne ajoutée avec succès pour {} {}", person.getFirstName(), person.getLastName());
            return ResponseEntity.ok(person);
            
        }

       

    }

    /**
     * Endpoint pour mettre à jour une personne existante.
     *
     * @param person L'objet personne mis à jour.
     * @return Une réponse indiquant le statut de l'opération.
     */
    @PutMapping("/person")
    public ResponseEntity<?> updatePerson(@RequestBody Person person) {
    	 
        Optional <Person> personupdated = personService.updatePerson(person);

        if(personupdated.isPresent()) {
            logger.info("Personne mis à jour avec succès pour {} {}", person.getFirstName(), person.getLastName());
            return ResponseEntity.ok(person);
        } else {
            //System.out.println("Caserne de pompiers non trouvée");
            logger.warn("Aucune personne trouvée pour {} {} à mettre à jour", person.getFirstName(), person.getLastName());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La personne " + person.getFirstName() + " " + person.getLastName() + " non trouvée...");
        }

         
    }

    /**
     * Endpoint pour supprimer une personne.
     *
     * @param firstName Le prénom de la personne.
     * @param lastName  Le nom de famille de la personne.
     * @return Une réponse indiquant le statut de l'opération.
     */
    @DeleteMapping("/person")
    public ResponseEntity<?> deletePerson(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        
        Optional<Person> Persondeleted = personService.deletePerson(firstName, lastName);


        if(Persondeleted.isPresent()) {
            logger.info("Personne supprimée avec succès pour {} {}", firstName, lastName);
            return ResponseEntity.ok(Persondeleted.get());
        } else {
            //System.out.println("Caserne de pompiers non trouvée");
            logger.warn("Aucune personne trouvée pour {} {} à supprimer", firstName, lastName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La personne " + firstName + " " + lastName + " non trouvée...");
        }

    }


}