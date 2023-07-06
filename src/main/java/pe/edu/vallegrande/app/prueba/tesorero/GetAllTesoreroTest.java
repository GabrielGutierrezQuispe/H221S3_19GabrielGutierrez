package pe.edu.vallegrande.app.prueba.tesorero;

import java.util.List;

import pe.edu.vallegrande.app.model.tesorero;
import pe.edu.vallegrande.app.service.CrudTesoreroService;

public class GetAllTesoreroTest {
    public static void main(String[] args) {
        try {
            CrudTesoreroService tesoreroService = new CrudTesoreroService();
            List<tesorero> lista = tesoreroService.getAll();
            if (!lista.isEmpty()) {
                System.out.println("Lista de Estudiantes:");
                for (tesorero tesorero : lista) {
                    System.out.println(tesorero);
                }
            } else {
                System.out.println("No se encontraron Estudiantes.");
            }
        } catch (Exception e) {
            System.err.println("Error al obtener la lista de estudiantes: " + e.getMessage());
        }
    }
}