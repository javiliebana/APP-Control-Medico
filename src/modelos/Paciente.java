package modelos;

public class Paciente {
	int id_paciente;
	int id_usuario;
	int id_medico;
	String descripcion;

	public int getId_paciente() {
		return id_paciente;
	}

	public void setId_paciente(int id_paciente) {
		this.id_paciente = id_paciente;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public int getId_medico() {
		return id_medico;
	}

	public void setId_medico(int id_medico) {
		this.id_medico = id_medico;
	}
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Paciente(int id_paciente, int id_usuario, int id_medico,String descripcion) {
		super();
		this.id_paciente = id_paciente;
		this.id_usuario = id_usuario;
		this.id_medico = id_medico;
		this.descripcion = descripcion;
	}

}
