package com.safetynetAlert.service;

import com.safetynetAlert.dto.*;
import com.safetynetAlert.mapper.HouseHoldChildDTOMapper;
import com.safetynetAlert.mapper.HouseholdStationDTOMapper;
import com.safetynetAlert.mapper.PersonDTOMapper;
import com.safetynetAlert.mapper.PersonStationDTOMapper;
import com.safetynetAlert.mapper.PersonInfoDTOMapper;
import com.safetynetAlert.model.*;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AlertService {

    @Setter
    @Getter
    @Autowired
    private JsonDataBase jsonData;
    final private PersonDTOMapper personDTOMapper;
    final private HouseHoldChildDTOMapper houseHoldDTOMapper;
    final private PersonStationDTOMapper personStationDTOMapper;
    final private HouseholdStationDTOMapper householdStationDTOMapper;
    final private PersonInfoDTOMapper personInfoDTOMapper;

    final private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");


    public AlertService(JsonDataBase jsonData, PersonDTOMapper personDTOMapper, HouseHoldChildDTOMapper childDTOMapper,
                        PersonStationDTOMapper personStationDTOMapper, HouseholdStationDTOMapper personAddressDTOMapper, PersonInfoDTOMapper personInfoDTOMapper) {
        this.jsonData = jsonData;
        this.personDTOMapper = personDTOMapper;
        this.houseHoldDTOMapper = childDTOMapper;
        this.personStationDTOMapper = personStationDTOMapper;
        this.householdStationDTOMapper = personAddressDTOMapper;
        this.personInfoDTOMapper = personInfoDTOMapper;
    }



    /**
     * Obtenir l'age d'une personne.
     *
     * @param firstName Le prénom de la personne.
     * @param lastName  Le nom de famille de la personne.
     * @return un entier qui contient l'age.
     */
    private int getAge(String firstName, String lastName) {

    	Data data = jsonData.getData();
        MedicalRecord medicalRecord = data.getMedicalRecordByName(firstName, lastName);

        LocalDate birthDate = LocalDate.parse(medicalRecord.getBirthdate(), dateFormatter);
        return Period.between(birthDate, LocalDate.now()).getYears();

    }


    /**
     * Obtenir une liste des personnes couvertes par une station de pompiers spécifique.
     *
     * @param stationNumber Le numéro de la station de pompiers.
     * @return Une liste de personnes avec leurs détails, y compris un décompte des adultes et des enfants.
     */
    public Map<String, Object> getPersonsCoveredByStation(int stationNumber) {

        Data data = jsonData.getData();

        List<String> addresses = jsonData.getData().getFirestations().stream()
                .filter(firestation -> firestation.getStation().equals(String.valueOf(stationNumber)))
                .map(FireStation::getAddress)
                .collect(Collectors.toList());

        List<Person> personsCovered = data.getPersons().stream()
                .filter(person -> addresses.contains(person.getAddress()))
                .collect(Collectors.toList());

        long adults = personsCovered.stream().filter(person -> getAge(person.getFirstName(), person.getLastName()) > 18).count();
        long children = personsCovered.stream().filter(person -> getAge(person.getFirstName(), person.getLastName()) <= 18).count();

        Map<String, Object> result;
        result = new HashMap<>();

        if (!personsCovered.isEmpty()) {
            result.put("persons", personsCovered.stream().map(personDTOMapper));
            result.put("adults", adults);
            result.put("children", children);

        }
        return result;
    }


    /**
     * Obtenir une liste d'enfants vivant à une adresse spécifique.
     *
     * @param address L'adresse recherchée.
     * @return Une liste d'enfants et d'autres membres du foyer.
     */
    public Map<String, Object> getChildrenByAddress(String address) {

        Data data = jsonData.getData();

        List<HouseholdChildDTO> houseHolds = data.getPersons().stream()
                .filter(person -> person.getAddress().equals(address) && getAge(person.getFirstName(), person.getLastName()) <= 18)
                .map(houseHoldDTOMapper)
                .collect(Collectors.toList());

        for (HouseholdChildDTO child : houseHolds) {
            List<PersonDTO> familyPersons = data.getPersons().stream()
                    .filter(person -> person.getAddress().equals(address) && !(person.getFirstName().equals(child.getFirstName())))
                    .map(personDTOMapper)
                    .collect(Collectors.toList());

            // add child old & list of family mombers
            child.setAge(getAge(child.getFirstName(), child.getLastName()));
            child.setFamilyPersons(familyPersons);
        }

        Map<String, Object> result = new HashMap<>();
        if (!houseHolds.isEmpty()) {
            result.put("children", houseHolds);
        }
        return result;
    }


    /**
     * Obtenir une liste de numéros de téléphone des personnes couvertes par une station de pompiers spécifique.
     *
     * @param stationNumber Le numéro de la station de pompiers.
     * @return Une liste de numéros de téléphone.
     */
    public Map<String, Object> getPhoneNumbersByStation(int stationNumber) {

        Data data = jsonData.getData();

        List<String> addresses = data.getFirestations().stream()
                .filter(firestation -> firestation.getStation().equals(String.valueOf(stationNumber)))
                .map(FireStation::getAddress)
                .collect(Collectors.toList());

        List<String> phonesCovered = data.getPersons().stream()
                .filter(person -> addresses.contains(person.getAddress()))
                .map(Person::getPhone)
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        if (!phonesCovered.isEmpty()) {
            result.put("phones", phonesCovered);
        }
        return result;
    }


    /**
     * Obtenir une liste de personnes vivant à une adresse spécifique avec le numéro de leur station de pompiers.
     *
     * @param address L'adresse recherchée.
     * @return Une liste de personnes avec leurs détails et dossiers médicaux.
     */
    public Map<String, Object> getPersonsByAddress(String address) {

        Data data = jsonData.getData();

        List<PersonMedicalRecordDTO> personsCovered = data.getPersons().stream()
                .filter(person -> person.getAddress().equals(address))
                .map(personStationDTOMapper)
                .collect(Collectors.toList());

        // recuperation du numero de station de cette address
        String NumberStation = data.getFirestations().stream()
                .filter(firestation -> firestation.getAddress().equals(address))
                .map(FireStation::getStation)
                .findFirst()
                .orElse("");
        // charger l'age et les le medical records
        for (PersonMedicalRecordDTO person : personsCovered) {
        	person.setAge(getAge(person.getFirstName(), person.getLastName()));
            MedicalRecord record = data.getMedicalRecordByName(person.getFirstName(), person.getLastName());    
            person.setMedications(record.getMedications());
            person.setAllergies(record.getAllergies());
    
        }

        PersonStationDTO personStationDTO = new PersonStationDTO();
        personStationDTO.setPersonMedicalRecordDTOs(personsCovered);
        personStationDTO.setStation(NumberStation);
        
        
        Map<String, Object> result = new HashMap<>();
        if (!personsCovered.isEmpty()) {
            result.put("persons", personStationDTO);
        }
        return result;
    }


    /**
     * Obtenir une liste de tous les foyers couverts par les numéros de station de pompiers donnés, regroupés par adresse.
     *
     * @param stationNumbers Les numéros des stations de pompiers.
     * @return Une liste de foyers regroupés par adresse.
     */
    public Map<String, Object> getHouseholdsByStations(String[] stationNumbers) {

        //parcourir toute les stations
        Data data = jsonData.getData();

        List<String> addresses = data.getFirestations().stream()
                .filter(firestation -> Arrays.stream(stationNumbers).collect(Collectors.toList()).contains(firestation.getStation()))
                .map(FireStation::getAddress)
                .distinct()
                .collect(Collectors.toList());

        List<HouseholdDTO> households = new ArrayList<>();

        for (String address : addresses) {
            
            List<PersonMedicalRecordDTO> personsCovered = data.getPersons().stream()
                    .filter(person -> person.getAddress().equals(address))
                    .map(personStationDTOMapper)
                    .collect(Collectors.toList());

            for (PersonMedicalRecordDTO person : personsCovered) {

                person.setAge(getAge(person.getFirstName(), person.getLastName()));
                person.setMedications(data.getMedicalRecordByName(person.getFirstName(), person.getLastName()).getMedications());
                person.setAllergies(data.getMedicalRecordByName(person.getFirstName(), person.getLastName()).getAllergies());
            }

            HouseholdDTO household = new HouseholdDTO(address, personsCovered);
            households.add(household);

        }

        Map<String, Object> result = new HashMap<>();
        if (!households.isEmpty()) {
            result.put("households", households);
        }
        return result;
    }

    /**
     * Obtenir une liste de personnes par leur nom de famille.
     *
     * @param lastName Le nom de famille recherché.
     * @return Une liste de personnes avec leurs détails et dossiers médicaux.
     */
    public Map<String, Object> getPersonsByLastName(String lastName) {

        Data data = jsonData.getData();

        List<PersonInfoDTO> personsInfo = data.getPersons().stream()
                .filter(person -> person.getLastName().equals(lastName))
                .map(personInfoDTOMapper)
                .collect(Collectors.toList());

        for (PersonInfoDTO person : personsInfo) {
            person.setAge(getAge(person.getFirstName(), person.getLastName()));
            person.setMedications(data.getMedicalRecordByName(person.getFirstName(), person.getLastName()).getMedications());
            person.setAllergies(data.getMedicalRecordByName(person.getFirstName(), person.getLastName()).getAllergies());
        }

        Map<String, Object> result = new HashMap<>();

        if (!personsInfo.isEmpty()) {
            result.put("persons", personsInfo);
        }
        return result;
    }

    /**
     * Obtenir une liste d'adresses email de toutes les personnes dans une ville spécifique.
     *
     * @param city La ville recherchée.
     * @return Une liste d'adresses email.
     */
    public Map<String, Object> getEmailsByCity(String city) {
        Data data = jsonData.getData();
        List<String> citisCovered = data.getPersons().stream()
                .filter(person -> person.getCity().equals(city))
                .map(Person::getEmail)
                .distinct()
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        if (!citisCovered.isEmpty()) {
            result.put("emails", citisCovered);
        }
        return result;

    }


    /**
     * Aouter une nouvelle personne.
     *
     * @param person L'objet personne à ajouter.
     */
    public Person addPerson(Person person) {

        Data data = jsonData.getData();
        data.addPerson(person);
        jsonData.saveData(data);
        return person;

    }


    /**
     * Endpoint pour mettre à jour une personne existante.
     *
     * @param person L'objet personne mis à jour.
     */
    public Person updatePerson(Person person) {
        Data data = jsonData.getData();
        data.updatePerson(person);
        jsonData.saveData(data);
        return person;
    }

    /**
     * Endpoint pour supprimer une personne.
     *
     * @param firstName Le prénom de la personne.
     * @param lastName  Le nom de famille de la personne.
     */
    public Person deletePerson(String firstName, String lastName) {
        Data data = jsonData.getData();
        Person persondeleted = data.deletePerson(firstName, lastName);
        jsonData.saveData(data);
        return persondeleted;
        
        
    }


    /**
     * Service pour ajouter une nouvelle cartographie de caserne de pompiers.
     *
     * @param fireStation L'objet cartographie de caserne de pompiers à ajouter.
     */
    public void addFireStation(FireStation fireStation) {
        Data data = jsonData.getData();
        data.addFirestation(fireStation);
        jsonData.saveData(data);
    }

    /**
     * Endpoint pour mettre à jour une cartographie de caserne de pompiers existante.
     *
     * @param fireStation L'objet cartographie de caserne de pompiers mis à jour.
     */
    public void updateFireStation(FireStation fireStation) {
        Data data = jsonData.getData();
        data.updateFirestation(fireStation);
        jsonData.saveData(data);
    }

    /**
     * Endpoint pour supprimer une cartographie de caserne de pompiers.
     *
     * @param address       L'adresse de la cartographie à supprimer.
     * @param stationNumber Le numéro de la caserne de pompiers de la cartographie à supprimer.
     */
    public void deleteFireStation(String address, String stationNumber) {

        Data data = jsonData.getData();

        if (data.deleteFirestation(address, stationNumber)) {
            jsonData.saveData(data);
        }
    }


    /**
     * Endpoint pour supprimer une cartographie de caserne de pompiers à partir de l'address
     *
     * @param address L'adresse de la cartographie à supprimer.
     */
    public void deleteFireStationByAddress(String address) {

        Data data = jsonData.getData();

        if (data.deleteFirestation(address, "-1")) {
            jsonData.saveData(data);
        }
    }


    /**
     * Endpoint pour supprimer une cartographie de caserne de pompiers à partir de l'address
     *
     * @param stationNumber Le numéro de la caserne de pompiers de la cartographie à supprimer.
     */
    public void deleteFireStationByStation(String stationNumber) {

        Data data = jsonData.getData();

        if (data.deleteFirestation("-1", stationNumber)) {
            jsonData.saveData(data);
        }
    }


    /**
     * Service pour ajouter un nouveau dossier médical.
     * @param medicalRecord L'objet dossier médical à ajouter.
     */
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        Data data = jsonData.getData();
        data.addMedicalRecord(medicalRecord);
        jsonData.saveData(data);
    }

    /**
     * Service pour mettre à jour un dossier médical existant.
     * @param medicalRecord L'objet dossier médical mis à jour.
     */
    public void updateMedicalRecord(MedicalRecord medicalRecord) {
        Data data = jsonData.getData();
        data.updateMedicalRecord(medicalRecord);
        jsonData.saveData(data);
    }

    /**
     * Service pour supprimer un dossier médical.
     * @param firstName Le prénom de la personne dont le dossier doit être supprimé.
     * @param lastName Le nom de famille de la personne dont le dossier doit être supprimé.
     */
    public void deleteMedicalRecord(String firstName, String lastName) {
        Data data = jsonData.getData();
        if (data.deleteMedicalRecord(firstName, lastName)) {
            jsonData.saveData(data);
        }
    }





}
