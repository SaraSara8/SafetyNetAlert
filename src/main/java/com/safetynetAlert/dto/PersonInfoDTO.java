package com.safetynetAlert.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class PersonInfoDTO {


    private String firstName;
    private String lastName;
    private String address;
    private int age;
    private String email;
    private List<String> medications;
    private List<String> allergies;


    public PersonInfoDTO(String firstName, String lastName, String address, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.age = 0;
        this. email = email;
        this.medications = Collections.singletonList("");
        this.allergies = Collections.singletonList("");
    }




}

