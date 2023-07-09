package pe.edu.vallegrande.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import pe.edu.vallegrande.app.model.tesorero;
import pe.edu.vallegrande.app.service.CrudTesoreroService;

@WebServlet({ "/TesoreroProcesar", "/TesoreroEliminar", "/TesoreroActivar", "/TesoreroBuscar",
		"/TesoreroListarInactivos" }) /// "/StudentListarInactivos"
public class TesoreroController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CrudTesoreroService service = new CrudTesoreroService();

	// Metodos url
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		switch (path) {
			case "/TesoreroProcesar":
				procesar(request, response);
				break;
			case "/TesoreroEliminar":
				eliminar(request, response);
				break;
			case "/TesoreroActivar":
				activar(request, response);
				break;
			case "/TesoreroBuscar":
				buscarPorFiltro(request, response);
				break;
			case "/TesoreroListarInactivos":
				listarInactivos(request, response);
				break;
		}
	}

	// Procesar
	private void procesar(HttpServletRequest request, HttpServletResponse response) {
		// Datos
		String accion = request.getParameter("accion");
		tesorero bean = new tesorero();

		// Validar y convertir los campos numéricos solo si no están vacíos
		if (request.getParameter("administrative_id") != ControllerUtil.STRING_EMPTY) {
			bean.setAdministrative_id(Integer.parseInt(request.getParameter("administrative_id")));
		}
		bean.setNames(request.getParameter("names"));
		bean.setLastname(request.getParameter("lastname"));
		bean.setEmail(request.getParameter("email"));
		bean.setDocument_type(request.getParameter("document_type"));
		bean.setDocument_number(request.getParameter("document_number"));
		bean.setPasswords(request.getParameter("passwords"));
		// proceso
		try {
			switch (accion) {
				case ControllerUtil.CRUD_NUEVO:
					service.insert(bean);
				case ControllerUtil.CRUD_EDITAR:
					service.update(bean);
					break;
			}
			ControllerUtil.responseJson(response, "administrative procesado con exito");
		} catch (Exception e) {
			ControllerUtil.responseJson(response, e.getMessage());
		}

	}

	// Buscar
	private void buscarPorFiltro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Datos
		String filter = request.getParameter("filter");
		String documentType = request.getParameter("documentType");
		String active = "A";
		
		if (request.getParameter("active") != "") {
			active = request.getParameter("active");
		}
		
		List<tesorero> searchResult = service.searchByFilter(filter, documentType, active);

		// Preparar la respuesta en formato JSON
		Gson gson = new Gson();
		String jsonData = gson.toJson(searchResult);

		// Enviar la respuesta
		ControllerUtil.responseJson(response, jsonData);
	}

	// Eliminar
	private void eliminar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer estudianteId = Integer.parseInt(request.getParameter("administrative_id"));
		service.delete(estudianteId);
	}

	// Activar
	private void activar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer studentId = Integer.parseInt(request.getParameter("administrative_id"));
		service.active(studentId);
	}

	// Listar inactivos
	private void listarInactivos(HttpServletRequest request, HttpServletResponse response) {
		// Datos
		List<tesorero> inactivos = service.getInactiveStudents();
		// preparando el JSON
		Gson gson = new Gson();
		String data = gson.toJson(inactivos);
		// reporte
		ControllerUtil.responseJson(response, data);
	}
}