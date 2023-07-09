package pe.edu.vallegrande.app.prueba.tesorero;

import java.util.List;

import pe.edu.vallegrande.app.model.tesorero;
import pe.edu.vallegrande.app.service.CrudTesoreroService;

public class BuscarOtrosFiltros {

	public static void main(String[] args) {
		try {
			// Datos
			tesorero bean = new tesorero();
			bean.setNames("     "); //Jose
			bean.setLastname("    "); // Cama
			bean.setDocument_number(" 56743973 "); // 56743973
			// Proceso
			CrudTesoreroService TesoreroService = new CrudTesoreroService();
			List<tesorero> lista = TesoreroService.get(bean);
			for (tesorero tesorero : lista) {
				System.out.println(tesorero);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
