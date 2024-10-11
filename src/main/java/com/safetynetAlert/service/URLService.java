package com.safetynetAlert.service;

import com.safetynetAlert.dto.*;
import com.safetynetAlert.mapper.HouseHoldChildDTOMapper;
import com.safetynetAlert.mapper.PersonDTOMapper;
import com.safetynetAlert.mapper.PersonStationDTOMapper;
import com.safetynetAlert.mapper.PersonInfoDTOMapper;
import com.safetynetAlert.model.*;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;



/**
 * Gère la logique métier liée aux URLs spécifiques, comme la recherche des personnes
 * courvertes par les casenres ou l'envoi d'alertes
 * @author SaraHaimeur
 *
 */
@Service
public class URLService {

    @Setter
    @Getter
    @Autowired
    private JsonDataBase jsonData;
    final private PersonDTOMapper personDTOMapper;
    final private HouseHoldChildDTOMapper houseHoldDTOMapper;
    final private MedicalRecordService medicalRecordService;
    final private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private static final Logger logger = LogManager.getLogger(URLService.class);

    public URLService(JsonDataBase jsonData,
            PersonDTOMapper personDTOMapper,
            HouseHoldChildDTOMapper houseHoldDTOMapper,
            MedicalRecordService medicalRecordService) {
        this.jsonData = jsonData;
        this.personDTOMapper = personDTOMapper;
        this.houseHoldDTOMapper = houseHoldDTOMapper;
        this.medicalRecordService = medicalRecordService;

    }

    /**
     * Obtenir l'age d'une personne.
     *
     * @param firstName Le prénom de la personne.
     * @param lastName  Le nom de famille de la personne.
     * @return un entier qui contient l'age.
     */
    private int getAge(String firstName, String lastName) {

        int age = -1;

        Optional<MedicalRecord> medicalRecordOptional = medicalRecordService.getMedicalRecordByName(firstName,
                lastName);

        if (medicalRecordOptional.isPresent()) {
            LocalDate birthDate = LocalDate.parse(medicalRecordOptional.get().getBirthdate(), dateFormatter);
            age = Period.between(birthDate, LocalDate.now()).getYears();
        }

        logger.info("L'âge de la personne {} {} a été récupéré avec succès", firstName, lastName);
        return age;

    }

