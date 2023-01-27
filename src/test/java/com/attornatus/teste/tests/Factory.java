package com.attornatus.teste.tests;

import java.time.LocalDate;

import com.attornatus.teste.dto.PersonDTO;
import com.attornatus.teste.entities.Address;
import com.attornatus.teste.entities.Person;

public class Factory {

	public static Person createPerson() {
		Person person = new Person(1L, "João Silva", LocalDate.parse("1993-04-22"));
		person.getAddresses().add(createAddress());
		
		return person;
	}
	
	public static PersonDTO createPersonDTO() {
		Person person = createPerson();
		return new PersonDTO(person, person.getAddresses());
	}
	
	public static Address createAddress() {
		return new Address(1L, "Avenida ...", "00000000", 35, "Recife"); 
	}
}
