package pe.edu.vallegrande.app.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Conexion a db cloud Azure Shirley
//String urlDB = "jdbc:sqlserver://scavontime-server.database.windows.net:1433;database=dbScavOnTime;encrypt=true;trustServerCertificate=false;";
//String user = "user";
//String pass = "scavontime#2023";

//Conexion a db cloud Azure Gabriel
//String urlDB = "jdbc:sqlserver://serverdatabasescavontime.database.windows.net:1433;database=db_ScavOnTime;encrypt=true;trustServerCertificate=false;trustServerCertificate=false;";
//String user = "adm";
//String pass = "Gabriel#70069439";

public class AccesoDB {

	public static Connection getConnection() throws SQLException {
		Connection conexion = null;
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

		//Conexion a base local Shirley
		String urlDB = "jdbc:sqlserver://localhost:1433;databaseName=H221S3_19GabrielGutierrez;encrypt=true;TrustServerCertificate=True;";
		String user = "sa";
		String pass = "Gabriel#70069439";

		//Conexion a base local Gabriel
//		String urlDB = "jdbc:sqlserver://serverdatabasescavontime.database.windows.net:1433;database=db_ScavOnTime;encrypt=true;trustServerCertificate=false;trustServerCertificate=false;";
//		String user = "adm";
//		String pass = "Gabriel#70069439";
		
		try {
			// Conexión con la BD
			Class.forName(driver).getDeclaredConstructor().newInstance();
			conexion = DriverManager.getConnection(urlDB, user, pass);
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw new SQLException("No se encontro el driver de la BD.");
		} catch (Exception e) {
			throw new SQLException("No se puede establecer conexión de la BD.");
		}
		return conexion;
	}
}
