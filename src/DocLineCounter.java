/**
 * @author Hakan CİVELEK - hakan.civelek2@ogr.sakarya.edu.tr
 * @since 07.04.2024
 * <p>
 * Bu sınıf docline satir sayisini hesaplar.
 * </p>
 */

import java.io.*;

public class DocLineCounter {
    int count = 0;
    boolean inJavadocBlock = false;
    int emptyLine = 0;

    public int counter(String fileName) throws FileNotFoundException {
        DocLineCounter docLineCounter = new DocLineCounter();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (inJavadocBlock) {
                    if (line.trim().endsWith("*/")) {
                        if (line.trim().length() > 3) { // solunda boşluk hariç herhangi bir deger varsa count++;
                            docLineCounter.count++;
                        } else {
                            emptyLine++;
                        }
                        inJavadocBlock = false;
                    } else {
                        docLineCounter.count++;
                    }
                } else if (line.trim().startsWith("/**")) {
                    inJavadocBlock = true;
                    if (line.trim().length() > 4) { // saginda boşluk hariç herhangi bir deger varsa count++;
                        docLineCounter.count++;
                    } else {
                        emptyLine++;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return docLineCounter.count;
    }
}
