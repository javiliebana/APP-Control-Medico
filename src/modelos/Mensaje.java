package modelos;

public class Mensaje {
	int id_mensaje, id_chat;
	String username, msg, fecha_msg;

	public Mensaje(int id_mensaje, int id_chat, String username, String msg, String fecha_msg) {
		super();
		this.id_mensaje = id_mensaje;
		this.id_chat = id_chat;
		this.username = username;
		this.msg = msg;
		this.fecha_msg = fecha_msg;
	}

	public int getId_mensaje() {
		return id_mensaje;
	}

	public void setId_mensaje(int id_mensaje) {
		this.id_mensaje = id_mensaje;
	}

	public int getId_chat() {
		return id_chat;
	}

	public void setId_chat(int id_chat) {
		this.id_chat = id_chat;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getFecha_msg() {
		return fecha_msg;
	}

	public void setFecha_msg(String fecha_msg) {
		this.fecha_msg = fecha_msg;
	}

}