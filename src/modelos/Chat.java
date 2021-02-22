package modelos;

public class Chat {
	String fecha;
	String medico;
	String usuario;
	String texto;
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
		
	}
	public String getMedico() {
		return texto;
	}
	public void setMedico(String medico) {
		this.medico = medico;
	}
	
	
	public Chat(String fecha,  String medico, String usuario, String texto) {
		super();
		this.fecha = fecha;
		this.medico = medico;
		this.usuario = usuario;
		this.texto = texto;

	}
}
