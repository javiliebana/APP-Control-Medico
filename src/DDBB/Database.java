package DDBB;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import modelos.Chat;
import modelos.Familiar;
import modelos.HistoriaMedico;
import modelos.Mensaje;
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
			 //conexion =DriverManager.getConnection("jdbc:mysql://localhost:3306/prmedicapp", "root","");
			conexion = DriverManager.getConnection("jdbc:mariadb://2.139.176.212:3306/prmedicapp", "prjliebana_admin",
					"*medicapp*2");

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

	public static ArrayList<User> getListaFamiliares(int id_paciente) {
		try {
			ArrayList<User> lista_users = new ArrayList<User>();
			PreparedStatement ps = conexion.prepareStatement(LISTA_FAMILIARES);
			ps.setInt(1, id_paciente);
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

	public static Paciente getPacienteFromIDUsuario(int id) {
		try {
			PreparedStatement ps = conexion.prepareStatement(PACIENTEQUERY);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				Paciente p = new Paciente(rs.getInt("ID_PACIENTE"), rs.getInt("ID_USUARIO"), rs.getInt("ID_MEDICO"),
						rs.getString("DESCRIPCION"));
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
				SensorTemp stemp = new SensorTemp(rs.getInt("ID_TEMPERATURA"), rs.getInt("ID_PACIENTE"),
						rs.getString("FECHA_TEMP"), rs.getString("TEMPERATURA"), rs.getString("HUMEDAD"));
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
				SensorMov smov = new SensorMov(rs.getInt("ID_MOVIMIENTO"), rs.getInt("ID_PACIENTE"),
						rs.getString("FECHA_EVENTO"), rs.getString("SENSOR"));
				lista_movimiento.add(smov);
			}
			return lista_movimiento;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static Chat getChat(int id_user) {
		try {
			PreparedStatement ps = conexion.prepareStatement(CHATQUERYUSERTOMEDIC);
			ps.setInt(1, id_user);
			ResultSet rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				Chat c = new Chat(rs.getInt("ID_CHAT"), rs.getInt("ID_USER_MEDIC"), rs.getInt("ID_USER"));
				ArrayList<Mensaje> lista_mensajes = getListaMensajes(rs.getInt("ID_CHAT"));
				c.setLista_mensajes(lista_mensajes);
				return c;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private static ArrayList<Mensaje> getListaMensajes(int id_chat) {
		try {
			ArrayList<Mensaje> lista_mensajes = new ArrayList<Mensaje>();
			PreparedStatement ps = conexion.prepareStatement(MSGQUERY);
			ps.setInt(1, id_chat);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Mensaje msg = new Mensaje(rs.getInt("ID_MSG"), rs.getInt("ID_CHAT"), rs.getString("USERNAME_SEND"),
						rs.getString("MSG"), rs.getString("MSG_FECHA"));
				lista_mensajes.add(msg);
			}
			return lista_mensajes;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static void sendMessage(Mensaje new_msg) {

		try {
			PreparedStatement ps = conexion.prepareStatement(INSERT_MSG_QUERY);
			ps.setInt(1, new_msg.getId_chat());
			ps.setString(2, new_msg.getUsername());
			ps.setString(3, new_msg.getMsg());
			ResultSet rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	public static void insertHistoriaMedica(HistoriaMedico hm) {
		try {
			PreparedStatement ps = conexion.prepareStatement(INSERT_MEDIC_HISOTRY_QUERY);
			ps.setInt(1, hm.getId_paciente());
			ps.setString(2, hm.getDescripcion());
			ps.setString(3, hm.getFecha());
			ResultSet rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	public static Paciente getPacienteFromIDFamiliar(int id) {
		try {
			PreparedStatement ps = conexion.prepareStatement(GET_PACIENTE_FROM_ID_USER_FAMILIAR);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				Paciente p = new Paciente(rs.getInt("ID_PACIENTE"), rs.getInt("ID_USUARIO"), rs.getInt("ID_MEDICO"),
						rs.getString("DESCRIPCION"));
				return p;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static User getUserFromID(int id) {
		try {
			PreparedStatement ps = conexion.prepareStatement(GET_USER_FROM_ID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				User user = new User(rs.getInt("ID_USUARIO"), rs.getString("USERNAME"), rs.getString("PASSWORD"),
						rs.getString("NOMBRE"), rs.getString("APELLIDO"), rs.getString("TELEFONO"), rs.getString("DNI"),
						rs.getString("ROL"));
				return user;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static int getUserIDFromUsername(String username) {
		try {
			PreparedStatement ps = conexion.prepareStatement(GET_USER_ID_FROM_USERNAME);
			ps.setString(1, username);
			int id = 0;
			ResultSet rs = ps.executeQuery();
			if (rs != null && rs.next()) {

				id = rs.getInt("ID_USUARIO");

				return id;
			}
			return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public static ArrayList<User> getListaUsuariosPacientes() {
		try {
			ArrayList<User> lista_users = new ArrayList<User>();
			PreparedStatement ps = conexion.prepareStatement(GET_USER_ROL_PACIENTE_LIST);
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

	public static int getUserIDFromMedicID(int id) {
		try {
			PreparedStatement ps = conexion.prepareStatement(GET_USER_ID_FROM_MEDIC_ID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs != null && rs.next()) {

				int paciente_id = rs.getInt("ID_USUARIO");

				return paciente_id;
			}
			return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public static void setTemp(int id_paciente) {
		try {
			PreparedStatement ps = conexion.prepareStatement(TEMP_INICIO);
			ps.setInt(1, id_paciente);
			ResultSet rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	public static void setMov(int id_paciente) {
		try {
			PreparedStatement ps = conexion.prepareStatement(MOV_INICIO);
			ps.setInt(1, id_paciente);
			ResultSet rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	public static void insertNewUser(User new_user) {
		try {
			PreparedStatement ps = conexion.prepareStatement(INSERT_NEW_USER);
			ps.setString(1, new_user.getUsername());
			ps.setString(2, new_user.getPassword());
			ps.setString(3, new_user.getNombre());
			ps.setString(4, new_user.getApellidos());
			ps.setString(5, new_user.getTelefono());
			ps.setString(6, new_user.getDni());
			ps.setString(7, new_user.getRol());
			ResultSet rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	public static void insertNewFamiliar(Familiar new_familiar) {
		try {
			PreparedStatement ps = conexion.prepareStatement(INSERT_NEW_FAMILIAR);
			ps.setInt(1, new_familiar.getId_usuario());
			ps.setInt(2, new_familiar.getId_paciente());

			ResultSet rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	public static void insertNewChat(Chat new_chat_familiar_medico) {
		try {
			PreparedStatement ps = conexion.prepareStatement(INSERT_NEW_CHAT);
			ps.setInt(1, new_chat_familiar_medico.getId_medico());
			ps.setInt(2, new_chat_familiar_medico.getId_usuario());

			ResultSet rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

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
	private final static String CHATQUERYUSERTOMEDIC = "select * from CHAT where ID_USER=?";
	private final static String MSGQUERY = "select * from Mensaje where ID_CHAT=?";
	private final static String TEMP_INICIO = "update Temperatura set ID_PACIENTE=? where ID_PACIENTE is null";
	private final static String MOV_INICIO = "update Movimiento set ID_PACIENTE=? where ID_PACIENTE is null";
	private final static String LISTA_FAMILIARES = "select * from Usuario where ID_USUARIO in (select id_usuario from Familiar where ID_PACIENTE=?)";
	private final static String GET_USER_FROM_ID = "select * from Usuario where ID_USUARIO=?";
	private final static String GET_PACIENTE_FROM_ID_USER_FAMILIAR = "select * from Paciente where id_paciente=(select id_paciente from Familiar where id_usuario=?)";
	private final static String GET_USER_ID_FROM_USERNAME = "select ID_USUARIO from Usuario where USERNAME=?";
	private final static String GET_USER_ID_FROM_MEDIC_ID = "select ID_USUARIO from Medico where ID_MEDICO=?";
	private final static String GET_USER_ROL_PACIENTE_LIST = "SELECT * FROM Usuario where ROL='P'";

	private final static String INSERT_MSG_QUERY = "INSERT INTO Mensaje (ID_CHAT,USERNAME_SEND,MSG) values (?,?,?)";
	private final static String INSERT_MEDIC_HISOTRY_QUERY = "INSERT INTO Historial_Medico (ID_PACIENTE,DESCRIPCION,FECHA_EVENTO) values(?,?,?)";
	private final static String INSERT_NEW_USER = "INSERT INTO Usuario (USERNAME,PASSWORD,NOMBRE,APELLIDO,TELEFONO,DNI,ROL) values (?,?,?,?,?,?,?)";
	private final static String INSERT_NEW_FAMILIAR = "INSERT INTO Familiar (ID_USUARIO,ID_PACIENTE) values (?,?)";
	private final static String INSERT_NEW_CHAT = "INSERT INTO Chat (ID_USER_MEDIC,ID_USER) values (?,?)";

}
