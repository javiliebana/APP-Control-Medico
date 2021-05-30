package modelos;

public class HistoriaMedico {
	int id_historia;
	int id_paciente;
	String descripcion;
	String fecha;

	public HistoriaMedico(int id_historia, int id_paciente, String descripcion, String fecha) {
		super();
		this.id_historia = id_historia;
		this.id_paciente = id_paciente;
		this.descripcion = descripcion;
		this.fecha = fecha;
	}

	public int getId_historia() {
		return id_historia;
	}

	public void setId_historia(int id_historia) {
		this.id_historia = id_historia;
	}

	public int getId_paciente() {
		return id_paciente;
	}

	public void setId_paciente(int id_paciente) {
		this.id_paciente = id_paciente;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}
