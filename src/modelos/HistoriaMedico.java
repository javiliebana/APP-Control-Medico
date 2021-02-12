package modelos;

public class HistoriaMedico {
	String fecha;
	String descripcion;

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public HistoriaMedico() {
		super();
	}

	public HistoriaMedico(String fecha, String descripcion) {
		super();
		this.fecha = fecha;
		this.descripcion = descripcion;
	}
	

}
