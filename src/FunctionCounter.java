/**
 * @author Hakan CİVELEK - hakan.civelek2@ogr.sakarya.edu.tr
 * @since 07.04.2024
 * <p>
 * Bu sınıf fonksiyon sayisini hesaplar.
 * </p>
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FunctionCounter {
    int count = 0;

    public int counter(String fileName) throws FileNotFoundException {
        FunctionCounter functionCounter = new FunctionCounter();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String functionPattern = ".*\\b([a-zA-Z_$][a-zA-Z_$0-9]*)\\b\\s+([a-zA-Z_$][a-zA-Z_$0-9]*)\\s*\\(.*\\)\\s*\\{?\\s*$";
                if (line.matches(functionPattern)) {
                    functionCounter.count++;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return functionCounter.count;
    }
}
