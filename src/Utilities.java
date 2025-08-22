import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Utilities {

    public static String encrypt(String s, String key) {


        int shift = 0;

        for (int i = 0; i < key.length(); i++){
            shift += key.charAt(i);
        }
        shift %= 26;

        StringBuilder encrypted = new StringBuilder();

        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                c = (char) ((c - base + shift) % 26 + base);
            }
            encrypted.append(c);
        }

        return encrypted.toString();

    }

    public static String decrypt(String s, String key) {

        int shift = 0;

        for (int i = 0; i < key.length(); i++){
            shift += key.charAt(i);
        }
        shift %= 26;

        StringBuilder decrypted = new StringBuilder();

        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                c = (char) ((c - base - shift + 26) % 26 + base);
            }
            decrypted.append(c);
        }

        return decrypted.toString();

    }

    public static String generatePassword() {

        int length = (int) (Math.random() * 10 + 5);
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {

            int index = (int) (Math.random() * chars.length());
            password.append(chars.charAt(index));

        }

        return password.toString();
    }

    public static void date(String path){

        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String currentDate = localDate.format(formatter);

        try {
            List<String> lines = Files.readAllLines(Path.of(path));

            if (!lines.isEmpty()) {
                lines.set(0, currentDate);
            } else {
                lines.add(currentDate);
            }

            Files.write(Path.of(path), lines);


        } catch (IOException e) {
            System.out.println("Error-Utilities-date");
        }


    }

}
