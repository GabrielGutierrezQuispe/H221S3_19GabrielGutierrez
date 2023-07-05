package pe.edu.vallegrande.app.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class ControllerUtil {


	// Constantes del CRUD
	public final static String CRUD_NUEVO = "NUEVO";
	public final static String CRUD_EDITAR = "EDITAR";
	
	public final static String STRING_EMPTY = "";
	public final static String CRUD_ELIMINAR = "ELIMINAR";

	
	
	public static void responseJson(HttpServletResponse response, String data) {
		try {
			PrintWriter out = response.getWriter();

			response.setContentType("application/jsom");
			response.setCharacterEncoding("utf-8");

			out.print(data);
			out.flush();
		} catch (Exception e) {
		}
	}
}
