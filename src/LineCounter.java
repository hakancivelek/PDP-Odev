/**
 * @author Hakan CİVELEK - hakan.civelek2@ogr.sakarya.edu.tr
 * @since 07.04.2024
 * <p>
 * Bu sınıf her şey dahil satir sayisini hesaplar.
 * </p>
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LineCounter {
    int count = 0;
    int emptyLine = 0;

    public int counter(String fileName) throws FileNotFoundException {
        emptyLine = 0;
        LineCounter lineCounter = new LineCounter();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
//                System.out.println("line: |" + line + "|");
                if (line.trim().isEmpty()) {
                    emptyLine++;
                }
                lineCounter.count++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lineCounter.count;
    }

}
