package modelos;

public class Familiar {
	int id_familiar;
	int id_usuario;
	int id_paciente;

	public Familiar(int id_familiar, int id_usuario, int id_paciente) {
		super();
		this.id_familiar = id_familiar;
		this.id_usuario = id_usuario;
		this.id_paciente = id_paciente;
	}

	public int getId_familiar() {
		return id_familiar;
	}

	public void setId_familiar(int id_familiar) {
		this.id_familiar = id_familiar;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public int getId_paciente() {
		return id_paciente;
	}

	public void setId_paciente(int id_paciente) {
		this.id_paciente = id_paciente;
	}

}
