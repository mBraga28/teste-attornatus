package com.attornatus.teste.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attornatus.teste.entities.Address;
import com.attornatus.teste.repositories.AddressRepository;
import com.attornatus.teste.dto.AddressDTO;
import com.attornatus.teste.dto.PersonDTO;
import com.attornatus.teste.entities.Person;
import com.attornatus.teste.repositories.PersonRepository;
import com.attornatus.teste.services.exceptions.DatabaseException;
import com.attornatus.teste.services.exceptions.ResourceNotFoundException;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository repository;
	
	@Autowired
	private AddressRepository addressRepository;

	@Transactional(readOnly = true)
	public Page<PersonDTO> findAllPaged(Long addressId, String name, Pageable pageable) {
		List<Address> address = (addressId == 0) ? null : Arrays.asList(addressRepository.getReferenceById(addressId));
	    Page<Person> page = repository.find(address, name, pageable);
	    repository.findPersonsWithAddresses(page.getContent());
	 	return page.map(x -> new PersonDTO(x, x.getAddresses()));
	}


	@Transactional(readOnly = true)
	public PersonDTO findById(Long id) {
		Optional<Person> obj = repository.findById(id);
		Person entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new PersonDTO(entity, entity.getAddresses());
	}

	@Transactional
	public PersonDTO insert(PersonDTO dto) {
		Person entity = new Person();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new PersonDTO(entity);
	}

	@Transactional
	public PersonDTO update(Long id, PersonDTO dto) {
		try {
		Person entity = repository.getReferenceById(id);
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new PersonDTO(entity); 
		} 
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} 
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	private void copyDtoToEntity(PersonDTO dto, Person entity) {

		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setBirthDate(dto.getBirthDate());
		
		entity.getAddresses().clear();
		for(AddressDTO adsDto : dto.getAddresses()) {
			Address address = addressRepository.getReferenceById(adsDto.getId());
			entity.getAddresses().add(address);
			
		}
	}

	
}
