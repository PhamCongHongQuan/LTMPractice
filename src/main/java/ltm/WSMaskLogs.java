package ltm;

import wscharacter.CharacterService;
import wscharacter.SoapCharacterService;

import java.util.ArrayList;
import java.util.List;

public class WSMaskLogs {
    private SoapCharacterService service;

    private String studentCode = "B23DCCN682";
    private String qCode = "jgkaZyBn";

    public void connection() {
        CharacterService characterService = new CharacterService();
        service = characterService.getSoapCharacterServicePort();
    }

    public List<String> receive() {
        return service.requestStringArray(studentCode, qCode);
    }

    public int getLevel(String line) {
        String level = line.trim().split("\\s+", 2)[0];

        if (level.equals("ERROR")) {
            return 0;
        }

        if (level.equals("WARN")) {
            return 1;
        }

        if (level.equals("INFO")) {
            return 2;
        }

        return 3;
    }

    public List<String> solve(List<String> data) {
        List<String> result = new ArrayList<>();

        for (String line : data) {
            String masked = line
                    .replaceAll(
                            "(?<!\\S)email=\\S+",
                            "email=[EMAIL]"
                    )
                    .replaceAll(
                            "(?<!\\S)phone=\\S+",
                            "phone=[PHONE]"
                    )
                    .replaceAll(
                            "(?<!\\S)token=\\S+",
                            "token=[TOKEN]"
                    );

            result.add(masked);
        }

        result.sort((a, b) ->
                Integer.compare(getLevel(a), getLevel(b))
        );

        return result;
    }

    public void send(List<String> result) {
        service.submitStringArray(studentCode, qCode, result);
    }

    public static void main(String[] args) {
        WSMaskLogs client = new WSMaskLogs();

        try {
            client.connection();

            List<String> data = client.receive();

            List<String> result = client.solve(data);

            System.out.println("Cac dong sau xu ly:");
            for (String line : result) {
                System.out.println(line);
            }

            client.send(result);
            System.out.println("Da gui ket qua.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}