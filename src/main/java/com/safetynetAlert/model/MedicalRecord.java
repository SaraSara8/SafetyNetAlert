package com.safetynetAlert.model;

import java.util.List;

/**
 * Cette classe représente l'objet medicalrecord dans le fichier Json.
 * @author Your Name
 * @version 1.0
 */
public class MedicalRecord {

    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;

    /**
     * Constructeur par défaut.
     */
    public MedicalRecord() {
    }

    /**
     * Constructeur avec paramètres.
     *
     * @param firstName Le prénom.
     * @param lastName Le nom de famille.
     * @param birthdate La date de naissance.
     * @param medications La liste des médicaments.
     * @param allergies La liste des allergies.
     */
    public MedicalRecord(String firstName, String lastName, String birthdate, List<String> medications, List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
    }

    /**
     * Retourne le prénom.
     *
     * @return Le prénom.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Définit le prénom.
     *
     * @param firstName Le prénom à définir.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retourne le nom de famille.
     *
     * @return Le nom de famille.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Définit le nom de famille.
     *
     * @param lastName Le nom de famille à définir.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retourne la date de naissance.
     *
     * @return La date de naissance.
     */
    public String getBirthdate() {
        return birthdate;
    }

    /**
     * Définit la date de naissance.
     *
     * @param birthdate La date de naissance à définir.
     */
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * Retourne la liste des médicaments.
     *
     * @return La liste des médicaments.
     */
    public List<String> getMedications() {
        return medications;
    }

    /**
     * Définit la liste des médicaments.
     *
     * @param medications La liste des médicaments à définir.
     */
    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    /**
     * Retourne la liste des allergies.
     *
     * @return La liste des allergies.
     */
    public List<String> getAllergies() {
        return allergies;
    }

    /**
     * Définit la liste des allergies.
     *
     * @param allergies La liste des allergies à définir.
     */
    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }
}