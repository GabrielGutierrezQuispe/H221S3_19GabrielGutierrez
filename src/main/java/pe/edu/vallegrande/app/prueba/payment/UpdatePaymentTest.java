package pe.edu.vallegrande.app.prueba.payment;

import pe.edu.vallegrande.app.model.payment;
import pe.edu.vallegrande.app.service.CrudPaymentService;

public class UpdatePaymentTest {
    public static void main(String[] args) {
        try {
            // Crear objeto de estudiante
            payment payments = new payment();
            payments.setId(1); // ID del estudiante a actualizar
            payments.setTypePayment("T");
            payments.setAmount("400");
            payments.setReferenceNumber("00001");
            payments.setStates("C");

            // Instanciar el servicio de estudiantes
            CrudPaymentService paymentService = new CrudPaymentService();

            // Actualizar estudiante
            paymentService.update(payments);

            // Mostrar mensaje de éxito
            System.out.println("Pago actualizado correctamente.");
        } catch (Exception e) {
            System.err.println("No se pudo tesorero el pago: " + e.getMessage());
        }
    }
}
