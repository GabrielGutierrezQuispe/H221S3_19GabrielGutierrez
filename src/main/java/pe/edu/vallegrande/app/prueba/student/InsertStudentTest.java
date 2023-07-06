package pe.edu.vallegrande.app.prueba.student;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;

public class InsertStudentTest {
    public static void main(String[] args) {
        try {
            // Crear objeto de estudiante
            Student student = new Student();
            student.setNames("Juan");
            student.setLastname("Pérez");
            student.setEmail("juan.perez@vallegrande.edu.pe");
            student.setDocument_type("DNI");
            student.setDocument_number("32114753");
            student.setSemester("I");
            student.setCareer("Análisis de Sistemas");
            student.setActive("A");

            // Instanciar el servicio de estudiantes
            CrudStudentService studentService = new CrudStudentService();

            // Insertar estudiante
            studentService.insert(student);

            // Mostrar mensaje de éxito
            System.out.println("Estudiante insertado correctamente. ID: " + student.getStudent_id());
        } catch (Exception e) {
            System.err.println("No se pudo insertar el estudiante: " + e.getMessage());
        }
    }
}
