package com.itrade.modelo;

import java.io.Serializable;

public class Contact implements Serializable {
	private String id_contact;
	private String name;
	private String number;
	private String description;
	private String email;
	
	public Contact(){
		
	}
	public Contact(String id_contact, String name, String number,
			String description, String email) {
		super();
		this.id_contact = id_contact;
		this.name = name;
		this.number = number;
		this.description = description;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId_contact() {
		return id_contact;
	}
	public void setId_contact(String id_contact) {
		this.id_contact = id_contact;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub			
		return name+" "+number+" "+description+"\n"+email;
	}
	 
	
}
