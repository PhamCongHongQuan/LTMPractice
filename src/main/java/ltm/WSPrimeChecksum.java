package ltm;

import ws.DataService;
import ws.SoapDataService;

import java.util.List;

public class WSPrimeChecksum {
    private SoapDataService service;

    private String studentCode = "B23DCCN682";
    private String qCode = "xEpCmL3l";

    public void connection() {
        DataService dataService = new DataService();
        service = dataService.getSoapDataServicePort();
    }

    public List<Integer> receive() {
        return service.getData(studentCode, qCode);
    }

    public boolean isPrime(int number) {
        if (number < 2) {
            return false;
        }

        for (int divisor = 2;
                (long) divisor * divisor <= number;
                divisor++) {

            if (number % divisor == 0) {
                return false;
            }
        }

        return true;
    }

    public String solve(List<Integer> data) {
        int primeCount = 0;
        long checksum = 0;

        for (int i = 0; i < data.size(); i++) {
            int number = data.get(i);

            if (isPrime(number)) {
                primeCount++;
            }

            checksum = Math.floorMod(
                    checksum + (i + 1L) * number,
                    100000L
            );
        }

        return "primeCount=" + primeCount
                + ";checksum=" + checksum;
    }

    public void send(String result) {
        service.submitDataString(studentCode, qCode, result);
    }

    public static void main(String[] args) {
        WSPrimeChecksum client = new WSPrimeChecksum();

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