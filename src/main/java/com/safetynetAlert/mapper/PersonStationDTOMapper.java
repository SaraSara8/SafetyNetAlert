package com.safetynetAlert.mapper;


import org.springframework.stereotype.Component;

import com.safetynetAlert.dto.PersonStationDTO;
import com.safetynetAlert.model.Person;

import java.util.function.Function;


@Component
public class PersonStationDTOMapper implements Function<Person, PersonStationDTO> {

    @Override
    public PersonStationDTO apply(Person person) {
        return new PersonStationDTO(person.getFirstName(), person.getLastName(), person.getPhone());
    }
}