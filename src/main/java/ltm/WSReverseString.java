package ltm;

import wscharacter.CharacterService;
import wscharacter.SoapCharacterService;

public class WSReverseString {
    private SoapCharacterService service;

    private String studentCode = "B23DCCN682";
    private String qCode = "J2wJt5VH";

    public void connection() {
        CharacterService characterService = new CharacterService();
        service = characterService.getSoapCharacterServicePort();
    }

    public String receive() {
        return service.requestString(studentCode, qCode);
    }

    public String solve(String data) {
        return new StringBuilder(data).reverse().toString();
    }

    public void send(String result) {
        service.submitString(studentCode, qCode, result);
    }

    public static void main(String[] args) {
        WSReverseString client = new WSReverseString();

        try {
            client.connection();

            String data = client.receive();
            System.out.println("Da nhan: " + data);

            String result = client.solve(data);

            client.send(result);
            System.out.println("Da gui: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}