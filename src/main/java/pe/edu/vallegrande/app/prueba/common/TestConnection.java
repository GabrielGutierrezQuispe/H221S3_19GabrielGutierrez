package pe.edu.vallegrande.app.prueba.common;

import java.sql.Connection;

import pe.edu.vallegrande.app.db.AccesoDB;

public class TestConnection {

	public static void main(String[] args) {
		try {
			Connection cn = AccesoDB.getConnection();
			System.out.println("Conexión Ok");
			cn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
