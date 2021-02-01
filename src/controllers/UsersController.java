package controllers;

import java.util.ArrayList;

import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import modelos.HistoriaMedico;
import modelos.SensorTemp;
import modelos.User;

public class UsersController {
	@FXML
	private Label lblNombre;

	@FXML
	private Label lblApellidos;

	@FXML
	private Label lblDNI;

	@FXML
	private JFXTextArea lblPoliza;

	@FXML
	private TabPane tabPane;

	@FXML
	private TextField etTempDay;

	@FXML
	private Button btnTempDay;

	@FXML
	private TextField etTempNight;

	@FXML
	private TextArea textAreaTemp;

	@FXML
	private Label lblTituloNombre;
	
	@FXML
    private Accordion accordion;

	public void mostrarDatos(User user) {

		lblTituloNombre.setText(user.getNombre());
		lblNombre.setText(user.getNombre());
		lblApellidos.setText(user.apellidos);
		lblPoliza.setText(user.getPoliza());
		String dato_temp = "";
		
		//sacamos los años para ver cuantos tabs creamos
		ArrayList<String> list_year = new ArrayList<String>();
		for (HistoriaMedico h : user.getLista_historia_medico()) {
			String date=h.getFecha().substring(h.getFecha().length() - 4);
			if (!list_year.contains(date)) {
				list_year.add(date);
				System.out.println(date);
			}
		}
		for (String s : list_year) {
				accordion = new Accordion();
			 for (HistoriaMedico h : user.getLista_historia_medico()) {
				 if (s.contains(h.getFecha().substring(h.getFecha().length() - 4))) {
					 TitledPane tp = new TitledPane();
					 tp.setText("Historial médico "+ h.getFecha());
					 JFXTextArea descripcion = new JFXTextArea();
					 descripcion.setText(h.getDescripcion());
					 tp.setContent(descripcion);
					 accordion.getPanes().add(tp);
					 
					}
				
			}
			 Tab year = new Tab(s,accordion);
			 tabPane.getTabs().add(year);
		
		}
		
		ArrayList<SensorTemp> lista_temp = user.getLista_sensor_temp();
		for (SensorTemp t : lista_temp) {
			dato_temp += t.getFecha() + "\nTemperatura de día: " + t.getTemp_d() + "\nTemperatura de noche: " + t.getTemp_n() + "\n";
		}
		textAreaTemp.setText(dato_temp);

	}
}
