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

/**
 * Gère l'ajout, la mise à jour et la suppression des personnes
 * 
 * @author SaraHaimeur
 *
 */
@Service
public class PersonService {

    @Setter
    @Getter
    @Autowired
    private JsonDataBase jsonData;

    
    private static final Logger logger = LogManager.getLogger(PersonService.class);

    public PersonService(JsonDataBase jsonData) {
        this.jsonData = jsonData;
    }

    /**
     * Recherche une personne par son prénom et son nom de famille.
     *
     * @param firstName le prénom de la personne.
     * 
     * @param lastName le nom de famille de la personne.
     * 
     * @return la personne trouvée ou null si aucune personne ne correspond.
     */
    public Optional<Person> findPersonByName(String firstName, String lastName) {

        Optional<Person> person = jsonData.getData().getPersons().stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName) &&
                        p.getLastName().equalsIgnoreCase(lastName))
                .findFirst();
        if (!person.isPresent()) {
            logger.info("Personne non trouvée pour le prénom {} et le nom de famille {}", firstName, lastName);
        } else {
            logger.info("Personne trouvée pour le prénom {} et le nom de famille {}", firstName, lastName);
        }
        return person;

    }

    /**
     * Aouter une nouvelle personne.
     *
     * @param person L'objet personne à ajouter.
     */
    public Optional<Person> addPerson(Person person) {

        Data data = jsonData.getData();

        // Vérifier si la personne existe déjà dans la base de données
        Optional<Person> existingPerson = findPersonByName(person.getFirstName(), person.getLastName());

        // Si la personne n'existe pas ajouter la personne à la base de données
        if (!existingPerson.isPresent()) {

            data.getPersons().add(person);
            jsonData.saveData(data);
            logger.info("Personne ajoutée avec succès pour le prénom {} et le nom de famille {}", person.getFirstName(), person.getLastName());
        } else {
            logger.warn("La personne pour le prénom {} et le nom de famille {} existe déjà", person.getFirstName(), person.getLastName());
        }

        return existingPerson;

    }

    /**
     * Endpoint pour mettre à jour une personne existante.
     *
     * @param person L'objet personne mis à jour.
     */
    public Optional<Person> updatePerson(Person person) {

        Data data = jsonData.getData();

        // Recherche de la personne dans la liste des personnes
        Optional<Person> personToUpdate = findPersonByName(person.getFirstName(), person.getLastName());

        // Si la personne est trouvée, la mettre à jour
        if (personToUpdate.isPresent()) {
            List<Person> updatedPersons = data.getPersons().stream()
                    .map(p -> (p.getFirstName().equals(person.getFirstName()) &&
                            p.getLastName().equals(person.getLastName())) ? person : p)
                    .collect(Collectors.toList());

            data.setPersons(updatedPersons);
            jsonData.saveData(data);
            logger.info("Personne mise à jour avec succès pour le prénom {} et le nom de famille {}", person.getFirstName(), person.getLastName());
        } else {
            logger.warn("Aucune personne trouvée pour le prénom {} et le nom de famille {} à mettre à jour", person.getFirstName(), person.getLastName());
        }

        // Retourner la personne mise à jour
        return personToUpdate;
    }

    /**
     * Endpoint pour supprimer une personne.
     *
     * @param firstName Le prénom de la personne.
     * @param lastName  Le nom de famille de la personne.
     */
    public Optional<Person> deletePerson(String firstName, String lastName) {

        Data data = jsonData.getData();

        // Recherche de la personne dans la liste des personnes
        Optional<Person> personToDelete = findPersonByName(firstName, lastName);

        // Si la personne est trouvée, la supprimer de la liste des personnes
        if (personToDelete.isPresent()) {

            data.getPersons().remove(personToDelete.get());
            jsonData.saveData(data);
            logger.info("Personne supprimée avec succès pour le prénom {} et le nom de famille {}", firstName, lastName);
        } else {
            logger.warn("Aucune personne trouvée pour le prénom {} et le nom de famille {} à supprimer", firstName, lastName);
        }

        return personToDelete;

    }

}
