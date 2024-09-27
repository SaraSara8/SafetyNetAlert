package com.safetynetAlert.model;

/**
 * Cette classe permet de représenter l'objet person du fichier Json.
 * @author  Your Name
 * @version 1.0
 */
public class Person {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;

    /**
     * Constructeur par défaut.
     */
    public Person() {
    }

    /**
     * Constructeur avec paramètres.
     *
     * @param firstName Le prénom.
     * @param lastName Le nom de famille.
     * @param address L'adresse.
     * @param city La ville.
     * @param zip Le code postal.
     * @param phone Le numéro de téléphone.
     * @param email L'adresse email.
     */
    public Person(String firstName, String lastName, String address, String city, String zip, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
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
     * Retourne le nom de famille.
     *
     * @return Le nom de famille.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Retourne l'adresse.
     *
     * @return L'adresse.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Retourne la ville.
     *
     * @return La ville.
     */
    public String getCity() {
        return city;
    }

    /**
     * Retourne le code postal.
     *
     * @return Le code postal.
     */
    public String getZip() {
        return zip;
    }

    /**
     * Retourne le numéro de téléphone.
     *
     * @return Le numéro de téléphone.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Retourne l'adresse email.
     *
     * @return L'adresse email.
     */
    public String getEmail() {
        return email;
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
     * Définit le nom de famille.
     *
     * @param lastName Le nom de famille à définir.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Définit l'adresse.
     *
     * @param address L'adresse à définir.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Définit la ville.
     *
     * @param city La ville à définir.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Définit le code postal.
     *
     * @param zip Le code postal à définir.
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * Définit le numéro de téléphone.
     *
     * @param phone Le numéro de téléphone à définir.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Définit l'adresse email.
     *
     * @param email L'adresse email à définir.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}