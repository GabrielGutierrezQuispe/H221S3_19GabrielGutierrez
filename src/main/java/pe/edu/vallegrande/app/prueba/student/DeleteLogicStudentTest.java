package pe.edu.vallegrande.app.prueba.student;

import pe.edu.vallegrande.app.service.CrudStudentService;

public class DeleteLogicStudentTest {
    public static void main(String[] args) {
        try {
            String studentId = "1"; // ID del estudiante a eliminar

            // Instanciar el servicio de estudiantes
            CrudStudentService studentService = new CrudStudentService();

            // Eliminar estudiante
            studentService.delete(studentId);

            // Mostrar mensaje de éxito
            System.out.println("Estudiante eliminado lógicamente.");
        } catch (Exception e) {
            // Mostrar mensaje de error
            System.err.println("No se pudo completar la eliminación lógica del estudiante: " + e.getMessage());
        }
    }
}
