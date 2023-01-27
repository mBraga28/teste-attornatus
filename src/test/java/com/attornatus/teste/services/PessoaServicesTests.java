package com.attornatus.teste.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.attornatus.teste.dto.PersonDTO;
import com.attornatus.teste.entities.Address;
import com.attornatus.teste.entities.Person;
import com.attornatus.teste.repositories.AddressRepository;
import com.attornatus.teste.repositories.PersonRepository;
import com.attornatus.teste.services.exceptions.DatabaseException;
import com.attornatus.teste.services.exceptions.ResourceNotFoundException;
import com.attornatus.teste.tests.Factory;

@ExtendWith(SpringExtension.class)
class PersonpersonServicesTests {

    @InjectMocks
    private PersonService service;

    @Mock
    private PersonRepository repository;

    @Mock
    private AddressRepository addressRepository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private Address address;
    private Person person;
    PersonDTO personDto;
    private PageImpl<Person> page;


    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;
        person = Factory.createPerson();
        address = Factory.createAddress();
        personDto = Factory.createPersonDTO();
        page = new PageImpl<>(List.of(person));

        Mockito.when(repository.findAll((Pageable) any())).thenReturn(page);

        Mockito.when(repository.save(any())).thenReturn(person);

        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(person));
        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        Mockito.when(repository.find(any(), any(), any())).thenReturn(page);

        Mockito.when(repository.getReferenceById(existingId)).thenReturn(person);
        Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        Mockito.when(addressRepository.getReferenceById(existingId)).thenReturn(address);
        Mockito.when(addressRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
    }

    @Test
    void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, personDto);
        });
    }

    @Test
    void updateShouldReturnPersonpersonDTOWhenIdExists() {
        PersonDTO result = service.update(existingId, personDto);
        Assertions.assertNotNull(result);
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });
    }

    @Test
    void findByIdShouldReturnPersonpersonDTOWhenIdExists() {

        PersonDTO result = service.findById(existingId);

        Assertions.assertNotNull(result);
    }

    @Test
    void findAllPagedShouldReturnPage() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<PersonDTO> result = service.findAllPaged(0L, "", pageable);

        Assertions.assertNotNull(result);
    }

    @Test
    void deleteShouldThrowDatabaseExceptionWhenDependentId() {

        Assertions.assertThrows(DatabaseException.class, () -> {
            service.delete(dependentId);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);
    }

    @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);
    }

    @Test
    void deleteShouldDoNothingWhenIdExists() {

        assertDoesNotThrow(() -> {
            service.delete(existingId);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
    }
}