    /**
     * Obtenir une liste des personnes couvertes par une station de pompiers
     * spécifique.
     *
     * @param stationNumber Le numéro de la station de pompiers.
     * @return Une liste de personnes avec leurs détails, y compris un décompte des
     *         adultes et des enfants.
     */
    public Map<String, Object> getPersonsCoveredByStation(String stationNumber) {

        Data data = jsonData.getData();

        List<String> addresses = jsonData.getData().getFirestations().stream()
                .filter(firestation -> firestation.getStation().equals(stationNumber))
                .map(FireStation::getAddress)
                .collect(Collectors.toList());


        List<Person> personsCovered = data.getPersons().stream()
                .filter(person -> addresses.contains(person.getAddress()))
                .collect(Collectors.toList());
        

        long adults = personsCovered.stream().filter(person -> getAge(person.getFirstName(), person.getLastName()) > 18)
                .count();
        long children = personsCovered.stream()
                .filter(person -> getAge(person.getFirstName(), person.getLastName()) <= 18).count();


        Map<String, Object> result;
        result = new HashMap<>();

        if (!personsCovered.isEmpty()) {
            result.put("persons", personsCovered.stream().map(personDTOMapper));
            result.put("adults", adults);
            result.put("children", children);

            logger.info("Personnes couvertes récupérées avec succès pour la station : {}", stationNumber);
        } else {
            logger.warn("Aucune personne couverte trouvée pour la station : {}", stationNumber);
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
                .filter(person -> person.getAddress().equals(address)
                        && getAge(person.getFirstName(), person.getLastName()) <= 18)
                .map(houseHoldDTOMapper)
                .collect(Collectors.toList());


        for (HouseholdChildDTO child : houseHolds) {
            List<PersonDTO> familyPersons = data.getPersons().stream()
                    .filter(person -> person.getAddress().equals(address)
                            && !(person.getFirstName().equals(child.getFirstName())))
                    .map(personDTOMapper)
                    .collect(Collectors.toList());

            // add child old & list of family mombers
            child.setAge(getAge(child.getFirstName(), child.getLastName()));
            child.setFamilyPersons(familyPersons);
        }

        Map<String, Object> result = new HashMap<>();
        if (!houseHolds.isEmpty()) {
            result.put("children", houseHolds);
            logger.info("Enfants récupérés avec succès habitant à l'adresse : {}", address);
        } else {
            logger.warn("Aucun enfant trouvé habitant à l'adresse : {}", address);
        }
        return result;
    }

    /**
     * Obtenir une liste de numéros de téléphone des personnes couvertes par une
     * station de pompiers spécifique.
     *
     * @param stationNumber Le numéro de la station de pompiers.
     * @return Une liste de numéros de téléphone.
     */
    public Map<String, Object> getPhoneNumbersByStation(String stationNumber) {

        Data data = jsonData.getData();

        List<String> addresses = data.getFirestations().stream()
                .filter(firestation -> firestation.getStation().equals(stationNumber))
                .map(FireStation::getAddress)
                .collect(Collectors.toList());

        List<String> phonesCovered = data.getPersons().stream()
                .filter(person -> addresses.contains(person.getAddress()))
                .map(Person::getPhone)
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        if (!phonesCovered.isEmpty()) {
            result.put("phones", phonesCovered);
            logger.info("Numéros de téléphone récupérés avec succès pour la station : {}", stationNumber);
        } else {
            logger.warn("Aucun numéro de téléphone trouvé pour la station : {}", stationNumber);
        }
        return result;
    }

    /**
     * Obtenir une liste de personnes vivant à une adresse spécifique avec le numéro
     * de leur station de pompiers.
     *
     * @param address L'adresse recherchée.
     * @return Une liste de personnes avec leurs détails et dossiers médicaux.
     */
    public Map<String, Object> getPersonsByAddress(String address) {

        Data data = jsonData.getData();

        List<PersonMedicalRecordDTO> personsCovered = new ArrayList<>();
        for (Person person : data.getPersons()) {

            if (person.getAddress().equals(address)) {
            
                PersonMedicalRecordDTO personMedicalRecordDTO = new PersonMedicalRecordDTO(
                        person.getFirstName(),
                        person.getLastName(),
                        person.getPhone());

                personMedicalRecordDTO.setAge(getAge(person.getFirstName(), person.getLastName()));

                Optional<MedicalRecord> medicOptional = medicalRecordService.getMedicalRecordByName(
                        person.getFirstName(),
                        person.getLastName());
                if (medicOptional.isPresent()) {
                    personMedicalRecordDTO.setMedications(medicOptional.get().getMedications());
                    personMedicalRecordDTO.setAllergies(medicOptional.get().getAllergies());
                }
                personsCovered.add(personMedicalRecordDTO);
            }
        }

        logger.info("Le nombre de personsCovered est : {} ", personsCovered.size());

        // recuperation du numero de station de cette address
        String numberStation = data.getFirestations().stream()
                .filter(firestation -> firestation.getAddress().equals(address))
                .map(FireStation::getStation)
                .findFirst()
                .orElse("");

        logger.info("le numero de station : {} ", numberStation);

        PersonStationDTO personStationDTO = new PersonStationDTO();
        personStationDTO.setPersonMedicalRecordDTOs(personsCovered);
        personStationDTO.setStation(numberStation);

        Map<String, Object> result = new HashMap<>();
        if (!personsCovered.isEmpty()) {
            result.put("persons", personStationDTO);
            logger.info("Les personnes ont été récupérées avec succès par adresse : {}", address);
        } else {
            logger.warn("Aucune personne n'a été trouvée pour l'adresse : {}", address);
        }
        return result;
    }

    /**
     * Obtenir une liste de tous les foyers couverts par les numéros de station de
     * pompiers donnés, regroupés par adresse.
     *
     * @param stationNumbers Les numéros des stations de pompiers.
     * @return Une liste de foyers regroupés par adresse.
     */
    public Map<String, Object> getHouseholdsByStations(String[] stationNumbers) {

        // parcourir toute les stations
        Data data = jsonData.getData();

        List<String> addresses = data.getFirestations().stream()
                .filter(firestation -> Arrays.stream(stationNumbers).collect(Collectors.toList())
                        .contains(firestation.getStation()))
                .map(FireStation::getAddress)
                .distinct()
                .collect(Collectors.toList());

        List<HouseholdDTO> households = new ArrayList<>();

        for (String address : addresses) {
            
            List<PersonMedicalRecordDTO> personsCovered = new ArrayList<>();
            for (Person person : data.getPersons()) {

                if (person.getAddress().equals(address)) {
                    
                    PersonMedicalRecordDTO personMedicalRecordDTO = new PersonMedicalRecordDTO(
                            person.getFirstName(),
                            person.getLastName(),
                            person.getPhone());

                    personMedicalRecordDTO.setAge(getAge(person.getFirstName(), person.getLastName()));

                    Optional<MedicalRecord> medicOptional = medicalRecordService.getMedicalRecordByName(
                            person.getFirstName(),
                            person.getLastName());
                    if (medicOptional.isPresent()) {
                        personMedicalRecordDTO.setMedications(medicOptional.get().getMedications());
                        personMedicalRecordDTO.setAllergies(medicOptional.get().getAllergies());
                    }
                    personsCovered.add(personMedicalRecordDTO);
                }
            }

            HouseholdDTO household = new HouseholdDTO(address, personsCovered);
            households.add(household);

        }

        Map<String, Object> result = new HashMap<>();
        if (!households.isEmpty()) {
            result.put("households", households);
            logger.info("Foyer récupéré avec succès our les stations: {}", Arrays.toString(stationNumbers));
        } else {
            logger.warn("Aucun foyer trouvé pour les stations : {}", Arrays.toString(stationNumbers));
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
 
        List<PersonInfoDTO> personsInfo = new ArrayList<>();
        for (Person person : data.getPersons()) {

            if (person.getLastName().equals(lastName)) {
                
                PersonInfoDTO personInfoDTO = new PersonInfoDTO(
                        person.getFirstName(),
                        person.getLastName(),
                        person.getAddress(),
                        person.getEmail());

                        personInfoDTO.setAge(getAge(person.getFirstName(), person.getLastName()));

                Optional<MedicalRecord> medicOptional = medicalRecordService.getMedicalRecordByName(
                        person.getFirstName(),
                        person.getLastName());
                if (medicOptional.isPresent()) {
                    personInfoDTO.setMedications(medicOptional.get().getMedications());
                    personInfoDTO.setAllergies(medicOptional.get().getAllergies());
                }
                personsInfo.add(personInfoDTO);
            }
        }


        Map<String, Object> result = new HashMap<>();

        if (!personsInfo.isEmpty()) {
            result.put("persons", personsInfo);
            logger.info("Personnes récupérées avec succès par nom de famille: {}", lastName);
        } else {
            logger.warn("Aucune personne trouvée avec le nom de famille : {}", lastName);
        }
        return result;
    }

    /**
     * Obtenir une liste d'adresses email de toutes les personnes dans une ville
     * spécifique.
     *
     * @param city La ville recherchée.
     * @return Une liste d'adresses email.
     */
    public Map<String, Object> getEmailsByCity(String city) {
        Data data = jsonData.getData();
        List<String> emails = data.getPersons().stream()
                .filter(person -> person.getCity().equals(city))
                .map(Person::getEmail)
                .distinct()
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        if (!emails.isEmpty()) {
            result.put("emails", emails);
            logger.info("Emails récupérés avec succès par ville: {}", city);
        } else {
            logger.warn("Aucun email trouvé pour la ville : {}", city);
        }
        return result;

    }
}