package pe.edu.vallegrande.app.prueba.payment;

import java.util.List;

import pe.edu.vallegrande.app.model.payment;
import pe.edu.vallegrande.app.service.CrudPaymentService;

public class GetFiltersPaymentTest {
    public static void main(String[] args) {
        try {
            // Datos
            payment bean = new payment();
            bean.setTypePayment(" T "); //T o E
            bean.setDates("  "); // 2023-07-09
            bean.setReferenceNumber("  "); //00001
            bean.setStates("  "); //D C 

            // Proceso
            CrudPaymentService paymentService = new CrudPaymentService();
            List<payment> lista = paymentService.get(bean);
            if (!lista.isEmpty()) {
                System.out.println("Se filtraron los siguientes resultados:");
                for (payment payments : lista) {
                    System.out.println("ID: " + payments.getId());
                    System.out.println("Tipo de Pago: " + payments.getTypePayment());
                    System.out.println("Cantidad: " + payments.getAmount());
                    System.out.println("Fecha: " + payments.getDates());
                    System.out.println("Numero de Pago: " + payments.getReferenceNumber());
                    System.out.println("Tiempo limite: " + payments.getTermTime());
                    System.out.println("Estado: " + payments.getStates());
                    System.out.println("------------------------");
                }
            } else {
                System.out.println("No se encontraron pagos que cumplan con los criterios de filtrado.");
            }
        } catch (Exception e) {
            System.err.println("No se pudo filtrar la lista de pagos: " + e.getMessage());
        }
    }
}
