package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import DDBB.Database;

public class User {

	public String username;
	public String password;
	public String rol;
	public String sexo;
	public String nombre;
	public String apellidos;
	public String poliza;
	public ArrayList<HistoriaMedico> lista_historia_medico;
	public ArrayList<SensorTemp> lista_sensor_temp;
	public ArrayList<SensorMov> lista_sensor_mov;
	public String id_medico;
	public String v_usuario;
	public ArrayList<Chat> lista_chat;
	public Connection cn;
	

	public User() {
		if(cn==null)
			cn = Database.getConexion();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getPoliza() {
		return poliza;
	}

	public void setPoliza(String poliza) {
		this.poliza = poliza;
	}

	public ArrayList<HistoriaMedico> getLista_historia_medico() {
		return lista_historia_medico;
	}

	public void setLista_historia_medico(ArrayList<HistoriaMedico> lista_historia_medico) {
		this.lista_historia_medico = lista_historia_medico;
	}

	public ArrayList<SensorTemp> getLista_sensor_temp() {
		return lista_sensor_temp;
	}

	public void setLista_sensor_temp(ArrayList<SensorTemp> lista_sensor_temp) {
		this.lista_sensor_temp = lista_sensor_temp;
	}

	public ArrayList<SensorMov> getLista_sensor_mov() {
		return lista_sensor_mov;
	}

	public void setLista_sensor_mov(ArrayList<SensorMov> lista_sensor_mov) {
		this.lista_sensor_mov = lista_sensor_mov;
	}

	public String getId_medico() {
		return id_medico;
	}

	public void setId_medico(String id_medico) {
		this.id_medico = id_medico;
	}

	public ArrayList<Chat> getLista_chat() {
		return lista_chat;
	}

	public void setLista_chat(ArrayList<Chat> lista_chat) {
		this.lista_chat = lista_chat;
	}

	public String getV_usuario() {
		return v_usuario;
	}

	public void setV_usuario(String v_usuario) {
		this.v_usuario = v_usuario;
	}


	public User(String username, String password, String rol, String nombre, String apellidos, String id_medico,
			String v_usuario, ArrayList<Chat> lista_chat) {
		super();
		this.username = username;
		this.password = password;
		this.rol = rol;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.id_medico = id_medico;
		this.v_usuario = v_usuario;
		this.lista_chat = lista_chat;
	}
	
	public String userLogin(String username, String password) {
		try {
			PreparedStatement ps = cn.prepareStatement(LOGINQUERY);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				String rol = rs.getString("ROL");
				return rol;
			} else {
				return ("ERROR");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ("ERROR");
		}
	
	}
	
	/**
	 * QUERY SQL
	 */	
	private static String LOGINQUERY = "select * from usuario where USERNAME=? and PASSWORD= ?";

	

}
