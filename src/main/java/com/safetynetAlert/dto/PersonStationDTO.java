package com.safetynetAlert.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonStationDTO {
	
	private String station;
	private List<PersonMedicalRecordDTO> personMedicalRecordDTOs;

}
