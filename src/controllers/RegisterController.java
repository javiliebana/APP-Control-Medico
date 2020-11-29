package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegisterController {
	private Stage stage;
	@FXML
	public Button registerButton;
	@FXML
	public Button cancelRegisterButton;

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
			// cerramos la ventana del login
			Stage stage = (Stage) registerButton.getScene().getWindow();
			stage.close();
			
		} catch (IOException e) {

		}
	}
}
