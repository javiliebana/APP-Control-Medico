package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelos.User;

public class LoginController {
	private Stage stage;

	@FXML
	private JFXButton buttonLogin;

	@FXML
	private JFXTextField textfielduser;

	@FXML
	private JFXPasswordField passwordfieldPW;

	@FXML
	private Hyperlink labelNewAcc;

	@FXML
	void goToRegister(MouseEvent event) {
		try {
			/*
			 * 
			 * AQUI HAY UN SWITCH PARA VERIFICAR EL LOGIN Y ELEGIR EL TIPO DE ROL
			 * 
			 * 
			 * 
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
			if (textfielduser.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Introduce un valor para Usuario", "Atención",
						JOptionPane.WARNING_MESSAGE);
						
			}

			else if (passwordfieldPW.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Introduce un valor para Contraseña", "Atención",
						JOptionPane.WARNING_MESSAGE);
			}
			ArrayList<User> lista_usuarios = JsonUtils.desserializarJsonAArray();
			for (User user : lista_usuarios) {
				if (textfielduser.getText().equals(user.getUsername())
						&& passwordfieldPW.getText().equals(user.getPassword())) {
					
					
					
					if (user.getRol().equals("M")) {
						
						FXMLLoader loader_medic = new FXMLLoader(getClass().getResource("../views/VentanaSupervisor.fxml"));
						MedicController contro_medic =  new MedicController();
						loader_medic.setController(contro_medic);
						Parent root2 = loader_medic.load();
						contro_medic.cargarDatosPacientes(user,lista_usuarios);
						
						stage = new Stage();
						stage.setTitle("User");
						stage.setScene(new Scene(root2));
						stage.show();
						// cerramos la ventana del login
						Stage stage = (Stage) buttonLogin.getScene().getWindow();
						stage.close();
						
					} else {
						
						FXMLLoader loader_user = new FXMLLoader(getClass().getResource("../views/Users_GUI.fxml"));
						UsersController contro_luser =  new UsersController();
						loader_user.setController(contro_luser);
						Parent root2 = loader_user.load();
						contro_luser.mostrarDatos(user);
						
						stage = new Stage();
						stage.setTitle("User");
						stage.setScene(new Scene(root2));
						stage.show();
						// cerramos la ventana del login
						Stage stage = (Stage) buttonLogin.getScene().getWindow();
						stage.close();

					}
					
					
					
					

				}
			}
	
	}catch(IOException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}

}
