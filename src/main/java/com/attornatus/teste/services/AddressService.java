package com.attornatus.teste.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attornatus.teste.repositories.AddressRepository;
import com.attornatus.teste.dto.AddressDTO;
import com.attornatus.teste.entities.Address;
import com.attornatus.teste.services.exceptions.ResourceNotFoundException;

@Service
public class AddressService {
	
	@Autowired
	private AddressRepository repository;

	@Transactional(readOnly = true)
	public Page<AddressDTO> findAllPaged(Pageable pageable) {
		Page<Address> list = repository.findAll(pageable);
		return list.map(x -> new AddressDTO(x));
	}

	@Transactional(readOnly = true)
	public AddressDTO findById(Long id) {
		Optional<Address> obj = repository.findById(id);
		Address entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new AddressDTO(entity);
	}

	@Transactional
	public AddressDTO insert(AddressDTO dto) {
		Address entity = new Address();
		entity.setArea(dto.getArea());
		entity.setCep(dto.getCep());
		entity.setNumber(dto.getNumber());
		entity.setCity(dto.getCity());
		entity = repository.save(entity);
		return new AddressDTO(entity);
	}
}
