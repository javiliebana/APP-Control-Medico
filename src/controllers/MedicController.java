package controllers;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXListView;

import DDBB.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelos.User;

public class MedicController {
	User medico_logado;
	User paciente_seleccionado;
	ArrayList<User> lista_usuarios;

	@FXML
	private Label lblName;

	@FXML
	private JFXListView<String> lvPacientes;

	@FXML
	void handleMouseClick(MouseEvent event) {
		String list_value = lvPacientes.getSelectionModel().getSelectedItem();
		String[] valores_user = list_value.split(" ");
		String username = valores_user[0].replaceAll("@", "").trim();

		for (User u : lista_usuarios) {
			if (u.getUsername().equals(username)) {
				paciente_seleccionado = u;
			}
		}

		FXMLLoader loader_user = new FXMLLoader(getClass().getResource("../views/VentanaMedicUsuarios.fxml"));
		MedicUserController control_user = new MedicUserController();
		loader_user.setController(control_user);
		Parent root2;
		try {
			root2 = loader_user.load();
			control_user.mostrarDatos(medico_logado, paciente_seleccionado);
			Stage stage = new Stage();
			stage.setTitle("Ventana del paciente");
			stage.setScene(new Scene(root2));
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cargarDatosPacientes(User medico) {
		medico_logado = medico;
		ArrayList<User> lista_pacientes = Database.getListaPacientes(medico_logado.getId());
		this.lista_usuarios = lista_pacientes;
		lblName.setText(medico.getNombre() + " " + medico.getApellidos());
		for (User user : lista_usuarios) {
			lvPacientes.getItems().add("@" + user.getUsername() + "\n " + user.getNombre() + " " + user.getApellidos());
		}

	}

}
