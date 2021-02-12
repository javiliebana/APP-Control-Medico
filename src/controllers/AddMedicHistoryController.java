package controllers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import modelos.HistoriaMedico;
import modelos.User;

public class AddMedicHistoryController {
	User selected_user;
	ArrayList<User> lista_usuarios;
	MedicUserController ventana_user_selected;

	@FXML
	private Label lblName;

	@FXML
	private JFXDatePicker datePicker;

	@FXML
	private JFXTextArea txdescripcion;

	@FXML
	private JFXButton btnAccept;

	@FXML
	private JFXButton btnCancel;

	@FXML
	void acceptMedicHistory(MouseEvent event) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {
			String fecha = formatter.format(datePicker.getValue()).toString();
			if (txdescripcion.getText().toString().trim().equals("")) {
				String description = txdescripcion.getText().toString();
				HistoriaMedico hm = new HistoriaMedico(fecha, description);

				for (User user : lista_usuarios) {
					if (user.getUsername().equals(selected_user.getUsername())) {
						user.getLista_historia_medico().add(hm);
					}
				}
				JsonUtils.serializarArrayAJson(lista_usuarios);
				ventana_user_selected.mostrarDatos(selected_user, lista_usuarios);
				// cerramos la ventana del formulario
				Stage stage = (Stage) btnCancel.getScene().getWindow();
				stage.close();
			} else {
				JOptionPane.showMessageDialog(null, "Debe introducir una descripión", "Error",
						JOptionPane.WARNING_MESSAGE);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Debe introducir una fecha correcta", "Error",
					JOptionPane.WARNING_MESSAGE);
		}

	}

	@FXML
	void cancelMedicHistory(MouseEvent event) {
		// cerramos la ventana del formulario
		Stage stage = (Stage) btnCancel.getScene().getWindow();
		stage.close();
	}

	public void loadData(User user, ArrayList<User> lista_usuarios, MedicUserController ventana_user_selected) {
		lblName.setText(user.getNombre() + " " + user.getApellidos());
		this.selected_user = user;
		this.lista_usuarios = lista_usuarios;
		this.ventana_user_selected = ventana_user_selected;

	}
}
