package com.safetynetAlert.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
@Getter
@Setter
public class HouseholdStationDTO {


    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private List<String> medications;
    private List<String> allergies;
    

    public HouseholdStationDTO(String firstName, String lastName,  String phone) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.age = 0;
        this.medications = Collections.singletonList("");
        this.allergies = Collections.singletonList("");
    }

}