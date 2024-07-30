package com.safetynetAlert.model;

import java.util.List;

/*
 * Cette classe permet de representer l'objet firestation du fichier Json
 */

public class FireStation {
	
	
	private String address;
	private String stationNumber;
	
	// c'est pour gerer le lien entre FireStation et Person (list des personnes desservies par station donnée)
	private List<Person> personsByStation;

}
