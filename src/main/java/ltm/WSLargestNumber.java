package ltm;

import ws.DataService;
import ws.SoapDataService;

import java.util.ArrayList;
import java.util.List;

public class WSLargestNumber {
    private SoapDataService service;

    private String studentCode = "B23DCCN682";
    private String qCode = "ZVDH0ygY";

    public void connection() {
        DataService dataService = new DataService();
        service = dataService.getSoapDataServicePort();
    }

    public List<Integer> receive() {
        return service.getData(studentCode, qCode);
    }

    public String solve(List<Integer> data) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Danh sach rong");
        }

        // Chuyen tung so thanh chuoi
        List<String> numbers = new ArrayList<>();

        for (int number : data) {
            numbers.add(String.valueOf(number));
        }

        // Sap xep theo thu tu ghep tao ra so lon nhat
        numbers.sort((a, b) -> (b + a).compareTo(a + b));

        // Truong hop tat ca cac so deu bang 0
        if (numbers.get(0).equals("0")) {
            return "0";
        }

        // Gheп cac so, khong them dau cach hay dau phay
        StringBuilder result = new StringBuilder();

        for (String number : numbers) {
            result.append(number);
        }

        return result.toString();
    }

    public void send(String result) {
        service.submitDataString(studentCode, qCode, result);
    }

    public static void main(String[] args) {
        WSLargestNumber client = new WSLargestNumber();

        try {
            client.connection();

            List<Integer> data = client.receive();
            System.out.println("Da nhan: " + data);

            String result = client.solve(data);

            client.send(result);
            System.out.println("Da gui: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}