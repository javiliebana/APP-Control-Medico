package models;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

public class Doctor {
	private int id;
	private String rol;
	private String email;
	private String passwd;
	private String name;
	private String surname;
	
	public Doctor(int id, String rol, String name,  String surname, String email, String passwd){
		this.id = id;
		this.rol = rol;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.passwd = passwd;		
	}
/*
	public Doctor(JFXTextField name2, JFXTextField surname2, JFXTextField email2, JFXPasswordField passwd2,
			JFXPasswordField apasswd) {
		// TODO Auto-generated constructor stub
	}
*/

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
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

	public String getName() {
		return name;
	}

	public static void setName(String name) {
		name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
