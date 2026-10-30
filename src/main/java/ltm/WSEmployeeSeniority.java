package ltm;

import wsobject.EmployeeY;
import wsobject.ObjectService;
import wsobject.SoapObjectService;

import java.util.ArrayList;
import java.util.List;

public class WSEmployeeSeniority {
    private SoapObjectService service;

    private String studentCode = "B23DCCN682";
    private String qCode = "IJu9mYlQ";

    public void connection() {
        ObjectService objectService = new ObjectService();
        service = objectService.getSoapObjectServicePort();
    }

    public List<EmployeeY> receive() {
        return service.requestListEmployeeY(studentCode, qCode);
    }

    public List<EmployeeY> solve(List<EmployeeY> data) {
        List<EmployeeY> result = new ArrayList<>(data);

        result.sort((a, b) -> {
            long timeA = a.getStartDate()
                    .toGregorianCalendar()
                    .getTimeInMillis();

            long timeB = b.getStartDate()
                    .toGregorianCalendar()
                    .getTimeInMillis();

            return Long.compare(timeA, timeB);
        });

        return result;
    }

    public void send(List<EmployeeY> result) {
        service.submitListEmployeeY(studentCode, qCode, result);
    }

    public static void main(String[] args) {
        WSEmployeeSeniority client = new WSEmployeeSeniority();

        try {
            client.connection();

            List<EmployeeY> data = client.receive();

            System.out.println("Truoc sap xep:");
            for (EmployeeY employee : data) {
                System.out.println(
                        employee.getName() + " | "
                        + employee.getStartDate()
                );
            }

            List<EmployeeY> result = client.solve(data);

            System.out.println("Sau sap xep:");
            for (EmployeeY employee : result) {
                System.out.println(
                        employee.getName() + " | "
                        + employee.getStartDate()
                );
            }

            client.send(result);
            System.out.println("Da gui ket qua.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}