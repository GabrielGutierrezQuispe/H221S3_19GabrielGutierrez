package pe.edu.vallegrande.app.prueba.payment;

import pe.edu.vallegrande.app.service.CrudPaymentService;

public class DeleteLogicPaymentTest {
    public static void main(String[] args) {
        try {
            Integer id = 2; // ID del pago a eliminar

            // Instanciar el servicio de estudiantes
            CrudPaymentService paymentsS = new CrudPaymentService();

            // Eliminar estudiante
            paymentsS.delete(id);

            // Mostrar mensaje de éxito
            System.out.println("Pago cancelado correctamente.");
        } catch (Exception e) {
            // Mostrar mensaje de error
            System.err.println("No se pudo completar al cancelar: " + e.getMessage());
        }
    }
}