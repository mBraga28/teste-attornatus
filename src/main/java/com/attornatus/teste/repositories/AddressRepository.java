package com.attornatus.teste.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attornatus.teste.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
