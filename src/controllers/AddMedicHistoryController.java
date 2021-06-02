package controllers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;

import DDBB.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import modelos.HistoriaMedico;
import modelos.Paciente;
import modelos.User;

public class AddMedicHistoryController {
	User medic, selected_user;
	private Paciente paciente;
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		try {
			String fecha = formatter.format(datePicker.getValue()).toString();
			if (!txdescripcion.getText().toString().trim().equals("")) {
				String description = txdescripcion.getText().toString();
				HistoriaMedico hm = new HistoriaMedico(0, paciente.getId_paciente(), description, fecha);

				Database.insertHistoriaMedica(hm);
				ventana_user_selected.mostrarDatos(medic, selected_user);
				// cerramos la ventana del formulario
				Stage stage = (Stage) btnCancel.getScene().getWindow();
				stage.close();
			} else {
				JOptionPane.showMessageDialog(null, "Debe introducir una descripi�n", "Error",
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

	public void loadData(User medic, User user, Paciente paciente, MedicUserController ventana_user_selected) {

		lblName.setText(user.getNombre() + " " + user.getApellidos());
		this.medic = medic;
		this.selected_user = user;
		this.paciente=paciente;
		this.ventana_user_selected = ventana_user_selected;

	}
}
