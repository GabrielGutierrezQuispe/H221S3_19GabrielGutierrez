package pe.edu.vallegrande.app.prueba.student;

import java.util.List;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;

public class GetAllStudentTest {
    public static void main(String[] args) {
        try {
            CrudStudentService studentService = new CrudStudentService();
            List<Student> lista = studentService.getAll();
            if (!lista.isEmpty()) {
                System.out.println("Lista de Estudiantes:");
                for (Student teacher : lista) {
                    System.out.println(teacher);
                }
            } else {
                System.out.println("No se encontraron Estudiantes.");
            }
        } catch (Exception e) {
            System.err.println("Error al obtener la lista de estudiantes: " + e.getMessage());
        }
    }
}