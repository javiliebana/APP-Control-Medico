package modelos;

import java.util.ArrayList;

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
	public ArrayList<IdFamiliares> lista_id_familiares;
	public String id_medico;
	public String chat;

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

	public ArrayList<IdFamiliares> getLista_id_familiares() {
		return lista_id_familiares;
	}

	public void setLista_id_familiares(ArrayList<IdFamiliares> lista_id_familiares) {
		this.lista_id_familiares = lista_id_familiares;
	}

	public String getId_medico() {
		return id_medico;
	}

	public void setId_medico(String id_medico) {
		this.id_medico = id_medico;
	}

	public String getChat() {
		return chat;
	}

	public void setChat(String chat) {
		this.chat = chat;
	}

	public User() {
		super();
	}

}
