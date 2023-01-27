package com.attornatus.teste.dto;

import java.io.Serializable;
import com.attornatus.teste.entities.Address;

public class AddressDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String area;
	private String cep;
	private Integer number;
	private String city;

	public AddressDTO() {
	}

	public AddressDTO(Long id, String area, String cep, Integer number, String city) {
		this.id = id;
		this.area = area;
		this.cep = cep;
		this.number = number;
		this.city = city;
	}

	public AddressDTO(Address entity) {
		this.id = entity.getId();
		this.area = entity.getArea();
		this.cep = entity.getCep();
		this.number = entity.getNumber();
		this.city = entity.getCity();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getArea() {
		return area;
	}

	public String getCep() {
		return cep;
	}

	public Integer getNumber() {
		return number;
	}

	public String getCity() {
		return city;
	}

}
