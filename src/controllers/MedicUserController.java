package controllers;

import java.util.ArrayList;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;

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
import modelos.HistoriaMedico;
import modelos.IdFamiliares;
import modelos.SensorMov;
import modelos.SensorTemp;
import modelos.User;

public class MedicUserController {
	public User user;

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
	private TextArea textAreaTemp;

	@FXML
	private Label lblTituloNombre;

	@FXML
	private Accordion accordion;

	@FXML
	private LineChart<String, Number> graph_temp;

	@FXML
	private JFXListView<String> lvfamiliares;

	public void mostrarDatos(User user, ArrayList<User> lista_usuarios) {

		// cargamos los datos del usuario
		this.user = user;
		lblTituloNombre.setText(user.getNombre() + " " + user.getApellidos());
		lblNombre.setText(user.getNombre());
		lblApellidos.setText(user.apellidos);
		lblPoliza.setText(user.getPoliza());
		String dato_temp = "";
		String dato_sensor_mov = "";

		// sacamos los años para ver cuantos tabs creamos
		ArrayList<String> list_year = new ArrayList<String>();
		for (HistoriaMedico h : user.getLista_historia_medico()) {
			String date = h.getFecha().substring(h.getFecha().length() - 4);
			if (!list_year.contains(date)) {
				list_year.add(date);
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

		for (SensorTemp t : user.getLista_sensor_temp()) {
			dato_temp += t.getFecha() + "\nTemperatura de día: " + t.getTemp_d() + "\nTemperatura de noche: "
					+ t.getTemp_n() + "\n";
		}
		textAreaTemp.setText(dato_temp);

		for (SensorMov sm : user.getLista_sensor_mov()) {
			if (sm.getAlerta().equals("T")) {
				dato_sensor_mov += sm.getFecha() + "\n" + "      Ha salido del domicilio\n";
			}

		}
		txrareaAlert.setText(dato_sensor_mov);

		// cargamos los datos del line chart
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Month");
		graph_temp.setTitle("Registro temperaturas");

		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Temperatura día");
		XYChart.Series series2 = new XYChart.Series();
		series2.setName("Temperatura noche");

		for (SensorTemp t : user.getLista_sensor_temp()) {

			series1.getData().add(new XYChart.Data(t.getFecha(), Double.parseDouble(t.getTemp_d())));
			series2.getData().add(new XYChart.Data(t.getFecha(), Double.parseDouble(t.getTemp_n())));
		}
		graph_temp.getData().addAll(series1, series2);

		for (IdFamiliares f : user.getLista_id_familiares()) {
			for (User u : lista_usuarios) {
				if (f.getId().equals(u.getUsername())) {
					lvfamiliares.getItems().add("@" + u.getUsername() + "\n " + u.getNombre() + " " + u.getApellidos());
				}

			}
		}

	}

}
