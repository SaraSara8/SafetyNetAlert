package com.safetynetAlert.mapper;


import com.safetynetAlert.model.Person;
import org.springframework.stereotype.Component;

import com.safetynetAlert.dto.HouseholdChildDTO;

import java.util.function.Function;


@Component
public class HouseHoldChildDTOMapper implements Function<Person, HouseholdChildDTO> {

    @Override
    public HouseholdChildDTO apply(Person person) {
        return new HouseholdChildDTO(person.getFirstName(), person.getLastName());
    }
}