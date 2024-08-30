package com.safetynetAlert.mapper;


import com.safetynetAlert.dto.PersonDTO;
import org.springframework.stereotype.Component;

import com.safetynetAlert.dto.PersonInfoDTO;
import com.safetynetAlert.model.Person;

import java.util.function.Function;


@Component
public class PersonInfoDTOMapper implements Function<Person, PersonInfoDTO> {

    @Override
    public PersonInfoDTO apply(Person person) {
        return new PersonInfoDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getEmail());
    }
}