package ltm;

import wscharacter.CharacterService;
import wscharacter.SoapCharacterService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class WSGroupVowels {
    private SoapCharacterService service;

    private String studentCode = "B23DCCN682";
    private String qCode = "stKL7CMt";

    public void connection() {
        CharacterService characterService = new CharacterService();
        service = characterService.getSoapCharacterServicePort();
    }

    public List<String> receive() {
        return service.requestStringArray(studentCode, qCode);
    }

    public int countVowels(String word) {
        int count = 0;

        for (char c : word.toCharArray()) {
            c = Character.toLowerCase(c);

            if ("aeiou".indexOf(c) != -1) {
                count++;
            }
        }

        return count;
    }

    public List<String> solve(List<String> data) {
    Map<Integer, List<String>> groups = new TreeMap<>();

    for (String word : data) {
        int count = countVowels(word);

        if (!groups.containsKey(count)) {
            groups.put(count, new ArrayList<>());
        }

        groups.get(count).add(word);
    }

    List<String> result = new ArrayList<>();

    for (List<String> group : groups.values()) {
        Collections.sort(group);

        // Them tung tu vao danh sach ket qua
        result.addAll(group);
    }

    return result;
}

    public void send(List<String> result) {
        service.submitCharacterStringArray(
                studentCode, qCode, result
        );
    }

    public static void main(String[] args) {
        WSGroupVowels client = new WSGroupVowels();

        try {
            client.connection();

            List<String> data = client.receive();
            System.out.println("Da nhan: " + data);

            List<String> result = client.solve(data);

            // In rieng tung nhom de de quan sat
            for (String group : result) {
                System.out.println("Nhom: " + group);
            }

            client.send(result);
            System.out.println("Da gui ket qua.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}