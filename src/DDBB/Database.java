package DDBB;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modelos.HistoriaMedico;
import modelos.Paciente;
import modelos.SensorMov;
import modelos.SensorTemp;
import modelos.User;

public class Database {

	private ResultSet rs = null;
	private PreparedStatement StmtParam;
	private static Connection conexion = null;

	public Database() {

		// conexion a la base de datos
		try {
			// conectamos a db
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicapp", "root", "");
			// conexion =
			// DriverManager.getConnection("jdbc:mariadb://2.139.176.212:3306/test",
			// "prdsantiago", "*medicapp*2");

		} catch (SQLException eSQL) {
			System.out.println("Error SQL: " + eSQL.toString());
			// close_connection(conexion);
		} catch (Exception e) {
			System.out.println("Error no controlado: " + e.toString());
			// close_connection(conexion);
		}
	}

	public static Connection getConexion() {
		return conexion;
	}

	/**
	 * ==================== Cerrar conexi�n bbdd ====================
	 */
	private void close_connection(Connection conexion) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (StmtParam != null) {
				StmtParam.close();
			}

			if (conexion != null) {
				conexion.close();
			}
		} catch (Exception e) {
			System.out.println("Error close_connection: " + e);
		}
	}

	public static User userLogin(String username, String password) {
		try {
			PreparedStatement ps = conexion.prepareStatement(LOGINQUERY);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				User user = new User(rs.getInt("ID_USUARIO"), rs.getString("USERNAME"), rs.getString("PASSWORD"),
						rs.getString("NOMBRE"), rs.getString("APELLIDO"), rs.getString("TELEFONO"), rs.getString("DNI"),
						rs.getString("ROL"));
				System.out.println(rs.getString("ROL"));
				return user;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static ArrayList<User> getListaPacientes(int id) {
		try {
			ArrayList<User> lista_users = new ArrayList<User>();
			PreparedStatement ps = conexion.prepareStatement(MEDIC_USER_LIST);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User(rs.getInt("ID_USUARIO"), rs.getString("USERNAME"), rs.getString("PASSWORD"),
						rs.getString("NOMBRE"), rs.getString("APELLIDO"), rs.getString("TELEFONO"), rs.getString("DNI"),
						rs.getString("ROL"));
				lista_users.add(user);
			}
			return lista_users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static Paciente cargarPaciente(int id) {
		try {
			PreparedStatement ps = conexion.prepareStatement(PACIENTEQUERY);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				Paciente p = new Paciente(rs.getInt("ID_PACIENTE"), rs.getInt("ID_USUARIO"), rs.getInt("ID_MEDICO"),
						rs.getString("DESCRIPCION"));
				System.out.println(rs.getString("DESCRIPCION"));
				return p;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<HistoriaMedico> cargarHistoriasMedicas(int id_paciente) {
		try {
			ArrayList<HistoriaMedico> lista_historias = new ArrayList<HistoriaMedico>();
			PreparedStatement ps = conexion.prepareStatement(HISTORIASQUERY);
			ps.setInt(1, id_paciente);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				HistoriaMedico hm = new HistoriaMedico(rs.getInt("ID_HISTORIA"), rs.getInt("ID_PACIENTE"),
						rs.getString("DESCRIPCION"), rs.getString("FECHA_EVENTO"));
				lista_historias.add(hm);
			}
			return lista_historias;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<SensorTemp> cargarListaTemperaturas(int id_paciente) {
		try {
			ArrayList<SensorTemp> lista_temperaturas = new ArrayList<SensorTemp>();
			PreparedStatement ps = conexion.prepareStatement(TEMPSQUERY);
			ps.setInt(1, id_paciente);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				SensorTemp stemp= new SensorTemp(rs.getInt("ID_TEMPERATURA"), rs.getInt("ID_PACIENTE"), rs.getString("FECHA_TEMP_DIA"), rs.getString("FECHA_TEMP_NOCHE"), rs.getString("TEMP_DIA"), rs.getString("TEMP_NOCHE"));
				lista_temperaturas.add(stemp);
			}
			return lista_temperaturas;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<SensorMov> cargarListaMovimiento(int id_paciente) {
		try {
			ArrayList<SensorMov> lista_movimiento = new ArrayList<SensorMov>();
			PreparedStatement ps = conexion.prepareStatement(SENSORLISTQUERY);
			ps.setInt(1, id_paciente);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				SensorMov smov= new SensorMov(rs.getInt("ID_MOVIMIENTO"), rs.getInt("ID_PACIENTE"), rs.getString("FECHA_EVENTO"), rs.getString("SENSOR"));
				lista_movimiento.add(smov);
			}
			return lista_movimiento;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * QUERY SQL
	 */
	private final static String LOGINQUERY = "select * from Usuario where USERNAME=? and PASSWORD= ?";
	private final static String MEDIC_USER_LIST = "SELECT * FROM Usuario where ROL='P' and ID_USUARIO in (select ID_USUARIO from paciente where ID_MEDICO=(select ID_MEDICO from medico where ID_USUARIO=?))";
	private final static String PACIENTEQUERY = "select * from Paciente where ID_USUARIO=?";
	private final static String HISTORIASQUERY = "select * from Historial_Medico where ID_PACIENTE=?";
	private final static String TEMPSQUERY = "select * from Temperatura where ID_PACIENTE=?";
	private final static String SENSORLISTQUERY = "select * from Movimiento where ID_PACIENTE=?";

}