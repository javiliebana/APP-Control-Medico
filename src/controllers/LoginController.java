package controllers;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import DDBB.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
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
			FXMLLoader loader_register = new FXMLLoader(getClass().getResource("../views/Register.fxml"));
			RegisterController register_controller = new RegisterController();
			loader_register.setController(register_controller);
			Parent root2 = loader_register.load();
			register_controller.cargarListaUsers();

			stage = new Stage();
			stage.setTitle("Ventana del paciente");
			stage.setScene(new Scene(root2));
			stage.show();
			// cerramos la ventana del login
			Stage stage = (Stage) buttonLogin.getScene().getWindow();
			stage.close();

		} catch (IOException e) {

		}
	}

	@FXML
	private void loginUser(MouseEvent event) {

		// login comprobamos los campos y luego verificamos con la base de datos
		try {
			if (textfielduser.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Introduce un valor para Usuario", "Atenci�n",
						JOptionPane.WARNING_MESSAGE);
			}

			else if (passwordfieldPW.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Introduce un valor para Contrase�a", "Atenci�n",
						JOptionPane.WARNING_MESSAGE);
			}
			User user_login = Database.userLogin(textfielduser.getText().trim(), passwordfieldPW.getText());

			// verificar por roles M=medico P=paciente F=familiar

			if (user_login == null) {

				JOptionPane.showMessageDialog(null, "El usuario no existe", "Atenci�n", JOptionPane.WARNING_MESSAGE);

			} else if (user_login.getRol().equals("M")) {

				FXMLLoader loader_medic = new FXMLLoader(getClass().getResource("../views/VentanaSupervisor.fxml"));
				MedicController contro_medic = new MedicController();
				loader_medic.setController(contro_medic);
				Parent root2 = loader_medic.load();
				contro_medic.cargarDatosPacientes(user_login);
				Scene medicscene = new Scene(root2);
				medicscene.getStylesheets().addAll(getClass().getResource("../css/userwindow.css").toExternalForm());
				stage = new Stage();
				stage.setTitle("Ventana del médico");
				stage.setScene(medicscene);
				stage.show();
				// cerramos la ventana del login
				Stage stage = (Stage) buttonLogin.getScene().getWindow();
				stage.close();

			} else if (user_login.getRol().equals("P")) {

				FXMLLoader loader_user = new FXMLLoader(getClass().getResource("../views/Users_GUI.fxml"));
				UsersController contro_luser = new UsersController();
				loader_user.setController(contro_luser);
				Parent root2 = loader_user.load();
				contro_luser.mostrarDatos(user_login);
				Scene userscene = new Scene(root2);
				userscene.getStylesheets().addAll(getClass().getResource("../css/userwindow.css").toExternalForm());
				stage = new Stage();
				stage.setTitle("Ventana del paciente");
				stage.setScene(userscene);
				stage.show();
				// cerramos la ventana del login
				Stage stage = (Stage) buttonLogin.getScene().getWindow();
				stage.close();

			} else if (user_login.getRol().equals("F")) {
				FXMLLoader loader_user = new FXMLLoader(getClass().getResource("../views/VentanaFamiliaUsuarios.fxml"));
				FamilyUserController contro_luser = new FamilyUserController();
				loader_user.setController(contro_luser);
				Parent root2 = loader_user.load();
				contro_luser.mostrarDatos(user_login);
				Scene familyscene = new Scene(root2);
				familyscene.getStylesheets().addAll(getClass().getResource("../css/userwindow.css").toExternalForm());
				stage = new Stage();
				stage.setTitle("Ventana del familiar");
				stage.setScene(familyscene);
				stage.show();
				// cerramos la ventana del login
				Stage stage = (Stage) buttonLogin.getScene().getWindow();
				stage.close();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
