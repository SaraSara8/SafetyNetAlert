package com.safetynetAlert.service;

import com.safetynetAlert.model.*;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;


@Service
public class FireStationService {

    @Setter
    @Getter
    @Autowired
    private JsonDataBase jsonData;

    private static final Logger logger = LogManager.getLogger(FireStationService.class);

    public FireStationService(JsonDataBase jsonData) {
        this.jsonData = jsonData;
    }

    /**
     * Service pour ajouter une nouvelle cartographie de caserne de pompiers.
     *
     * @param fireStation L'objet cartographie de caserne de pompiers à ajouter.
     */
    public Optional<FireStation> addFireStation(FireStation fireStation) {

        Data data = jsonData.getData();

        // Vérifier si la personne existe déjà dans la base de données
        Optional<FireStation> existingFireStation = findFirestationByAddress(fireStation.getAddress());

        // Si la personne n'existe pas ajouter la personne à la base de données
        if (!existingFireStation.isPresent()) {

            data.getFirestations().add(fireStation);
            jsonData.saveData(data);
            logger.info("Firestation ajoutée : {}", fireStation);
        } else {
            logger.warn("Impossible d'ajouter la Firestation, elle existe déjà : {}", fireStation);
        }

        return existingFireStation;

    }

    /**
     * Endpoint pour mettre à jour une cartographie de caserne de pompiers
     * existante.
     *
     * @param fireStation L'objet cartographie de caserne de pompiers mis à jour.
     */
    public Optional<FireStation> updateFireStation(FireStation fireStation) {

        Data data = jsonData.getData();
        Optional<FireStation> fireStationToUpdate = findFirestationByAddress(fireStation.getAddress());

        if (fireStationToUpdate.isPresent()) {
            data.getFirestations().remove(fireStationToUpdate.get());
            data.getFirestations().add(fireStation);
            jsonData.saveData(data);
            logger.info("Firestation mise à jours : {}", fireStation);
        } else {
            logger.warn("Impossible de mettre à jour la Firestation, elle n'existe pas : {}", fireStation);
        }

        return fireStationToUpdate;
    }

    /**
     * Endpoint pour supprimer une cartographie de caserne de pompiers à partir de
     * l'address
     *
     * @param address L'adresse de la cartographie à supprimer.
     */
    public Optional<FireStation> deleteFireStationByAddress(String address) {

        Data data = jsonData.getData();
        Optional<FireStation> fireStationToDelete = findFirestationByAddress(address);

        if (fireStationToDelete.isPresent()) {
            data.getFirestations().remove(fireStationToDelete.get());
            jsonData.saveData(data);
            logger.info("Firestation supprimée par adresse : {}", address);
        } else {
            logger.warn("Impossible de supprimer la Firestation, elle n'existe pas : {}", address);
        }

        return fireStationToDelete;
    }

    /**
     * Endpoint pour supprimer une cartographie de caserne de pompiers à partir de
     * l'address
     *
     * @param stationNumber Le numéro de la caserne de pompiers de la cartographie à
     *                      supprimer.
     */
    public Optional<FireStation> deleteFireStationByStation(String stationNumber) {

        Data data = jsonData.getData();

        Optional<FireStation> fireStationToDelete = findFirestationByStation(stationNumber);

        if (fireStationToDelete.isPresent()) {
            data.getFirestations().remove(fireStationToDelete.get());
            jsonData.saveData(data);
            logger.info("Firestation supprimée par numéro de station : {}", stationNumber);
        } else {
            logger.info("Firestation non trouvée par numéro de station : {}", stationNumber);
        }

        return fireStationToDelete;
    }

    /**
     * Recherche une caserne de pompiers par son adresse.
     *
     * @param address l'adresse de la caserne de pompiers.
     * @return la caserne de pompiers trouvée ou un Optional vide si aucune caserne
     *         ne correspond.
     */
    public Optional<FireStation> findFirestationByAddress(String address) {
        Optional<FireStation> fireStation = jsonData.getData().getFirestations().stream()
                .filter(f -> f.getAddress().equalsIgnoreCase(address))
                .findFirst();
        if (fireStation.isPresent()) {
            logger.info("Firestation trouvée par adresse : {}", address);
        } else {
            logger.info("Aucune Firestation trouvée par adresse : {}", address);
        }
        return fireStation;
    }

    /**
     * Recherche une caserne de pompiers par son adresse.
     *
     * @param stationNumber le numéro de la station de la caserne de pompiers
     * @return la caserne de pompiers trouvée ou un Optional vide si aucune caserne
     *         ne correspond.
     */
    public Optional<FireStation> findFirestationByStation(String stationNumber) {
        Optional<FireStation> fireStation = jsonData.getData().getFirestations().stream()
                .filter(f -> f.getStation().equalsIgnoreCase(stationNumber))
                .findFirst();
        if (fireStation.isPresent()) {
            logger.info("Firestation trouvée par numéro de station : {}", stationNumber);
        } else {
            logger.info("Aucune Firestation trouvée par numéro de station : {}", stationNumber);
        }
        return fireStation;
    }

}
