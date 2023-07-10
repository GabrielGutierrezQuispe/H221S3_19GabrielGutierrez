package pe.edu.vallegrande.app.prueba.tesorero;

import pe.edu.vallegrande.app.model.tesorero;
import pe.edu.vallegrande.app.service.CrudTesoreroService;

public class GetForIdTesoreroTest {
    public static void main(String[] args) {
        try {
            CrudTesoreroService tesoreroService = new CrudTesoreroService();
            tesorero bean = tesoreroService.getForId("1");
            if (bean != null) {
                System.out.println("Se encontró el siguiente administrador:");
                System.out.println(bean);
            } else {
                System.out.println("No se encontró ningún administrador con el ID especificado.");
            }
        } catch (Exception e) {
            System.err.println("No se pudo obtener el administrador por su ID: " + e.getMessage());
        }
    }
}
