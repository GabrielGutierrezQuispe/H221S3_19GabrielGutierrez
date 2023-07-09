package pe.edu.vallegrande.app.prueba.tesorero;

import pe.edu.vallegrande.app.service.CrudTesoreroService;

public class DeleteLogicTesoreroTest {
    public static void main(String[] args) {
        try {
            Integer tesoreroId = 5; // ID del estudiante a eliminar

            // Instanciar el servicio de estudiantes
            CrudTesoreroService tesoreroService = new CrudTesoreroService();

            // Eliminar estudiante
            tesoreroService.delete(tesoreroId);

            // Mostrar mensaje de éxito
            System.out.println("Administrador eliminado lógicamente.");
        } catch (Exception e) {
            // Mostrar mensaje de error
            System.err.println("No se pudo completar la eliminación lógica del administrador: " + e.getMessage());
        }
    }
}
