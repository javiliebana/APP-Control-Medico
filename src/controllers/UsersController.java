package controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXTextArea;

import DDBB.Database;
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
	void enviarMessage(MouseEvent event) {

		String text = etText.getText().toString();
		Mensaje new_msg = new Mensaje(0, chat.getId_chat(), user.getUsername(), text, "");
		Database.sendMessage(new_msg);
		
		etText.setText("");
		mostrarDatos(user);
		
	}
	
	@FXML
	void enviarTempDay(MouseEvent event) {

//		boolean isday = true;
//		boolean newday = true;
//		boolean correctvalue = isNumeric(etTempDay.getText().toString());
//
//		// fecha actual
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//		LocalDateTime now = LocalDateTime.now();
//		String fecha = dtf.format(now).toString();
//
//		for (SensorTemp st : user.getLista_sensor_temp()) {
//			if (st.getTemp_n().equals("0")) {
//				//si la temperatura de la noche es 0 es porque se hacreado ya un objeto con la temperatura de dia
//				isday = false;
//			} else if (st.getFecha().equals(fecha)) {
//				//si la fecha corresponde a la fecha del dia de hoy, el usuario ya ha registrado su temperatura diaria
//				newday = false;
//			}
//		}
//		
//		if (isday && newday && correctvalue) {
//
//			// valor noche = 0 para despues buscar el obj temperatura con valor noche=0 y
//			// reemplazarlo
//			String temp_day = etTempDay.getText().toString();
//			String temp_night = "0";
//			SensorTemp st = new SensorTemp(fecha, temp_day, temp_night);
//
//			ArrayList<User> list_user = JsonUtils.desserializarJsonAArray();
//			for (int i = 0; i < list_user.size(); i++) {
//				// reemplazamos el usuario por sus nuevos datos
//				if (list_user.get(i).getUsername().equals(user.getUsername())) {
//					list_user.get(i).getLista_sensor_temp().add(st);
//					user.getLista_sensor_temp().add(st);
//				}
//			}
//
//			JsonUtils.serializarArrayAJson(list_user);
//			mostrarDatos(user);
//			etTempDay.setText("");
//		} else if (isday && !newday) {
//			JOptionPane.showMessageDialog(null, "Solo una toma de temperatura de dia y otra de noche", "Error",
//					JOptionPane.WARNING_MESSAGE);
//		} else if (!correctvalue) {
//			JOptionPane.showMessageDialog(null, "Debe introducir un valor correcto", "Error",
//					JOptionPane.WARNING_MESSAGE);
//		} else {
//			JOptionPane.showMessageDialog(null, "Ya ha introducido una temperatura", "Error",
//					JOptionPane.WARNING_MESSAGE);
//		}
		
	}

	@FXML
	void enviarTempNight(MouseEvent event) {

//		boolean isnight = false;
//		boolean correctvalue = isNumeric(etTempNight.getText().toString());
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//		LocalDateTime now = LocalDateTime.now();
//		String fecha = dtf.format(now).toString();
//		String temp_night = etTempNight.getText().toString();
//
//		if (correctvalue) {
//			for (int i = 0; i < user.getLista_sensor_temp().size(); i++) {
//
//				if (user.getLista_sensor_temp().get(i).getTemp_n().equals("0")) {
//					//si existe una temperatura =0 entonces si es de noche
//					isnight = true;
//				}
//				if (user.getLista_sensor_temp().get(i).getFecha().equals(fecha)) {
//					user.getLista_sensor_temp().get(i).setTemp_n(temp_night);
//				}
//
//			}
//
//			if (isnight) {
//				ArrayList<User> list_user = JsonUtils.desserializarJsonAArray();
//				for (int i = 0; i < list_user.size(); i++) {
//					if (list_user.get(i).getUsername().equals(user.getUsername())) {
//						for (SensorTemp temp : list_user.get(i).getLista_sensor_temp()) {
//							if (temp.getFecha().equals(fecha)) {
//								temp.setTemp_n(temp_night);
//							}
//						}
//					}
//				}
//
//				JsonUtils.serializarArrayAJson(list_user);
//				mostrarDatos(user);
//				etTempNight.setText("");
//
//			} else {
//				JOptionPane.showMessageDialog(null, "Debe introducir una fecha previa a la noche", "Error",
//						JOptionPane.WARNING_MESSAGE);
//			}
//
//		} else {
//			JOptionPane.showMessageDialog(null, "Debe introducir un valor correcto", "Error",
//					JOptionPane.WARNING_MESSAGE);
//		}
	}
	
	public void mostrarDatos(User user) {
		//cargamos los datos del usuario
		Paciente paciente = Database.getPacienteFromIDUsuario(user.getId());
		this.user = user;
		lblTituloNombre.setText(user.getNombre() + " " + user.getApellidos());
		lblNombre.setText(user.getNombre());
		lblApellidos.setText(user.apellidos);
		lblPoliza.setText(paciente.getDescripcion());
		String dato_temp = "";
		String dato_sensor_mov="";
		String auxFecha = "";
		String text = "";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();
		String fecha = dtf.format(now).toString();
		
		//siempre que carguemos la ventana debemos borrar los datos del panel de citas medicas
		List<Tab> pane_list=tabPane.getTabs();
		tabPane.getTabs().removeAll(pane_list);

		//cargamos las historias medicas en una arraylist
		ArrayList<HistoriaMedico> lista_historias = Database.cargarHistoriasMedicas(paciente.getId_paciente());
		// sacamos los a�os para ver cuantos tabs creamos
		ArrayList<String> list_year = new ArrayList<String>();
		for (HistoriaMedico h : lista_historias) {
			String date = h.getFecha().substring(0,4);
			if (!list_year.contains(date)) {
				list_year.add(date);
			}
		}
		//ordenamos la lista de a�os
		
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
		//cargamos los datos para temperatura y mov
		ArrayList<SensorTemp> lista_temp = Database.cargarListaTemperaturas(paciente.getId_paciente());
		ArrayList<SensorMov> lista_mov = Database.cargarListaMovimiento(paciente.getId_paciente());

		for (SensorTemp t : lista_temp) {
			dato_temp += t.getFecha_d() + "\nTemperatura de día: " + t.getTemp_d() + "\nTemperatura de noche: "
					+ t.getTemp_n() + "\n";
		}
		textAreaTemp.setText(dato_temp);
		
		for (SensorMov sm : lista_mov) {
			
			dato_sensor_mov +=sm.getFecha()+"\n"+"      Ha salido del domicilio por "+ sm.getAlerta()+"\n";
			
		}
		txrareaAlert.setText(dato_sensor_mov);
		
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
