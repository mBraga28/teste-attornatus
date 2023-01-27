package com.attornatus.teste.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.attornatus.teste.entities.Address;
import com.attornatus.teste.entities.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

	@Query("SELECT DISTINCT obj FROM Person obj INNER JOIN obj.addresses ads WHERE "
			+ "(COALESCE(:addresses) IS NULL OR ads IN :addresses) AND "
			+ "(:name = '' OR LOWER(obj.name) LIKE LOWER(CONCAT('%',:name,'%')))")
	Page<Person> find(List<Address> addresses, String name, Pageable pageable);
	
	@Query("SELECT obj FROM Person obj JOIN FETCH obj.addresses WHERE obj IN :persons")
	List<Person> findPersonsWithAddresses(List<Person> persons);
}
