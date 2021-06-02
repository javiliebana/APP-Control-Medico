package modelos;

import java.util.ArrayList;

public class Chat {
	int id_chat;
	int id_medico;
	int id_usuario;
	ArrayList<Mensaje> lista_mensajes;
	
	
	public Chat(int id_chat, int id_medico, int id_usuario) {
		super();
		this.id_chat = id_chat;
		this.id_medico = id_medico;
		this.id_usuario = id_usuario;
	}
	public int getId_chat() {
		return id_chat;
	}
	public void setId_chat(int id_chat) {
		this.id_chat = id_chat;
	}
	public int getId_medico() {
		return id_medico;
	}
	public void setId_medico(int id_medico) {
		this.id_medico = id_medico;
	}
	public int getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	public ArrayList<Mensaje> getLista_mensajes() {
		return lista_mensajes;
	}
	public void setLista_mensajes(ArrayList<Mensaje> lista_mensajes) {
		this.lista_mensajes = lista_mensajes;
	}
	
	
	
}
