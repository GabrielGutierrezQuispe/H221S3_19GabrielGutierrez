package pe.edu.vallegrande.app.prueba.tesorero;

import pe.edu.vallegrande.app.model.tesorero;
import pe.edu.vallegrande.app.service.CrudTesoreroService;

public class UpdateTesoreroTest {
    public static void main(String[] args) {
        try {
            // Crear objeto de estudiante
            tesorero tesorer = new tesorero();
            tesorer.setAdministrative_id(5); // ID del estudiante a actualizar
            tesorer.setNames("Roberto Owen");
            tesorer.setLastname("Mendoza Chavez");
            tesorer.setEmail("roberto.mendeza@vallegrande.edu.pe");
            tesorer.setDocument_type("DNI");
            tesorer.setDocument_number("63781874");
            tesorer.setPasswords("roberto2023");
            tesorer.setActive("A");

            // Instanciar el servicio de estudiantes
            CrudTesoreroService tesoreroService = new CrudTesoreroService();

            // Actualizar estudiante
            tesoreroService.update(tesorer);

            // Mostrar mensaje de éxito
            System.out.println("Administrador actualizado correctamente.");
        } catch (Exception e) {
            System.err.println("No se pudo tesorero el administrador: " + e.getMessage());
        }
    }
}
