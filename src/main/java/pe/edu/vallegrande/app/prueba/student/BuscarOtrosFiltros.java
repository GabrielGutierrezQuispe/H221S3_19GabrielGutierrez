package pe.edu.vallegrande.app.prueba.student;

import java.util.List;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;

public class BuscarOtrosFiltros {

	public static void main(String[] args) {
		try {
			// Datos
			Student bean = new Student();
			bean.setNames("     "); //Mario
			bean.setLastname("    ");  //Gutierrez
			bean.setDocument_number("   "); //56743973
			bean.setSemester("  "); // I, II, III, IV, V
			bean.setCareer(" Análisis de Sistemas "); // Análisis de Sistemas o Producion Agraria
			// Proceso
			CrudStudentService studentService = new CrudStudentService();
			List<Student> lista = studentService.get(bean);
			for (Student student : lista) {
				System.out.println(student);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
