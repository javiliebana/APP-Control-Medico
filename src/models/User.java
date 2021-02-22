package models;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import controllers.Json;

public class User {
	private String id;
	private String rol;
	private String name;
	private String surname;
	private String email;
	private String passwd;
	private String apasswd;
	private String[] family;
	private String position;
	private List<User> todos;  
	
	
	public User() {
		
	}
	public User(String id, String rol,  String name,  String surname,  String email,  String passwd){
		this.id = id;
		this.rol = rol;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.passwd = passwd;
	}
	/*
	public User(int id, String rol, String name, String surname, String email, String passwd, String family[]){
		this.id = id;
		this.rol = rol;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.passwd = passwd;
		this.family = family;	
	}
	*/
	

	
	public String toString() {
		String print = "";
		return print;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String[] getFamily() {
		return family;
	}

	public void setFamily(String[] family) {
		this.family = family;
	}
	
	public void setPosition(String p) {
		position = p;
	}
	
	 public void setTodos(List<User> todos) {
		  this.todos = todos;
	}
	 
	 
	public void addDataToJson(ArrayList<User> usersList) {
		if(usersList.size() >= 1) {
			position = usersList.get(usersList.size() -1).getId();
		}
	}
}

