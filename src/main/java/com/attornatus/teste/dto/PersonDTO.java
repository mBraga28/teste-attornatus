package com.attornatus.teste.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.attornatus.teste.entities.Address;
import com.attornatus.teste.entities.Person;

public class PersonDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotBlank(message = "Campo requerido")
	private String name;
	private LocalDate birthDate;
	
	
	private List<AddressDTO> addresses = new ArrayList<>();
	
	public PersonDTO() {
	}

	public PersonDTO(Long id, @NotBlank(message = "Campo requerido") String name,
			@NotBlank(message = "Campo requerido") LocalDate birthDate) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}

	public PersonDTO(Person entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.birthDate = entity.getBirthDate();
	}

	public PersonDTO(Person entity, Set<Address> addresses) {
		this(entity);
		addresses.forEach(ads -> this.addresses.add(new AddressDTO(ads)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public List<AddressDTO> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressDTO> address) {
		this.addresses = address;
	}
}

