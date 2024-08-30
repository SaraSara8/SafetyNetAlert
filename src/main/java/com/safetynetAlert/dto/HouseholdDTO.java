package com.safetynetAlert.dto;



import lombok.Getter;

import java.util.List;

@Getter
public class HouseholdDTO {

    private String address;
    private List<HouseholdStationDTO> personsHousehold;

    public HouseholdDTO(String address, List<HouseholdStationDTO> personsHousehold) {
        this.address = address;
        this.personsHousehold = personsHousehold;
    }


}
