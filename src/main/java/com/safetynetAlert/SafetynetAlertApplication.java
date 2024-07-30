package com.safetynetAlert;

import java.io.BufferedReader;
import java.io.FileReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.safetynetAlert.model.JsonDataBase;

@SpringBootApplication
public class SafetynetAlertApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafetynetAlertApplication.class, args);
		
		
		JsonDataBase parseTest = new JsonDataBase();
		
		
		
		
		String input ="";
		
		try {
			
			BufferedReader bufferedreader= 
					new BufferedReader (new FileReader("/Users/sarahaimeur/Desktop/formations/GIT/Projet_5/safetynetAlert/src/main/resources/DataBase.json"));
			
			String line;
			
			while ((line = bufferedreader.readLine()) != null) {
				
				input += line;
				
				
			}	
			
			bufferedreader.close();
			
			
			
			
				
			parseTest.perseDataBase(input);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
