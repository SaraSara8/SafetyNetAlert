package com.safetynetAlert.mapper;



import org.springframework.stereotype.Component;

import com.safetynetAlert.dto.PersonDTO;
import com.safetynetAlert.model.Person;

import java.util.function.Function;


@Component
public class PersonDTOMapper implements Function<Person, PersonDTO> {
    @Override
    public PersonDTO apply(Person person) {
        return new PersonDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone());
    }
}