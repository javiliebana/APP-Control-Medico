package controllers;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegisterController {
	private Stage stage;

	@FXML
	private JFXTextField textFieldUser;

	@FXML
	private JFXTextField textFieldName;

	@FXML
	private JFXTextField textFieldSurname;

	@FXML
	private JFXPasswordField textFieldPW;

	@FXML
	private JFXPasswordField textFieldRPW;

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
			if (textFieldUser.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Introduce un valor para Usuario", "Atención",
						JOptionPane.WARNING_MESSAGE);
			} else if (textFieldName.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Introduce un valor para Nombre", "Atención",
						JOptionPane.WARNING_MESSAGE);
			} else if (textFieldSurname.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Introduce un valor para Apellidos", "Atención",
						JOptionPane.WARNING_MESSAGE);
			} else if (textFieldPW.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Introduce un valor para Contraseña", "Atención",
						JOptionPane.WARNING_MESSAGE);
			} else if (textFieldRPW.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Introduce un valor para Repetir Contraseña", "Atención",
						JOptionPane.WARNING_MESSAGE);
			} else if (!textFieldRPW.getText().equals(textFieldPW.getText())) {
				JOptionPane.showMessageDialog(null, "Las contraeñas deben coincidir", "Atención",
						JOptionPane.WARNING_MESSAGE);
			} else {

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
			}

		} catch (IOException e) {

		}
	}
}
