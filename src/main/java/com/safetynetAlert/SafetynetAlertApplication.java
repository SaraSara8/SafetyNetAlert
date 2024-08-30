package com.safetynetAlert;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.safetynetAlert.model.JsonDataBase;
import com.safetynetAlert.model.Person;
import com.safetynetAlert.model.Data;




@SpringBootApplication
public class SafetynetAlertApplication {


	final static Logger logger = LogManager.getLogger(SafetynetAlertApplication.class);

	@Autowired
	JsonDataBase jsonData;

	public static void main(String[] args) {
		SpringApplication.run(SafetynetAlertApplication.class, args);
	}



	// mise en place du chargement des données du fichier json dans  l'objet JsonDataBase
	@Bean
	CommandLineRunner runner() {
		return args -> {


			Data data = jsonData.getData();
			logger.info("nombre de personnes chargées depuis le fichier",data.getPersons().size());






		};

	}





}
