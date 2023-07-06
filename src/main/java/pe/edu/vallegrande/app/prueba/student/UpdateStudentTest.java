package pe.edu.vallegrande.app.prueba.student;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;

public class UpdateStudentTest {
    public static void main(String[] args) {
        try {
            // Crear objeto de estudiante
            Student student = new Student();
            student.setStudent_id(5); // ID del estudiante a actualizar
            student.setNames("Juan");
            student.setLastname("Chavez");
            student.setEmail("juan.p@vallegrande.edu.pe");
            student.setDocument_type("DNI");
            student.setDocument_number("63438792");
            student.setSemester("V");
            student.setCareer("Produción Agraria");
            student.setActive("A");

            // Instanciar el servicio de estudiantes
            CrudStudentService studentService = new CrudStudentService();

            // Actualizar estudiante
            studentService.update(student);

            // Mostrar mensaje de éxito
            System.out.println("Estudiante actualizado correctamente.");
        } catch (Exception e) {
            System.err.println("No se pudo actualizar el estudiante: " + e.getMessage());
        }
    }
}
