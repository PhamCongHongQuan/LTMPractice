package ltm;

import ws.DataService;
import ws.SoapDataService;

import java.util.ArrayList;
import java.util.List;

public class WSEvenOdd {
    private SoapDataService service;

    private String studentCode = "B23DCCN682";
    private String qCode = "f3SUhzSY";

    public void connection() {
        DataService dataService = new DataService();
        service = dataService.getSoapDataServicePort();
    }

    public List<Integer> receive() {
        return service.getData(studentCode, qCode);
    }

    public List<String> solve(List<Integer> data) {
        int even = 0;
        int odd = 0;

        for (int number : data) {
            if (number % 2 == 0) {
                even++;
            } else {
                odd++;
            }
        }

        List<String> result = new ArrayList<>();
        result.add("EVEN=" + even);
        result.add("ODD=" + odd);

        return result;
    }

    public void send(List<String> result) {
        service.submitDataStringArray(studentCode, qCode, result);
    }

    public static void main(String[] args) {
        WSEvenOdd client = new WSEvenOdd();

        try {
            client.connection();

            List<Integer> data = client.receive();
            System.out.println("Da nhan: " + data);

            List<String> result = client.solve(data);

            client.send(result);
            System.out.println("Da gui: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}