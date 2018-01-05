package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LineReader {
    static String getLine() throws IOException{
        String line = "";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        line = bufferedReader.readLine();
        return line;
    }
}
