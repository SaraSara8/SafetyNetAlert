package com.safetynetAlert.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Représente un ensemble de données comprenant des personnes, des casernes de pompiers et des dossiers médicaux.
  * @author Your Name
 * @version 1.0
 */
public class Data {
    private List<Person> persons;
    private List<FireStation> firestations;
    private List<MedicalRecord> medicalrecords;

    /**
     * Constructeur de la classe Data.
     *
     * @param persons Liste des personnes.
     * @param firestations Liste des casernes de pompiers.
     * @param medicalRecords Liste des dossiers médicaux.
     */
    public Data(List<Person> persons, List<FireStation> firestations, List<MedicalRecord> medicalRecords) {
        this.persons = persons;
        this.firestations = firestations;
        this.medicalrecords = medicalRecords;
    }

// Getters et Setters

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<FireStation> getFirestations() {
        return firestations;
    }

    public void setFirestations(List<FireStation> firestations) {
        this.firestations = firestations;
    }

    public List<MedicalRecord> getMedicalrecords() {
        return medicalrecords;
    }

    public void setMedicalrecords(List<MedicalRecord> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }

    
}