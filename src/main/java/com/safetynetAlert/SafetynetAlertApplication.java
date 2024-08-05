package com.safetynetAlert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.safetynetAlert.model.JsonDataBase;
import com.safetynetAlert.model.Person;



@SpringBootApplication
public class SafetynetAlertApplication {

	final static Logger logger = LogManager.getLogger(SafetynetAlertApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SafetynetAlertApplication.class, args);


	}

	// mise en place du chargement des données du fichier json dans  l'objet JsonDataBase 
	@Bean
	CommandLineRunner runner() {
		return args -> {  
			// etape 1 chargement du fichier
			Path path = Paths.get("src/main/resources/DataBase.json");
			byte[] fileBites = Files.readAllBytes(path);
			
			// etape 2 etiration avec JsonIter
			JsonIterator iterator = JsonIterator.parse(fileBites);
			Any any = iterator.readAny();

			// chargement des persons du  fichier dans l'objet JsonDataBase
			Any personToRead = any.get("persons");
			List<Person> personsFromDataBase  = initializePersons(personToRead);
			logger.info("nombre de personnes chargées depuis le fichier", personsFromDataBase.size());

		};

	}

	private List<Person> initializePersons (Any anyPerson){

		// itereration et remplissage des persons

		List<Person> persons = new ArrayList();
		anyPerson.forEach(p ->{

			Person person = new Person();

			person.setFirstName(p.get("firstName").toString());
			person.setLastName(p.get("lastName").toString());
			person.setCity(p.get("city").toString());
			person.setPhone(p.get("phone").toString());
			person.setZip(p.get("zip").toString());
			person.setEmail(p.get("email").toString());

			persons.add(person);

		});

		return persons;

	}

}
