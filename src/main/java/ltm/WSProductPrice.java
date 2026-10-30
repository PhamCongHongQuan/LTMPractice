package ltm;

import wsobject.ObjectService;
import wsobject.ProductY;
import wsobject.SoapObjectService;

public class WSProductPrice {
    private SoapObjectService service;

    private String studentCode = "B23DCCN682";
    private String qCode = "UtXzfzw9";

    public void connection() {
        ObjectService objectService = new ObjectService();
        service = objectService.getSoapObjectServicePort();
    }

    public ProductY receive() {
        return service.requestProductY(studentCode, qCode);
    }

    public void solve(ProductY product) {
        float price = product.getPrice();
        float taxRate = product.getTaxRate();
        float discount = product.getDiscount();

        float finalPrice = price
                * (1 + taxRate / 100f)
                * (1 - discount / 100f);

        product.setFinalPrice(finalPrice);
    }

    public void send(ProductY product) {
        service.submitProductY(studentCode, qCode, product);
    }

    public static void main(String[] args) {
        WSProductPrice client = new WSProductPrice();

        try {
            client.connection();

            ProductY product = client.receive();

            System.out.println("Gia ban dau: " + product.getPrice());
            System.out.println("Thue (%): " + product.getTaxRate());
            System.out.println("Giam gia (%): " + product.getDiscount());

            client.solve(product);

            System.out.println("Gia cuoi: " + product.getFinalPrice());

            client.send(product);
            System.out.println("Da gui san pham.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}