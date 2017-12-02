package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileToStringReader {
    static public String convertTextFileToSting(String filePath) {
        String textFormFile = "";

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }

            textFormFile = sb.toString();
        } catch (IOException e) {
            return "";
        }

        return !textFormFile.equals("") ? textFormFile : "";
    }
}
