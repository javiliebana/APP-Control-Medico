package modelos;

public class SensorTemp {
	String fecha;
	String temp_d;
	String temp_n;

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
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

	public SensorTemp(String fecha, String temp_d, String temp_n) {
		super();
		this.fecha = fecha;
		this.temp_d = temp_d;
		this.temp_n = temp_n;
	}

	public SensorTemp() {
		super();
	}

}
