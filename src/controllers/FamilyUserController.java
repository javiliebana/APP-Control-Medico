package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelos.HistoriaMedico;
import modelos.SensorMov;
import modelos.SensorTemp;
import modelos.User;

public class FamilyUserController {
	public User user;
	public User selected_user;
	public ArrayList<User> lista_usuarios;

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



	public void mostrarDatos(User user, ArrayList<User> lista_usuarios) {
		// guardamos los datos del usuario actual para el chat
			this.user = user;
			this.lista_usuarios = lista_usuarios;
			User v_user=null;
			//cargamos la ventana con los datos del paciente (su v_user)
		for (User v_u : lista_usuarios) {
			if (v_u.getUsername().equals(user.getV_usuario())) {
				System.out.println("v_user: "+v_u.getUsername());
				v_user=v_u;
				lblTituloNombre.setText("Viendo el perfil de: "+v_u.getNombre() + " " + v_user.getApellidos());
				lblNombre.setText(v_u.getNombre());
				lblApellidos.setText(v_u.apellidos);
				lblPoliza.setText(v_u.getPoliza());
			}
		}
		
		
		List<Tab> pane_list=tabPane.getTabs();
		tabPane.getTabs().removeAll(pane_list);
		
		
		String dato_temp = "";
		String dato_sensor_mov = "";
		// sacamos los a�os para ver cuantos tabs creamos
		ArrayList<String> list_year = new ArrayList<String>();
		for (HistoriaMedico h : v_user.getLista_historia_medico()) {
			String date = h.getFecha().substring(h.getFecha().length() - 4);
			if (!list_year.contains(date)) {
				list_year.add(date);
			}
		}
		//ordenamos la lista de a�os
		Collections.sort(list_year);
		
		for (String s : list_year) {
			accordion = new Accordion();
			for (HistoriaMedico h : v_user.getLista_historia_medico()) {
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

		for (SensorTemp t : v_user.getLista_sensor_temp()) {
			dato_temp += t.getFecha() + "\nTemperatura de día: " + t.getTemp_d() + "\nTemperatura de noche: "
					+ t.getTemp_n() + "\n";
		}
		textAreaTemp.setText(dato_temp);

		for (SensorMov sm : v_user.getLista_sensor_mov()) {
			if (sm.getAlerta().equals("T")) {
				dato_sensor_mov += sm.getFecha() + "\n" + "      Ha salido del domicilio\n";
			}

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

		for (SensorTemp t : v_user.getLista_sensor_temp()) {

			series1.getData().add(new XYChart.Data(t.getFecha(), Double.parseDouble(t.getTemp_d())));
			series2.getData().add(new XYChart.Data(t.getFecha(), Double.parseDouble(t.getTemp_n())));
		}
		graph_temp.getData().addAll(series1, series2);

	

	}

}

