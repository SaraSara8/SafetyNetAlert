package com.safetynetAlert.mapper;


import org.springframework.stereotype.Component;

import com.safetynetAlert.dto.PersonMedicalRecordDTO;
import com.safetynetAlert.model.Person;

import java.util.function.Function;


@Component
public class PersonStationDTOMapper implements Function<Person, PersonMedicalRecordDTO> {

    @Override
    public PersonMedicalRecordDTO apply(Person person) {
        return new PersonMedicalRecordDTO(person.getFirstName(), person.getLastName(), person.getPhone());
    }
}