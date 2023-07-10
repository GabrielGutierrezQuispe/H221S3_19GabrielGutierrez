package pe.edu.vallegrande.app.prueba.payment;

import pe.edu.vallegrande.app.model.payment;
import pe.edu.vallegrande.app.service.CrudPaymentService;

public class GetForIdPaymentTest {
    public static void main(String[] args) {
        try {
            CrudPaymentService paymentsService = new CrudPaymentService();
            payment bean = paymentsService.getForId(1);
            if (bean != null) {
                System.out.println("Se encontró el siguiente pago:");
                System.out.println(bean);
            } else {
                System.out.println("No se encontró ningún pago con el ID especificado.");
            }
        } catch (Exception e) {
            System.err.println("No se pudo obtener el pago por su ID: " + e.getMessage());
        }
    }
}
