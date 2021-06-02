package controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;

import DDBB.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelos.Chat;
import modelos.HistoriaMedico;
import modelos.Mensaje;
import modelos.Paciente;
import modelos.SensorMov;
import modelos.SensorTemp;
import modelos.User;

public class FamilyUserController {
	public User familiar;
	public User user_paciente;
	public Paciente paciente;
	public Chat chat;

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
	private AnchorPane anchorpanehist;

	@FXML
	private TabPane tabPane;

	@FXML
	private TextArea textAreaTemp;

	@FXML
	private Label lblTituloNombre;

	@FXML
	private Accordion accordion;

	@FXML
	private LineChart<String, Number> graph_temp;

	@FXML
	private TextArea textAreaChat;

	@FXML
	private TextField etText;

	@FXML
	private Button btnSend;

	@FXML
	void enviarMessage(MouseEvent event) {
		String text = etText.getText().toString();
		Mensaje new_msg = new Mensaje(0, chat.getId_chat(), familiar.getUsername(), text, "");
		Database.sendMessage(new_msg);
		
		etText.setText("");
		mostrarDatos(familiar);
	}

	public void mostrarDatos(User familiar) {
		// cargamos los datos del usuario
		Paciente paciente = Database.getPacienteFromIDFamiliar(familiar.getId());
		User user_paciente= Database.getUserFromID(paciente.getId_usuario());
		this.familiar=familiar;
		this.user_paciente = user_paciente;
		this.paciente = paciente;
		lblTituloNombre.setText(user_paciente.getNombre() + " " + user_paciente.getApellidos());
		lblNombre.setText(user_paciente.getNombre());
		lblApellidos.setText(user_paciente.apellidos);
		lblPoliza.setText(paciente.getDescripcion());
		String auxFecha = "";
		String text = "";

		// siempre que carguemos la ventana debemos borrar los datos del panel de citas
		// medicas
		List<Tab> pane_list = tabPane.getTabs();
		tabPane.getTabs().removeAll(pane_list);

		String dato_temp = "";
		String dato_sensor_mov = "";

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
			dato_temp += t.getFecha_d() + "\nTemperatura de día: " + t.getTemp_d() + "\nTemperatura de noche: "
					+ t.getTemp_n() + "\n";
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
		graph_temp.setTitle("Registro temperaturas");

		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Temperatura día");
		XYChart.Series series2 = new XYChart.Series();
		series2.setName("Temperatura noche");

		for (SensorTemp t : lista_temp) {

			series1.getData().add(new XYChart.Data(t.getFecha_d(), Double.parseDouble(t.getTemp_d())));
		}
		graph_temp.getData().addAll(series1, series2);

		// cargamos el chat y sus mensajes
		Chat chat = Database.getChat(familiar.getId());
		this.chat=chat;
		for (Mensaje msg : chat.getLista_mensajes()) {
			if (!auxFecha.equals(msg.getFecha_msg())) {
				text += msg.getFecha_msg() + "\n";
				auxFecha = msg.getFecha_msg();
			}
			text += msg.getUsername() + ": " + msg.getMsg() + "\n";
		}
		textAreaChat.setText(text);

	}

}
