package pe.edu.vallegrande.app.prueba.student;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;

public class GetForIdStudentTest {
    public static void main(String[] args) {
        try {
            CrudStudentService studentService = new CrudStudentService();
            Student bean = studentService.getForId("1");
            if (bean != null) {
                System.out.println("Se encontró el siguiente estudiante:");
                System.out.println(bean);
            } else {
                System.out.println("No se encontró ningún estudiante con el ID especificado.");
            }
        } catch (Exception e) {
            System.err.println("No se pudo obtener el estudiante por su ID: " + e.getMessage());
        }
    }
}
