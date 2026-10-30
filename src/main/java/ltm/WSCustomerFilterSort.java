package ltm;

import wsobject.Customer;
import wsobject.ObjectService;
import wsobject.SoapObjectService;

import java.util.ArrayList;
import java.util.List;

public class WSCustomerFilterSort {
    private SoapObjectService service;

    private String studentCode = "B23DCCN682";
    private String qCode = "sbhUmW9V";

    public void connection() {
        ObjectService objectService = new ObjectService();
        service = objectService.getSoapObjectServicePort();
    }

    public List<Customer> receive() {
        return service.requestListCustomer(studentCode, qCode);
    }

    public List<Customer> solve(List<Customer> data) {
        List<Customer> result = new ArrayList<>();

        // Loc khach hang
        for (Customer customer : data) {
            if (customer.getPurchaseCount() >= 6
                    && customer.getTotalSpent() >= 4000) {
                result.add(customer);
            }
        }

        // Sap xep danh sach da loc
        result.sort((a, b) -> {
            int comparison = Float.compare(
                    b.getTotalSpent(),
                    a.getTotalSpent()
            );

            if (comparison != 0) {
                return comparison;
            }

            return a.getCustomerId().compareTo(b.getCustomerId());
        });

        return result;
    }

    public void send(List<Customer> result) {
        service.submitListCustomer(studentCode, qCode, result);
    }

    public static void main(String[] args) {
        WSCustomerFilterSort client = new WSCustomerFilterSort();

        try {
            client.connection();

            List<Customer> data = client.receive();

            System.out.println("Da nhan:");
            for (Customer customer : data) {
                System.out.println(
                        customer.getCustomerId()
                        + " | So lan mua: " + customer.getPurchaseCount()
                        + " | Tong chi tieu: " + customer.getTotalSpent()
                );
            }

            List<Customer> result = client.solve(data);

            System.out.println("Sau loc va sap xep:");
            for (Customer customer : result) {
                System.out.println(
                        customer.getCustomerId()
                        + " | " + customer.getTotalSpent()
                );
            }

            client.send(result);
            System.out.println("Da gui " + result.size() + " khach hang.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}