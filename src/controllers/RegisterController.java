package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelos.User;

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
	private JFXComboBox<String> comboBox;

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
				boolean alreadyexist = false;
				ArrayList<User> lista_usuarios = JsonUtils.desserializarJsonAArray();
				for (User user : lista_usuarios) {
					if (!alreadyexist) {
						if (!textFieldUser.getText().trim().equals(user.getUsername())) {

						} else {
							alreadyexist = true;
						}
					}

				}

				if (!alreadyexist) {

					String username = textFieldUser.getText().trim().toString();
					String password = textFieldPW.getText().trim().toString();
					String nombre = textFieldName.getText().toString();
					String apellidos = textFieldSurname.getText().toString();
					String rol = "F";
					String v_user = "";
					String chat="";
					for (User user : lista_usuarios) {
						String name = user.getNombre() + " " + user.getApellidos();
						if (name.equals(comboBox.getValue())) {
							v_user = user.getUsername();
						}
					}

					User u = new User(username, password, rol, nombre, apellidos, chat,v_user);
					lista_usuarios.add(u);
					JsonUtils.serializarArrayAJson(lista_usuarios);
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
				} else {
					JOptionPane.showMessageDialog(null, "Ese nombre de usuario ya existe", "Atención",
							JOptionPane.WARNING_MESSAGE);
				}

			}

		} catch (IOException e) {

		}
	}

	public void cargarListaUsers() {
		ArrayList<User> lista_usuarios = JsonUtils.desserializarJsonAArray();
		for (User user : lista_usuarios) {
			if (user.getRol().equals("U")) {
				String name = user.getNombre() + " " + user.getApellidos();
				comboBox.getItems().add(name);
			}

		}

	}

}
