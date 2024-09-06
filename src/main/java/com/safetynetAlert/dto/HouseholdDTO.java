package com.safetynetAlert.dto;



import lombok.Getter;

import java.util.List;

@Getter
public class HouseholdDTO {

    private String address;
    private List<PersonMedicalRecordDTO> personsHousehold;

    public HouseholdDTO(String address, List<PersonMedicalRecordDTO> personsHousehold) {
        this.address = address;
        this.personsHousehold = personsHousehold;
    }


}
