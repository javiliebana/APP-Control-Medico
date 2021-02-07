package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelos.User;


public class MedicController {
	
	User selected_user;
	ArrayList<User> lista_usuarios;
	
	
	@FXML
	private Label lblName;

	@FXML
	private ListView<String> lvPacientes;
	
	@FXML
	public void handleMouseClick(MouseEvent arg0) {
		String list_value=lvPacientes.getSelectionModel().getSelectedItem();
		String[] valores_user=list_value.split(" ");
		String username=valores_user[0].replaceAll("@", "").trim();
		System.out.println(username);
		
		for (User u : lista_usuarios) {
			if (u.getUsername().equals(username)) {
				selected_user=u;
			}
		}
		
		
		FXMLLoader loader_user = new FXMLLoader(getClass().getResource("../views/Users_GUI.fxml"));
		UsersController contro_luser =  new UsersController();
		loader_user.setController(contro_luser);
		Parent root2;
		try {
			root2 = loader_user.load();
			contro_luser.mostrarDatos(selected_user);
			Stage stage = new Stage();
			stage.setTitle("User");
			stage.setScene(new Scene(root2));
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void cargarDatosPacientes(User medico, ArrayList<User> lista_usuarios) {
		this.lista_usuarios=lista_usuarios;
		lblName.setText(medico.getNombre()+" "+medico.getApellidos());
		for (User user : lista_usuarios) {
			//solo mostramos los usuarios asignados a ese paciente y con rol de paciente
			if (user.getId_medico().equals(medico.getUsername()) && user.getRol().equals("U")) {
				lvPacientes.getItems().add("@"+user.getUsername()+"\n "+user.getNombre()+" "+user.getApellidos());
			}
			
			
		}
	}
	
	
}