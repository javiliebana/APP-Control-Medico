package modelos;

public class SensorTemp {
	int id_temperatura;
	int id_paciente;
	String fecha;
	String temp;
	String humedad;
	
	public SensorTemp(int id_temperatura, int id_paciente, String fecha, String temp, String humedad) {
		super();
		this.id_temperatura = id_temperatura;
		this.id_paciente = id_paciente;
		this.fecha = fecha;
		this.temp = temp;
		this.humedad = humedad;
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getHumedad() {
		return humedad;
	}

	public void setHumedad(String humedad) {
		this.humedad = humedad;
	}
	
	
	
	

}
