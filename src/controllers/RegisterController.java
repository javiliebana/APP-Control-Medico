package controllers;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import controllers.Json;
import models.Doctor;
import models.Pacient;
import models.User;


public class RegisterController {
	private Stage stage;
	
	private String id;
	
	private String rol;
	
	@FXML
    private JFXTextField name;

    @FXML
    private JFXTextField surname;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXPasswordField passwd;

    @FXML
    private JFXPasswordField apasswd;

    @FXML
    private JFXButton registerButton;

    @FXML
    private JFXButton cancelRegisterButton;

	@FXML
	void cancelRegister(MouseEvent event) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../views/Login.fxml"));
			AnchorPane mypane = (AnchorPane) loader.load();
			Scene scene = new Scene(mypane);
			stage = new Stage();
			stage.setTitle("Register");
			stage.setScene(scene);
			stage.show();
			// cerramos la ventana del login
			Stage stage = (Stage) cancelRegisterButton.getScene().getWindow();
			stage.close();
			
		} catch (IOException e) {

		}
	}

	@FXML
	void doRegister(MouseEvent event) {
		try {	

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../views/Login.fxml"));
			AnchorPane mypane = (AnchorPane) loader.load();
			Scene scene = new Scene(mypane);
			stage = new Stage();
			stage.setTitle("Register");
			stage.setScene(scene);
			stage.show();	
			// leer y escribir en BBDD Json
			rwJson();
			// cerramos la ventana del login
			Stage stage = (Stage) registerButton.getScene().getWindow();
			stage.close();	
			
		} catch (IOException e) {

		}
	}
	
	public void rwJson() {
		
		// cargala BBDD .json
		Json file = new Json(); 
		Vector<User> userList = new Vector<User>();
		Vector<User> readJson = new Vector<User>();
		readJson = file.deserializeJsonToArray();
		
		if(readJson != null) {
			userList = readJson;
		}
		User user = new User(id, rol, name.getText(), surname.getText(), email.getText(), passwd.getText());
		userList.add(user);
		
		// guarda los datos			
		Json.serializeToJson(userList);
		
	}	
	
/*	
	// Convertir Vector a Array
	public ArrayList<User> chargeJson() {
		Json readJson = new Json();
		Vector<User> jsonUsersList = new Vector<User>();
		jsonUsersList = readJson.deserializeJsonToArray();
		
		ArrayList<User> arrayUsersList = new ArrayList<User>(jsonUsersList);
			
		return arrayUsersList;
	}
*/
	void serializeToJson(){
		if (name.getText() == "Dr") {
			rol = "doctor";
			Doctor doctor = new Doctor(0, rol, name.getText(), surname.getText(), email.getText(), passwd.getText());
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			try(FileWriter writer = new FileWriter("usuarios.json")){
				gson.toJson(doctor, writer);
				
			}catch(IOException e) {
				e.printStackTrace();
			}
		}else {
			rol = "pacient";
			Pacient pacient = new Pacient(0, rol, name.getText(), surname.getText(), email.getText(), passwd.getText());
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			try(FileWriter writer = new FileWriter("usuarios.json")){
				gson.toJson(pacient, writer);
				
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
