package controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import DDBB.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import modelos.Chat;
import modelos.Mensaje;
import modelos.User;

public class ChatMedicFController {
	public User user, medic;
	public Chat chat;

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
		String text = etText.getText().toString();
		Mensaje new_msg = new Mensaje(0, chat.getId_chat(), medic.getUsername(), text, "");
		Database.sendMessage(new_msg);

		etText.setText("");
		mostrarDatos(user,medic);
	}


	public void mostrarDatos(User user, User medic) {
		this.user = user;
		this.medic = medic;
		String auxFecha = "";
		String text = "";
		System.out.println("id del user familiar: "+user.getId());
		lblName.setText(user.getNombre() + " " + user.getApellidos());

		// cargamos el chat y sus mensajes
		Chat chat = Database.getChat(user.getId());
		this.chat=chat;
		for (Mensaje msg : chat.getLista_mensajes()) {
			if (!auxFecha.equals(msg.getFecha_msg())) {
				text += msg.getFecha_msg() + "\n";
				auxFecha = msg.getFecha_msg();
			}
			text += msg.getUsername() + ": " + msg.getMsg() + "\n";

			textAreaChat.setText(text);
		}
		textAreaChat.setText(text);
		
	}

}
