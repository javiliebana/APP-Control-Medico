package controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import java.util.List;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import DDBB.Database;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import modelos.Chat;
import modelos.HistoriaMedico;
import modelos.Mensaje;
import modelos.Paciente;
import modelos.SensorMov;
import modelos.SensorTemp;
import modelos.User;

public class UsersController {

	User user;
	Chat chat;

	@FXML
	private Label lblNombre;

	@FXML
	private Label lblApellidos;

	@FXML
	private Label lblDNI;

	@FXML
	private JFXTextArea lblPoliza;

	@FXML
	private JFXTextArea txrareaAlert;

	@FXML
	private TabPane tabPane;

	@FXML
	private TextField etTempDay;

	@FXML
	private Button btnTempDay;

	@FXML
	private JFXButton btnRefresh;

	@FXML
	private TextField etTempNight;

	@FXML
	private TextArea textAreaTemp;

	@FXML
	private TextArea textAreaChat;

	@FXML
	private TextField etText;

	@FXML
	private Button btnSend;

	@FXML
	private Label lblTituloNombre;

	@FXML
	private Accordion accordion;

	@FXML
	private LineChart<String, Number> graph_temp;

	@FXML
	void enviarMessage(MouseEvent event) {

		String text = etText.getText().toString();
		Mensaje new_msg = new Mensaje(0, chat.getId_chat(), user.getUsername(), text, "");
		Database.sendMessage(new_msg);

		etText.setText("");
		mostrarDatos(user);

	}

	@FXML
	void refreshData(MouseEvent event) {
		mostrarDatos(user);
	}

	public void mostrarDatos(User user) {
		// cargamos los datos del usuario
		Paciente paciente = Database.getPacienteFromIDUsuario(user.getId());
		this.user = user;
		lblTituloNombre.setText(user.getNombre() + " " + user.getApellidos());
		lblNombre.setText(user.getNombre());
		lblApellidos.setText(user.apellidos);
		lblPoliza.setText(paciente.getDescripcion());
		Database.setTemp(paciente.getId_paciente());
		String dato_temp = "";
		String dato_sensor_mov = "";
		String auxFecha = "";
		String text = "";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();
		String fecha = dtf.format(now).toString();

		// siempre que carguemos la ventana debemos borrar los datos del panel de citas
		// medicas
		List<Tab> pane_list = tabPane.getTabs();
		tabPane.getTabs().removeAll(pane_list);

		// cargamos las historias medicas en una arraylist
		ArrayList<HistoriaMedico> lista_historias = Database.cargarHistoriasMedicas(paciente.getId_paciente());
		// sacamos los a�os para ver cuantos tabs creamos
		ArrayList<String> list_year = new ArrayList<String>();
		for (HistoriaMedico h : lista_historias) {
			String date = h.getFecha().substring(0, 4);
			if (!list_year.contains(date)) {
				list_year.add(date);
			}
		}
		// ordenamos la lista de a�os

		Collections.sort(list_year);
		for (String s : list_year) {
			accordion = new Accordion();
			for (HistoriaMedico h : lista_historias) {
				if (s.contains(h.getFecha().substring(0, 4))) {
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
		// cargamos los datos para temperatura y mov
		ArrayList<SensorTemp> lista_temp = Database.cargarListaTemperaturas(paciente.getId_paciente());
		ArrayList<SensorMov> lista_mov = Database.cargarListaMovimiento(paciente.getId_paciente());

		for (SensorTemp t : lista_temp) {
			dato_temp += t.getFecha() + "\nTemperatura: " + t.getTemp() + "\nPorcentaje de Humedad: " + t.getHumedad()
					+ "%\n";
		}
		textAreaTemp.setText(dato_temp);

		for (SensorMov sm : lista_mov) {

			dato_sensor_mov += sm.getFecha() + "\n" + "      Ha salido del domicilio por " + sm.getAlerta() + "\n";

		}
		txrareaAlert.setText(dato_sensor_mov);

		// cargamos los datos del line chart
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Month");
		graph_temp.getData().clear();
		graph_temp.setTitle("Registro temperatura y humedad");

		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Temperatura (Cº)");
		XYChart.Series series2 = new XYChart.Series();
		series2.setName("Humedad (%)");

		for (SensorTemp t : lista_temp) {

			series1.getData().add(new XYChart.Data(t.getFecha(), Double.parseDouble(t.getTemp())));
			series2.getData().add(new XYChart.Data(t.getFecha(), Double.parseDouble(t.getHumedad())));
		}
		graph_temp.getData().addAll(series1, series2);

		// cargamos el chat y sus mensajes
		Chat chat_user = Database.getChat(user.getId());
		chat = chat_user;

		for (Mensaje msg : chat_user.getLista_mensajes()) {
			if (!auxFecha.equals(msg.getFecha_msg())) {
				text += msg.getFecha_msg() + "\n";
				auxFecha = msg.getFecha_msg();
			}
			text += msg.getUsername() + ": " + msg.getMsg() + "\n";
		}
		textAreaChat.setText(text);
	}

//	public static boolean isNumeric(String strNum) {
//		if (strNum == null) {
//			return false;
//		}
//		try {
//			double d = Double.parseDouble(strNum);
//		} catch (NumberFormatException nfe) {
//			return false;
//		}
//		return true;
//	}

}
