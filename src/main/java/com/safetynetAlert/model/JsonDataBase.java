package com.safetynetAlert.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.HTMLDocument.Iterator;

import com.jsoniter.JsonIterator;
import com.jsoniter.ValueType;

/*
 * Cette classe permet de representer les données du fichier Json
 */


public class JsonDataBase {
	
	private List<Person> persons;
	private List<FireStation> firestations;
	private List<MedicalRecord> medicalRecords;
	
	
	public JsonDataBase() {
		
		persons = new ArrayList<Person>();
	}
	
	public void perseDataBase(String input) throws Exception {
	        
	    
		 String firstName= null;
		String lastName = null;
		String address= null;
		String city= null;
		 String zip= null;
		 String phone= null;
		 String email= null;
		
		
	    JsonIterator iterator = JsonIterator.parse(input);

	    
	    
	    for (String field = iterator.readObject(); field != null; field = iterator.readObject()) {
	    	
	    	
	    	if (field.equals("persons")) {
	           
	         
	        	
	        	
	        	while (iterator.readArray()) {
	        		
	        		
                    for (String field2 = iterator.readObject(); field2 != null; field2 = iterator.readObject()) {
                    	switch (field2) {
	        
                    	case "firstName":
                    		if (iterator.whatIsNext() == ValueType.STRING) {
                    			//person.setFirstName(iterator.readString());
                    			firstName = iterator.readString();
                    			
                    		}
                    		continue;
                    	case "lastName":
                    		if (iterator.whatIsNext() == ValueType.STRING) {
                    			//person.setLastName(iterator.readString());
                    			lastName = iterator.readString();
                    		}
                    		continue;
                    	case "address":
                    		if (iterator.whatIsNext() == ValueType.STRING) {
                    			//person.setFirstName(iterator.readString());
                    			address = iterator.readString();
                    		}
                    		continue;
                    	case "city":
                    		if (iterator.whatIsNext() == ValueType.STRING) {
                    			//person.setLastName(iterator.readString());
                    			city = iterator.readString();
                    		}
                    		continue;
                    	case "zip":
                    		if (iterator.whatIsNext() == ValueType.STRING) {
                    			//person.setFirstName(iterator.readString());
                    			zip = iterator.readString();
                    		}
                    		continue;
                    	case "phone":
                    		if (iterator.whatIsNext() == ValueType.STRING) {
                    			//person.setLastName(iterator.readString());
                    			phone = iterator.readString();
                    		}
                    		continue;   
                    	case "email":
                    		if (iterator.whatIsNext() == ValueType.STRING) {
                    			//person.setLastName(iterator.readString());
                    			email = iterator.readString();
                    		}
                    		continue;   
                    	//default:
                    		//iterator.skip();
                    	}
                    }
                    
                    Person person = new Person(firstName, lastName, address, city,zip, phone, email);
                    persons.add(person);
                    //System.out.println(person.getFirstName());
	        	}
	        //default:
	        //	continue;
	        	//System.out.println("eeeeeeeeee     " + field);
        		//iterator.skip();
	        } else { break;}
	    }
	    
	    
	  //test d'afficahge
		
	    
	    
	    
	    for (Person perso : persons) {
			
			System.out.println(perso.getFirstName());
			System.out.println(perso.getZip());
			System.out.println(perso.getEmail());
			System.out.println(perso.getPhone());
		}

	}
	
	
	
	
	
	
	
	
	

}
