package DDBB;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	private ResultSet rs = null;
	private PreparedStatement StmtParam;
	private static Connection conexion = null;

	public Database() {

		// conexion a la base de datos
		try {
			// conectamos a db
			conexion = DriverManager.getConnection("jdbc:mariadb://2.139.176.212:3306/test", "prdsantiago", "*medicapp*2");
			
		} catch (SQLException eSQL) {
			System.out.println("Error SQL: " + eSQL.toString());
			//close_connection(conexion);
		} catch (Exception e) {
			System.out.println("Error no controlado: " + e.toString());
			//close_connection(conexion);
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

}
