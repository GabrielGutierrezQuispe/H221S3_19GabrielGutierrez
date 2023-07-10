package pe.edu.vallegrande.app.prueba.payment;

import java.util.List;

import pe.edu.vallegrande.app.model.payment;
import pe.edu.vallegrande.app.service.CrudPaymentService;

public class GetAllPaymentTest {
    public static void main(String[] args) {
        try {
            CrudPaymentService paymentService = new CrudPaymentService();
            List<payment> lista = paymentService.getAll();
            if (!lista.isEmpty()) {
                System.out.println("Lista de Pagos:");
                for (payment payments : lista) {
                    System.out.println(payments);
                }
            } else {
                System.out.println("No se encontraron Pagos.");
            }
        } catch (Exception e) {
            System.err.println("Error al obtener la lista de Pagos: " + e.getMessage());
        }
    }
}