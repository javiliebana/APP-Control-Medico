package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import DDBB.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelos.Chat;
import modelos.Familiar;
import modelos.Paciente;
import modelos.User;

public class RegisterController {
	private Stage stage;
	HashMap<String, User> mapa_pacientes;

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

			FXMLLoader loader_login = new FXMLLoader(getClass().getResource("../views/Login.fxml"));
			LoginController logincontroller = new LoginController();
			loader_login.setController(logincontroller);
			Parent root2 = loader_login.load();
			Scene loginscene = new Scene(root2);
			loginscene.getStylesheets().addAll(getClass().getResource("../css/loginregister.css").toExternalForm());
			stage = new Stage();
			stage.setTitle("Login MEDICAPP");
			stage.setScene(loginscene);
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
				JOptionPane.showMessageDialog(null, "Introduce un valor para Usuario", "Atenci�n",
						JOptionPane.WARNING_MESSAGE);
			} else if (textFieldName.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Introduce un valor para Nombre", "Atenci�n",
						JOptionPane.WARNING_MESSAGE);
			} else if (textFieldSurname.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Introduce un valor para Apellidos", "Atenci�n",
						JOptionPane.WARNING_MESSAGE);
			} else if (textFieldPW.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Introduce un valor para Contrase�a", "Atenci�n",
						JOptionPane.WARNING_MESSAGE);
			} else if (textFieldRPW.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Introduce un valor para Repetir Contrase�a", "Atenci�n",
						JOptionPane.WARNING_MESSAGE);
			} else if (!textFieldRPW.getText().equals(textFieldPW.getText())) {
				JOptionPane.showMessageDialog(null, "Las contrae�as deben coincidir", "Atenci�n",
						JOptionPane.WARNING_MESSAGE);
			}else if(comboBox.getValue()==null) {
				JOptionPane.showMessageDialog(null, "Debe seleccionar un paciente", "Atenci�n",
						JOptionPane.WARNING_MESSAGE);
			}
			else {
				boolean alreadyexist = false;
//				ArrayList<User> lista_usuarios = JsonUtils.desserializarJsonAArray();
//				for (User user : lista_usuarios) {
//					if (!alreadyexist) {
//						if (!textFieldUser.getText().trim().equals(user.getUsername())) {
//
//						} else {
//							alreadyexist = true;
//						}
//					}
//				}

				if (!alreadyexist) {

					String username = textFieldUser.getText().trim().toString();
					String password = textFieldPW.getText().trim().toString();
					String nombre = textFieldName.getText().toString();
					String apellidos = textFieldSurname.getText().toString();
					String rol = "F";
					String v_user = "";
					String chat="";
//					for (User user : lista_usuarios) {
//						String name = user.getNombre() + " " + user.getApellidos();
//						if (name.equals(comboBox.getValue().toString())) {
//							v_user = user.getUsername();
//						}
//					}
					
					
					User new_user = new User(0, username, password, nombre, apellidos, "", "", rol);
					Database.insertNewUser(new_user);
					//recogemos el id que se ha generado
					int generatedID = Database.getUserIDFromUsername(username);
					//recogemos el id del paciente al que va a supervisar
					User user_paciente = mapa_pacientes.get(comboBox.getValue().toString());
					Paciente p = Database.getPacienteFromIDUsuario(user_paciente.getId());
					//creamos su usuario familiar y el chat con el medico
					Familiar new_familiar = new Familiar(0, generatedID, p.getId_paciente());
					int user_medic_id=Database.getUserIDFromMedicID(p.getId_medico());
					Chat new_chat_familiar_medico =new Chat(0, user_medic_id, generatedID);
					Database.insertNewFamiliar(new_familiar);
					Database.insertNewChat(new_chat_familiar_medico);
					
					FXMLLoader loader_login = new FXMLLoader(getClass().getResource("../views/Login.fxml"));
					LoginController logincontroller = new LoginController();
					loader_login.setController(logincontroller);
					Parent root2 = loader_login.load();
					Scene loginscene = new Scene(root2);
					loginscene.getStylesheets().addAll(getClass().getResource("../css/loginregister.css").toExternalForm());
					stage = new Stage();
					stage.setTitle("Login MEDICAPP");
					stage.setScene(loginscene);
					stage.show();
					// cerramos la ventana del login
					Stage stage = (Stage) cancelRegisterButton.getScene().getWindow();
					stage.close();
					
				} else {
					JOptionPane.showMessageDialog(null, "Ese nombre de usuario ya existe", "Atenci�n",
							JOptionPane.WARNING_MESSAGE);
				}

			}

		} catch (IOException e) {

		}
	}

	public void cargarListaUsers() {
		ArrayList<User> lista_users_pacientes = Database.getListaUsuariosPacientes();
		HashMap<String, User> mapa_pacientes = new HashMap<String, User>();
		for (User user : lista_users_pacientes) {
				String name = user.getNombre() + " " + user.getApellidos();
				comboBox.getItems().add(name);
				mapa_pacientes.put(name, user);
		}
		this.mapa_pacientes=mapa_pacientes;
		

	}

}
