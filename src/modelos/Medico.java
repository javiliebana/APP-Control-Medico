package modelos;

public class Medico {
	int id_medico;
	int id_usuario;

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

	public Medico(int id_medico, int id_usuario) {
		super();
		this.id_medico = id_medico;
		this.id_usuario = id_usuario;
	}

}
