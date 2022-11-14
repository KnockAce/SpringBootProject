package com.rioc.ws.repositories;

import com.rioc.ws.models.dao.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAddressRepository extends JpaRepository<Address, Integer> {

}
