package ltm;

import wscharacter.CharacterService;
import wscharacter.SoapCharacterService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WSRotateRight {
    private SoapCharacterService service;

    private String studentCode = "B23DCCN682";
    private String qCode = "Blhnznkv";

    public void connection() {
        CharacterService characterService = new CharacterService();
        service = characterService.getSoapCharacterServicePort();
    }

    public List<Integer> receive() {
        return service.requestCharacter(studentCode, qCode);
    }

    public List<Integer> solve(List<Integer> data) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Danh sach rong");
        }

        // Phan tu dau tien la ma ASCII cua ky tu dau tien
        int shift = data.get(0) % data.size();

        // Sao chep danh sach nhan duoc
        List<Integer> result = new ArrayList<>(data);

        // Xoay sang phai shift vi tri
        Collections.rotate(result, shift);

        return result;
    }

    public void send(List<Integer> result) {
        service.submitCharacterCharArray(studentCode, qCode, result);
    }

    public static void main(String[] args) {
        WSRotateRight client = new WSRotateRight();

        try {
            client.connection();

            List<Integer> data = client.receive();
            System.out.println("Da nhan: " + data);

            List<Integer> result = client.solve(data);

            client.send(result);
            System.out.println("Da gui: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}