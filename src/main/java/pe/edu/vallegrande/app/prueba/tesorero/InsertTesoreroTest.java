package pe.edu.vallegrande.app.prueba.tesorero;

import pe.edu.vallegrande.app.model.tesorero;
import pe.edu.vallegrande.app.service.CrudTesoreroService;

public class InsertTesoreroTest {
    public static void main(String[] args) {
        try {
            // Crear objeto de estudiante
            tesorero tesorer = new tesorero();
            tesorer.setNames("Bartolomeo Max");
            tesorer.setLastname("Quispe Elguera");
            tesorer.setEmail("bartolomeo.quispe@vallegrande.edu.pe");
            tesorer.setDocument_type("DNI");
            tesorer.setDocument_number("97834781");
            tesorer.setPasswords("bartolomeo2023");
            tesorer.setActive("A");

            // Instanciar el servicio de estudiantes
            CrudTesoreroService tesoreroService = new CrudTesoreroService();

            // Insertar estudiante
            tesoreroService.insert(tesorer);

            // Mostrar mensaje de éxito
            System.out.println("Administrador insertado correctamente. ID: " + tesorer.getAdministrative_id());
        } catch (Exception e) {
            System.err.println("No se pudo insertar el administrador: " + e.getMessage());
        }
    }
}
