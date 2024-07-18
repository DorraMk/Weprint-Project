package com.michaelakamihe.ecommercebackend.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String USER = "USER";
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLE_ID")
	private Long roleId;

	//@Enumerated(EnumType.STRING)
	//@Column(length = 20)
	//private ERoles ename;
	private String name;
	
	
	public Role() {
		
	}
	


	/*public Role(ERoles name) {
		super();
		this.ename = name;
	}*/



	public Long getRoleId() {
		return roleId;
	}


	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	/*public ERoles getEName() {
		return ename;
	}



	public void setEName(ERoles name) {
		this.ename = name;
	}
*/


	public static String getUser() {
		return USER;
	}


	public static String getRoleUser() {
		return ROLE_USER;
	}


	public static String getRoleAdmin() {
		return ROLE_ADMIN;
	}


	
	

}
