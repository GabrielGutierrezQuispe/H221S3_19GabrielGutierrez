package pe.edu.vallegrande.app.prueba.payment;

import pe.edu.vallegrande.app.model.payment;
import pe.edu.vallegrande.app.service.CrudPaymentService;

public class InsertPaymentTest {
    public static void main(String[] args) {
        try {
            // Crear objeto de estudiante
            payment payment = new payment();
            payment.setTypePayment( "E" ); //E O T
            payment.setAmount( "400" ); // el monto a pagar
            payment.setReferenceNumber("00006"); 
            payment.setStates("C");

            // Instanciar el servicio de estudiantes
            CrudPaymentService paymentService = new CrudPaymentService();

            // Insertar estudiante
            paymentService.insert(payment);

            // Mostrar mensaje de éxito
            System.out.println("Pago insertado correctamente. ID: " + payment.getId());
        } catch (Exception e) {
            System.err.println("No se pudo insertar el Pago: " + e.getMessage());
        }
    }
}
