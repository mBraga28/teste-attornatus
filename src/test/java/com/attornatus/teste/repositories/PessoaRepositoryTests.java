package com.attornatus.teste.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.attornatus.teste.entities.Person;
import com.attornatus.teste.tests.Factory;

@DataJpaTest
class PersonRepositoryTests {

    @Autowired
    private PersonRepository repository;

    private long existingId;
    private long nonExistingId;
    private long countTotalPersons;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalPersons = 25L;
    }

    @Test
    void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {

        Optional<Person> result = repository.findById(existingId);

        assertTrue(result.isPresent());
    }

    @Test
    void findByIdShouldReturnIsEmptyOptionalWhenIdDoesNotExist() {

        Optional<Person> result = repository.findById(nonExistingId);

        assertTrue(result.isEmpty());
    }

    @Test
    void saveShouldPersistWithAutoincrementWhenIdIsNull() {

        Person person = Factory.createPerson();
        person.setId(null);

        person = repository.save(person);
        Optional<Person> result = repository.findById(person.getId());

        Assertions.assertNotNull(person.getId());
        Assertions.assertEquals(countTotalPersons + 1L, person.getId());
        assertTrue(result.isPresent());
        Assertions.assertSame(result.get(), person);

    }

    @Test
    void deleteShouldDeleteObjectWhenIdExists() {

        repository.deleteById(existingId);

        Optional<Person> result = repository.findById(existingId);
        assertFalse(result.isPresent());
    }

    @Test
    void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExists() {

        assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(nonExistingId);
        });
    }
}
