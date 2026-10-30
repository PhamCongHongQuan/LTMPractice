package ltm;

import wsobject.Customer;
import wsobject.ObjectService;
import wsobject.SoapObjectService;

import java.util.ArrayList;
import java.util.List;

public class WSCustomerFilter {
    private SoapObjectService service;

    private String studentCode = "B23DCCN682";
    private String qCode = "4X240bxW";

    public void connection() {
        ObjectService objectService = new ObjectService();
        service = objectService.getSoapObjectServicePort();
    }

    public List<Customer> receive() {
        return service.requestListCustomer(studentCode, qCode);
    }

    public List<Customer> solve(List<Customer> data) {
    List<Customer> result = new ArrayList<>();

    for (Customer customer : data) {
        if (customer.getTotalSpent() > 500 && customer.getPurchaseCount() >=3) {
            result.add(customer);
        }
    }

    return result;
}

    public void send(List<Customer> result) {
        service.submitListCustomer(studentCode, qCode, result);
    }

    public static void main(String[] args) {
        WSCustomerFilter client = new WSCustomerFilter();

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

            System.out.println("Khach hang du dieu kien:");
            for (Customer customer : result) {
                System.out.println(customer.getCustomerId());
            }

            client.send(result);
            System.out.println("Da gui " + result.size() + " khach hang.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}