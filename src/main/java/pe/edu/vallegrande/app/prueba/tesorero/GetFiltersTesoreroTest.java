package pe.edu.vallegrande.app.prueba.tesorero;

import java.util.List;

import pe.edu.vallegrande.app.model.tesorero;
import pe.edu.vallegrande.app.service.CrudTesoreroService;

public class GetFiltersTesoreroTest {
    public static void main(String[] args) {
        try {
            // Datos
            tesorero bean = new tesorero();
            bean.setAdministrative_id(null);
            bean.setNames("");
            bean.setLastname("");
            bean.setEmail("");
            bean.setDocument_type("DNI");
            bean.setDocument_number("");
            bean.setPasswords("");
            bean.setActive("");

            // Proceso
            CrudTesoreroService tesoreroService = new CrudTesoreroService();
            List<tesorero> lista = tesoreroService.get(bean);
            if (!lista.isEmpty()) {
                System.out.println("Se filtraron los siguientes resultados:");
                for (tesorero tesorero : lista) {
                    System.out.println("ID: " + tesorero.getAdministrative_id());
                    System.out.println("Nombres: " + tesorero.getNames());
                    System.out.println("Apellidos: " + tesorero.getLastname());
                    System.out.println("Correo: " + tesorero.getEmail());
                    System.out.println("Tipo de documento: " + tesorero.getDocument_type());
                    System.out.println("Número de documento: " + tesorero.getDocument_number());
                    System.out.println("Contraseña: " + tesorero.getPasswords());
                    System.out.println("Estado: " + tesorero.getActive());
                    System.out.println("------------------------");
                }
            } else {
                System.out.println("No se encontraron Administradores que cumplan con los criterios de filtrado.");
            }
        } catch (Exception e) {
            System.err.println("No se pudo filtrar la lista de estudiantes: " + e.getMessage());
        }
    }
}
