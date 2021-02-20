package controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import modelos.Chat;
import modelos.User;

public class ChatMedicFController {
	public User user;
	public String medic;

    @FXML
    private Label lblName;

    @FXML
    private JFXTextArea textAreaChat;

    @FXML
    private TextField etText;

    @FXML
    private JFXButton btnSend;

    @FXML
    void enviarMessage(MouseEvent event) {
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();
		String fecha = dtf.format(now).toString();
		String text = etText.getText().toString();
		
		ArrayList<User> list_user = JsonUtils.desserializarJsonAArray();
		
		Chat c = new Chat(fecha, user.getId_medico(), medic, text);
		
		for (int i = 0; i < list_user.size(); i++) {
			// reemplazamos el usuario por sus nuevos datos
			if (list_user.get(i).getUsername().equals(user.getUsername())) {
				if(user.getLista_chat() != null) {
					// generar lista_chat automaticamente por código
				}
				list_user.get(i).getLista_chat().add(c);
				user.getLista_chat().add(c);
			}
			
			JsonUtils.serializarArrayAJson(list_user);
			mostrarDatos(user);
		}
		etText.setText("");
    }
  
	public void mostrarDatos(User user) {	
		this.user = user;
		String auxFecha = "";
		String text = "";
		
		lblName.setText(user.getNombre()+" "+user.getApellidos());
		
		if(user.getLista_chat() != null) {
			// mostrar texto escrito en el area del chat
			for (Chat c : user.getLista_chat()) {
				if (!auxFecha.equals(c.getFecha())) {
					text += c.getFecha() + "\n";
					auxFecha = c.getFecha();	
				}	
				text += c.getUsuario() + ": "+ c.getTexto() + "\n";
			}
		}
		textAreaChat.setText(text);
				
	}    
    
	public void mostrarDatos(User user, String medic) {
		this.user = user;
		this.medic = medic;

		mostrarDatos(user);	
	}  
    
	
	
}
