package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modelos.User;

public class ChatMedicFController {

    @FXML
    private Label lblName;

    @FXML
    private JFXTextArea txtChat;

    @FXML
    private TextField et_message;

    @FXML
    private JFXButton btn_send;
    
    public User user;

	public void mostrarDatos(User user) {
		lblName.setText(user.getNombre()+" "+user.getApellidos());
		
		
	}
    
    
    
}
