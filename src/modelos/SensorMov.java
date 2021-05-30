package modelos;

public class SensorMov {
	int id_movimiento;
	int id_paciente;
	String fecha;
	String alerta;

	public SensorMov(int id_movimiento, int id_paciente, String fecha, String alerta) {
		super();
		this.id_movimiento = id_movimiento;
		this.id_paciente = id_paciente;
		this.fecha = fecha;
		this.alerta = alerta;
	}

	public int getId_movimiento() {
		return id_movimiento;
	}

	public void setId_movimiento(int id_movimiento) {
		this.id_movimiento = id_movimiento;
	}

	public int getId_paciente() {
		return id_paciente;
	}

	public void setId_paciente(int id_paciente) {
		this.id_paciente = id_paciente;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getAlerta() {
		return alerta;
	}

	public void setAlerta(String alerta) {
		this.alerta = alerta;
	}

}
