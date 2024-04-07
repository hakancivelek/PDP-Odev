/**
 * @author Hakan CİVELEK - hakan.civelek2@ogr.sakarya.edu.tr
 * @since 07.04.2024
 * <p>
 * Bu sınıf tekli ve çoklu yorum satir sayisinin toplamını hesaplar.
 * </p>
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CommentCounter {
    int count = 0;
    boolean inCommentBlock = false;
    int emptyLine = 0;
    int codeLineFromSingleComment = 0; // sout("test") // geçerli bir kodun yanında yorum varsa onu alıyoruz

    public int counter(String fileName) throws FileNotFoundException {
        CommentCounter commentLineCounter = new CommentCounter();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            codeLineFromSingleComment = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                if (inCommentBlock) {
                    if (line.trim().endsWith("*/")) {
                        if (line.trim().length() > 3) { // solunda boşluk hariç herhangi bir deger varsa count++;
                            commentLineCounter.count++;
                        } else {
                            emptyLine++;
                        }
                        inCommentBlock = false;
                    } else {
                        commentLineCounter.count++;
                    }
                } else if (line.trim().startsWith("/*") && !line.trim().startsWith("/**")) {
                    inCommentBlock = true;
                    if (line.trim().length() > 3) { // saginda boşluk hariç herhangi bir deger varsa count++;
                        commentLineCounter.count++;
                    } else {
                        emptyLine++;
                    }
                } else if (line.trim().contains("//")) {
                    int index = line.indexOf("//");

                    if (index != -1 && !line.substring(0, index).trim().isEmpty()) {
                        codeLineFromSingleComment++;
                    }
                    commentLineCounter.count++;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return commentLineCounter.count;
    }
}
