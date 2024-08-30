package com.safetynetAlert.dto;

import java.util.List;

public class HouseholdChildDTO {


    private String firstName;
    private String lastName;
    private int age;
    private List<PersonDTO> familyPersons;


    public HouseholdChildDTO(String firstName, String lastName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = 0;
        this.familyPersons = null;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<PersonDTO> getFamilyPersons() {
        return familyPersons;
    }

    public void setFamilyPersons(List<PersonDTO> familyPersons) {
        this.familyPersons = familyPersons;
    }


}