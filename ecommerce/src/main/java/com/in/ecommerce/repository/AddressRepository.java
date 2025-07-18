package com.in.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in.ecommerce.entity.Address;
import com.in.ecommerce.entity.User;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long >{
	
	 List<Address> findByUser(User user);

}
