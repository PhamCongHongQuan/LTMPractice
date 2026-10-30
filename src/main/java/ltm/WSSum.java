package ltm;

import ws.DataService;
import ws.SoapDataService;

import java.util.List;

public class WSSum {
    private SoapDataService service;

    private String studentCode = "B23DCCN682";
    private String qCode = "VguH9uOp";

    public void connection() {
        DataService dataService = new DataService();
        service = dataService.getSoapDataServicePort();
    }

    public List<Integer> receive() {
        return service.getData(studentCode, qCode);
    }

    public int solve(List<Integer> data) {
        int sum = 0;

        for (int number : data) {
            sum += number;
        }

        return sum;
    }

    public void send(int result) {
        service.submitDataInt(studentCode, qCode, result);
    }

    public static void main(String[] args) {
        WSSum client = new WSSum();

        try {
            client.connection();

            List<Integer> data = client.receive();
            System.out.println("Da nhan: " + data);

            int result = client.solve(data);

            client.send(result);
            System.out.println("Da gui: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}