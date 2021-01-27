package controllers;

import java.io.IOException;
import java.util.Vector;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.User;

public class LoginController {
	private Stage stage;

	@FXML
    private JFXButton buttonLogin;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXPasswordField passwd;

    @FXML
    private Hyperlink labelNewAcc;

	@FXML
	void goToRegister(MouseEvent event) {
		try {
			/*
			
			AQUI HAY UN SWITCH PARA VERIFICAR EL LOGIN Y ELEGIR EL TIPO DE ROL
			
			
			
			*/
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../views/Register.fxml"));
			AnchorPane mypane = (AnchorPane) loader.load();
			Scene scene = new Scene(mypane);
			stage = new Stage();
			stage.setTitle("Register");
			stage.setScene(scene);
			stage.show();
			// cerramos la ventana del login
			Stage stage = (Stage) labelNewAcc.getScene().getWindow();
			stage.close();
			
		} catch (IOException e) {

		}
	}

	@FXML
	private void loginUser(MouseEvent event) {
		try {
			
			Vector<User> userList = rJson();
			
			if (login(userList) == true) {				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("../views/Users_GUI.fxml"));
				AnchorPane mypane = (AnchorPane) loader.load();
				Scene scene = new Scene(mypane);
				stage = new Stage();
				stage.setTitle("Register");
				stage.setScene(scene);
				stage.show();
				// cerramos la ventana del login
				Stage stage = (Stage) buttonLogin.getScene().getWindow();
				stage.close();
				// ((Node) (event.getSource())).getScene().getWindow().hide();
			}

		} catch (IOException e) {

		}
	}
	
	// carga la BBDD .json
	public Vector<User> rJson() {
		Json file = new Json(); 
		Vector<User> userList = new Vector<User>();
		Vector<User> readJson = new Vector<User>();
		readJson = file.deserializeJsonToArray();
		
		if(readJson != null) {
			userList = readJson;
		}
		return userList;
	}
	
	// comprueba los datos a compara dentro de la lista de usuarios
	public boolean login(Vector<User> userList) throws IOException {	
		Boolean account = false;
		for(User user : userList) {
			// equals: compara ....
			if(user.getEmail().equals(email.getText()) && user.getPasswd().equals(passwd.getText())) {
				account = true;
			}
		}
		if(account == false) {
			System.out.println("La cuenta no existe");
		}
		return account;	
	}
}
