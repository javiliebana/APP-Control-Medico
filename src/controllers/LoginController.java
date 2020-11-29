package controllers;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {
	private Stage stage;

	@FXML
	private JFXButton buttonLogin;
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

		} catch (IOException e) {

		}
	}
}
