package controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

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
import javafx.scene.input.MouseEvent;
import modelos.HistoriaMedico;
import modelos.SensorTemp;
import modelos.User;

public class UsersController {

	User user;

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

	@FXML
	void enviarTempDay(MouseEvent event) {

		boolean isday = true;
		boolean newday = true;

		// fecha actual
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();
		String fecha = dtf.format(now).toString();

		for (SensorTemp st : user.getLista_sensor_temp()) {
			if (st.getTemp_n().equals("0")) {
				isday = false;
			} else if (st.getFecha().equals(fecha)) {
				newday = false;
			}

		}
		if (isday && newday) {

			// valor noche = 0 para despues buscar el obj temperatura con valor noche=0 y
			// reemplazarlo
			String temp_day = etTempDay.getText().toString();
			String temp_night = "0";
			SensorTemp st = new SensorTemp(fecha, temp_day, temp_night);

			ArrayList<User> list_user = JsonUtils.desserializarJsonAArray();
			for (int i = 0; i < list_user.size(); i++) {
				// reemplazamos el usuario por sus nuevos datos
				if (list_user.get(i).getUsername().equals(user.getUsername())) {
					list_user.get(i).getLista_sensor_temp().add(st);
					user.getLista_sensor_temp().add(st);

				}
			}

			JsonUtils.serializarArrayAJson(list_user);
			mostrarDatos(user);
		} else if (isday && !newday) {
			JOptionPane.showMessageDialog(null, "Solo una toma de temperatura de dia y otra de noche", "Error",
					JOptionPane.WARNING_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Ya ha introducido una temperatura", "Error",
					JOptionPane.WARNING_MESSAGE);
		}

	}

	@FXML
	void enviarTempNight(MouseEvent event) {

		boolean isnight = false;
		String temp_day = "0";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();
		String fecha = dtf.format(now).toString();
		String temp_night = etTempNight.getText().toString();
		for (int i = 0; i < user.getLista_sensor_temp().size(); i++) {

			if (user.getLista_sensor_temp().get(i).getTemp_n().equals("0")) {
				isnight = true;
			}
			if (user.getLista_sensor_temp().get(i).getFecha().equals(fecha)) {
				user.getLista_sensor_temp().get(i).setTemp_n(temp_night);
			}

		}
		if (isnight) {
			ArrayList<User> list_user = JsonUtils.desserializarJsonAArray();
			for (int i = 0; i < list_user.size(); i++) {
				if (list_user.get(i).getUsername().equals(user.getUsername())) {
					for (SensorTemp temp : list_user.get(i).getLista_sensor_temp()) {
						if (temp.getFecha().equals(fecha)) {
							temp.setTemp_n(temp_night);
						}
					}
				}
			}

			JsonUtils.serializarArrayAJson(list_user);
			mostrarDatos(user);

		} else {
			JOptionPane.showMessageDialog(null, "Debe introducir una fecha previa a la noche", "Error",
					JOptionPane.WARNING_MESSAGE);
		}

	}

	public void mostrarDatos(User user) {
		this.user = user;
		lblTituloNombre.setText(user.getNombre() + " " + user.getApellidos());
		lblNombre.setText(user.getNombre());
		lblApellidos.setText(user.apellidos);
		lblPoliza.setText(user.getPoliza());
		String dato_temp = "";

		// sacamos los años para ver cuantos tabs creamos
		ArrayList<String> list_year = new ArrayList<String>();
		for (HistoriaMedico h : user.getLista_historia_medico()) {
			String date = h.getFecha().substring(h.getFecha().length() - 4);
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
					tp.setText("Historial médico " + h.getFecha());
					JFXTextArea descripcion = new JFXTextArea();
					descripcion.setText(h.getDescripcion());
					tp.setContent(descripcion);
					accordion.getPanes().add(tp);

				}

			}
			Tab year = new Tab(s, accordion);
			tabPane.getTabs().add(year);

		}

		ArrayList<SensorTemp> lista_temp = user.getLista_sensor_temp();
		for (SensorTemp t : lista_temp) {
			dato_temp += t.getFecha() + "\nTemperatura de día: " + t.getTemp_d() + "\nTemperatura de noche: "
					+ t.getTemp_n() + "\n";
		}
		textAreaTemp.setText(dato_temp);

	}
}
