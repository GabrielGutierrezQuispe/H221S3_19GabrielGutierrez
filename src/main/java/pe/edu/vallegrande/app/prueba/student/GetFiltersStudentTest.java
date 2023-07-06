package pe.edu.vallegrande.app.prueba.student;

import java.util.List;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;

public class GetFiltersStudentTest {
    public static void main(String[] args) {
        try {
            // Datos
            Student bean = new Student();
            bean.setStudent_id(null);
            bean.setNames("");
            bean.setLastname("");
            bean.setEmail("");
            bean.setDocument_type("DNI");
            bean.setDocument_number("");
            bean.setSemester("");
            bean.setCareer("");
            bean.setActive("");

            // Proceso
            CrudStudentService studentService = new CrudStudentService();
            List<Student> lista = studentService.get(bean);
            if (!lista.isEmpty()) {
                System.out.println("Se filtraron los siguientes resultados:");
                for (Student student : lista) {
                    System.out.println("ID: " + student.getStudent_id());
                    System.out.println("Nombres: " + student.getNames());
                    System.out.println("Apellidos: " + student.getLastname());
                    System.out.println("Correo: " + student.getEmail());
                    System.out.println("Tipo de documento: " + student.getDocument_type());
                    System.out.println("Número de documento: " + student.getDocument_number());
                    System.out.println("Semestre: " + student.getSemester());
                    System.out.println("Carrera: " + student.getCareer());
                    System.out.println("Estado: " + student.getActive());
                    System.out.println("------------------------");
                }
            } else {
                System.out.println("No se encontraron estudiantes que cumplan con los criterios de filtrado.");
            }
        } catch (Exception e) {
            System.err.println("No se pudo filtrar la lista de estudiantes: " + e.getMessage());
        }
    }
}
