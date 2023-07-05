package pe.edu.vallegrande.app.prueba.student;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;

public class UpdateStudentTest {
    public static void main(String[] args) {
        try {
            // Crear objeto de estudiante
            Student student = new Student();
            student.setStudent_id(5); // ID del estudiante a actualizar
            student.setDocumentNumber("70843742");
            student.setNames("Pérez");
            student.setLastname("Garcia");
            student.setEmail("juan.perez@vallegrande.edu.pe");
            student.setSemester("I");
            student.setCareer("Análisis de Sistemas");
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
