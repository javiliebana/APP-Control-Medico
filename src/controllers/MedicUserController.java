package controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.lang.model.util.Elements;

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
import modelos.IdFamiliares;
import modelos.Mensaje;
import modelos.Paciente;
import modelos.SensorMov;
import modelos.SensorTemp;
import modelos.User;

public class MedicUserController {
	public User medic, user;
	public Paciente paciente;
	public User selected_user;
	public ArrayList<User> lista_familiares;
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
	private JFXListView<String> lvfamiliares;

	@FXML
	private TextArea textAreaChat;

	@FXML
	private TextField etText;

	@FXML
	private Button btnSend;

	@FXML
	void enviarMessage(MouseEvent event) {
		String text = etText.getText().toString();
		Mensaje new_msg = new Mensaje(0, chat.getId_chat(), medic.getUsername(), text, "");
		Database.sendMessage(new_msg);

		etText.setText("");
		mostrarDatos(medic, user);
	}

	@FXML
	void addMedicHistory(MouseEvent event) {

		FXMLLoader loader_user = new FXMLLoader(getClass().getResource("../views/VentanaAddMedicHistory.fxml"));
		AddMedicHistoryController control_user = new AddMedicHistoryController();
		loader_user.setController(control_user);

		try {
			Parent root2 = loader_user.load();
			control_user.loadData(medic,user,paciente, this);
			Stage stage = new Stage();
			stage.setTitle("User");
			Scene chatscene = new Scene(root2);
			chatscene.getStylesheets().addAll(getClass().getResource("../css/chat.css").toExternalForm());
			stage.setScene(chatscene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// llamar a chat con familiar
	@FXML
	public void handleMouseClick(MouseEvent arg0) {
		String list_value = lvfamiliares.getSelectionModel().getSelectedItem();
		String[] valores_user = list_value.split(" ");
		String username = valores_user[0].replaceAll("@", "").trim();

		for (User u : lista_familiares) {
			if (u.getUsername().equals(username)) {
				selected_user = u;
			}
		}

		FXMLLoader loader_user = new FXMLLoader(getClass().getResource("../views/FamilyMedicChat.fxml"));
		ChatMedicFController control_user = new ChatMedicFController();
		loader_user.setController(control_user);
		Parent root2;
		try {
			root2 = loader_user.load();
			control_user.mostrarDatos(selected_user, medic);
			Stage stage = new Stage();
			stage.setTitle("User");
			stage.setScene(new Scene(root2));
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mostrarDatos(User medic, User user) {
		// cargamos los datos del usuario
		Paciente paciente = Database.getPacienteFromIDUsuario(user.getId());
		this.medic = medic;
		this.user = user;
		this.paciente=paciente;
		lblTituloNombre.setText(user.getNombre() + " " + user.getApellidos());
		lblNombre.setText(user.getNombre());
		lblApellidos.setText(user.apellidos);
		lblPoliza.setText(paciente.getDescripcion());
		String auxFecha = "";
		String text = "";

		// siempre que carguemos la ventana debemos borrar los datos del panel de citas
		// medicas
		List<Tab> pane_list = tabPane.getTabs();
		tabPane.getTabs().removeAll(pane_list);
		List<String> elements=lvfamiliares.getItems();
		lvfamiliares.getItems().removeAll(elements);

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
				if (s.contains(h.getFecha().substring(0,4))) {
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

			// cargamos la lisa de familiares que tienen asignado a este paciente
			ArrayList<User> lista_familiares = Database.getListaFamiliares(paciente.getId_paciente());
			this.lista_familiares = lista_familiares;
			for (User u : lista_familiares) {

				lvfamiliares.getItems().add("@" + u.getUsername() + "\n " + u.getNombre() + " " + u.getApellidos());

			}

			// cargamos el chat y sus mensajes
			Chat chat_user = Database.getChat(user.getId());
			chat=chat_user;
			
			for (Mensaje msg : chat_user.getLista_mensajes()) {
				if (!auxFecha.equals(msg.getFecha_msg())) {
					text += msg.getFecha_msg() + "\n";
					auxFecha = msg.getFecha_msg();	
				}	
				text += msg.getUsername() + ": "+ msg.getMsg() + "\n";
			}
			textAreaChat.setText(text);

		}

	}


