package com.safetynetAlert.model;

/*
 * Cette classe permet de representer l'objet firestation du fichier Json
/**
 * This class represents the FireStation object from the Json file.
 * A FireStation is associated with an address and a station number.
 * 
 * @author  Your Name
 * @version 1.0
 */
public class FireStation {


	private String address;
	private String station;


    /**
     * Default constructor.
     * This is mandatory for Java and necessary for unit tests.
     */
	public FireStation() {
	}

    /**
     * Constructor for the FireStation class.
     *
     * @param address the address of the FireStation
     * @param station the station number of the FireStation
     */
	public FireStation(String address, String station) {
		this.address = address;
		this.station = station;
	}

    /**
     * Method to get the address of the FireStation.
     * 
     * @return the address of the FireStation
     */
	public String getAddress() {
		return address;
	}

    /**
     * Method to set the address of the FireStation.
     * 
     * @param address the new address of the FireStation
     */
	public void setAddress(String address) {
		this.address = address;
	}

    /**
     * Method to get the station number of the FireStation.
     * 
     * @return the station number of the FireStation
     */
	public String getStation() {
		return station;
	}


    /**
     * Method to set the station number of the FireStation.
     * 
     * @param station the new station number of the FireStation
     */
	public void setStation(String station) {
		this.station = station;
	}



}


