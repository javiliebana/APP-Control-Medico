package modelos;

public class SensorTemp {
	int id_temperatura;
	int id_paciente;
	String fecha_d;
	String fecha_n;
	String temp_d;
	String temp_n;
	public SensorTemp(int id_temperatura, int id_paciente, String fecha_d, String fecha_n, String temp_d,
			String temp_n) {
		super();
		this.id_temperatura = id_temperatura;
		this.id_paciente = id_paciente;
		this.fecha_d = fecha_d;
		this.fecha_n = fecha_n;
		this.temp_d = temp_d;
		this.temp_n = temp_n;
	}
	public int getId_temperatura() {
		return id_temperatura;
	}
	public void setId_temperatura(int id_temperatura) {
		this.id_temperatura = id_temperatura;
	}
	public int getId_paciente() {
		return id_paciente;
	}
	public void setId_paciente(int id_paciente) {
		this.id_paciente = id_paciente;
	}
	public String getFecha_d() {
		return fecha_d;
	}
	public void setFecha_d(String fecha_d) {
		this.fecha_d = fecha_d;
	}
	public String getFecha_n() {
		return fecha_n;
	}
	public void setFecha_n(String fecha_n) {
		this.fecha_n = fecha_n;
	}
	public String getTemp_d() {
		return temp_d;
	}
	public void setTemp_d(String temp_d) {
		this.temp_d = temp_d;
	}
	public String getTemp_n() {
		return temp_n;
	}
	public void setTemp_n(String temp_n) {
		this.temp_n = temp_n;
	}

	

}
