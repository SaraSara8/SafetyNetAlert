package com.safetynetAlert.dto;

import java.util.Collections;
import java.util.List;

import com.safetynetAlert.model.MedicalRecord;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonMedicalRecordDTO {


	
	private String firstName;
	private String lastName;
	private String phone;
	private int age;
	private List<String> medications;
	private List<String> allergies;
	
	
	
	public PersonMedicalRecordDTO(String firstName, String lastName, String phone) {
		super();
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.age = 0;
		this.medications = Collections.singletonList("");
		this.allergies = Collections.singletonList("");
	}



}
