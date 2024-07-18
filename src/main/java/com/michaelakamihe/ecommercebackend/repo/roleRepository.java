package com.michaelakamihe.ecommercebackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.michaelakamihe.ecommercebackend.model.ERoles;
import com.michaelakamihe.ecommercebackend.model.Role;

public interface roleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);
	//Role findByEName(ERoles ename);
}
