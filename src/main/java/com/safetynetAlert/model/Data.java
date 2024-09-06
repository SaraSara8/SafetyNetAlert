package com.safetynetAlert.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Représente un ensemble de données comprenant des personnes, des casernes de pompiers et des dossiers médicaux.
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

    /**
     * Ajoute une nouvelle personne à la liste des personnes.
     *
     * @param person la personne à ajouter.
     */
    public void addPerson(Person person) {
        persons.add(person);
    }

    /**
     * Met à jour les informations d'une personne existante.
     * Si une personne avec le même prénom et nom de famille est trouvée, elle est remplacée par la nouvelle version.
     *
     * @param updatedPerson la personne mise à jour.
     */
    public void updatePerson(Person updatedPerson) {
        persons = persons.stream()
                .map(p -> (p.getFirstName().equals(updatedPerson.getFirstName()) &&
                        p.getLastName().equals(updatedPerson.getLastName())) ? updatedPerson : p)
                .collect(Collectors.toList());
    }

    /**
     * Supprime une personne de la liste en fonction de son prénom et de son nom.
     *
     * @param firstName le prénom de la personne à supprimer.
     * @param lastName le nom de famille de la personne à supprimer.
     * @return true si la personne a été trouvée et supprimée, sinon false.
     */
    public Person deletePerson(String firstName, String lastName) {
    	
    	Person persontoDelete = findPersonByName(firstName, lastName);
        persons.removeIf(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName));
        return persontoDelete;
    }

    /**
     * Recherche une personne par son prénom et son nom de famille.
     *
     * @param firstName le prénom de la personne.
     * @param lastName le nom de famille de la personne.
     * @return la personne trouvée ou null si aucune personne ne correspond.
     */
    public Person findPersonByName(String firstName, String lastName) {
        return persons.stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName) &&
                        p.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Ajoute une nouvelle caserne de pompiers à la liste des casernes.
     *
     * @param firestation la caserne de pompiers à ajouter.
     */
    public void addFirestation(FireStation firestation) {
        firestations.add(firestation);
    }

    /**
     * Met à jour les informations d'une caserne de pompiers existante.
     * Si une caserne avec la même adresse est trouvée, elle est remplacée par la nouvelle version.
     *
     * @param updatedFirestation la caserne de pompiers mise à jour.
     */
    public void updateFirestation(FireStation updatedFirestation) {
        firestations = firestations.stream()
                .map(f -> (f.getAddress().equals(updatedFirestation.getAddress())) ? updatedFirestation : f)
                .collect(Collectors.toList());
    }

    /**
     * Supprime une caserne de pompiers en fonction de son adresse et de son numéro de station.
     *
     * @param address l'adresse de la caserne de pompiers à supprimer.
     * @param stationNumber le numéro de la station de la caserne de pompiers à supprimer.
     * @return true si la caserne de pompiers a été trouvée et supprimée, sinon false.
     */
    public boolean deleteFirestation(String address, String stationNumber) {
        if (!address.equals("-1") && !stationNumber.equals("-1")) {
            return firestations.removeIf(f -> f.getAddress().equals(address) && f.getStation().equals(stationNumber));
        } else {
            if (!stationNumber.equals("-1")) {
                return firestations.removeIf(f -> f.getStation().equals(stationNumber));
            } else if (!address.equals("-1")) {
                return firestations.removeIf(f -> f.getAddress().equals(address));
            }
        }
        return false;
    }

    /**
     * Recherche une caserne de pompiers par son adresse.
     *
     * @param address l'adresse de la caserne de pompiers.
     * @return la caserne de pompiers trouvée ou un Optional vide si aucune caserne ne correspond.
     */
    public Optional<FireStation> findFirestationByAddress(String address) {
        return firestations.stream()
                .filter(f -> f.getAddress().equalsIgnoreCase(address))
                .findFirst();
    }

    /**
     * Ajoute un nouveau dossier médical à la liste des dossiers médicaux.
     *
     * @param medicalRecord le dossier médical à ajouter.
     */
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        medicalrecords.add(medicalRecord);
    }

    /**
     * Met à jour les informations d'un dossier médical existant.
     * Si un dossier avec le même prénom et nom de famille est trouvé, il est remplacé par la nouvelle version.
     *
     * @param updatedMedicalRecord le dossier médical mis à jour.
     */
    public void updateMedicalRecord(MedicalRecord updatedMedicalRecord) {
        medicalrecords = medicalrecords.stream()
                .map(m -> (m.getFirstName().equals(updatedMedicalRecord.getFirstName()) &&
                        m.getLastName().equals(updatedMedicalRecord.getLastName())) ? updatedMedicalRecord : m)
                .collect(Collectors.toList());
    }

    /**
     * Supprime un dossier médical en fonction du prénom et du nom de famille de la personne.
     *
     * @param firstName le prénom de la personne.
     * @param lastName le nom de famille de la personne.
     * @return true si le dossier médical a été supprimé, sinon false.
     */
    public boolean deleteMedicalRecord(String firstName, String lastName) {
        return medicalrecords.removeIf(m -> m.getFirstName().equals(firstName) &&
                m.getLastName().equals(lastName));
    }

    /**
     * Recherche un dossier médical par le prénom et le nom de famille de la personne.
     *
     * @param firstName le prénom de la personne.
     * @param lastName le nom de famille de la personne.
     * @return le dossier médical trouvé ou un Optional vide si aucun dossier ne correspond.
     */
    public MedicalRecord getMedicalRecordByName(String firstName, String lastName) {
        boolean found = false;
        MedicalRecord medicalRecord = null;
        Iterator<MedicalRecord> recordIterator = this.medicalrecords.iterator();
        while (recordIterator.hasNext() && !found) {
            medicalRecord = recordIterator.next();
            if (medicalRecord.getFirstName().equalsIgnoreCase(firstName) &&
                    medicalRecord.getLastName().equalsIgnoreCase(lastName)) {
                found = true;
            }
        }
        return medicalRecord;
    }

    /**
     * Obtenir les dossiers médicaux d'une personne.
     *
     * @param firstName Le prénom de la personne.
     * @param lastName Le nom de famille de la personne.
     * @return Une map des dossiers médicaux.
     */
    public Map<String, List<String>> getMedicalRecords(String firstName, String lastName) {
        return medicalrecords.stream()
                .filter(m -> m.getFirstName().equalsIgnoreCase(firstName) && m.getLastName().equalsIgnoreCase(lastName))
                .map(m -> {
                    Map<String, List<String>> medicalInfo = new HashMap<>();
                    medicalInfo.put("medications", m.getMedications());
                    medicalInfo.put("allergies", m.getAllergies());
                    return medicalInfo;
                })
                .findFirst()
                .orElse(new HashMap<>());
    }

    /**
     * Obtenier les informations d'une personne par leur nom de famille et leur prénom.
     *
     * @param firstName Le prénom de la personne.
     * @param lastName Le nom de famille de la personne.
     * @return un objet Person s'il existe.
     */
    private Optional<Person> getPersonByName(String firstName, String lastName) {
        return persons.stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName))
                .findFirst();
    }
}