package com.safetynetAlert.mapper;


import org.springframework.stereotype.Component;

import com.safetynetAlert.dto.HouseholdStationDTO;
import com.safetynetAlert.model.Person;

import java.util.function.Function;


@Component
public class HouseholdStationDTOMapper implements Function<Person, HouseholdStationDTO> {

    @Override
    public HouseholdStationDTO apply(Person person) {
        return new HouseholdStationDTO(person.getFirstName(), person.getLastName(), person.getPhone());
    }
}