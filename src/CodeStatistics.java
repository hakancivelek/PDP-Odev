/**
 * @author Hakan CİVELEK - hakan.civelek2@ogr.sakarya.edu.tr
 * @since 31.03.2024
 * <p>
 * Bu sınıf, belirli dosyaların kod istatistiklerini diger siniflari kullanarak hesaplar ve degerleri yazdirir.
 * </p>
 */

import java.io.*;

public class CodeStatistics {
    public static void calculateStatisticsForFiles(String[] fileNames) {
        for (String fileName : fileNames) {
            try {
                calculateStatistics(fileName);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void calculateStatistics(String fileName) throws FileNotFoundException {
        DocLineCounter docLine = new DocLineCounter();
        CommentCounter commentLine = new CommentCounter();
        LineCounter lineCounter = new LineCounter();
        FunctionCounter functionCounter = new FunctionCounter();
        int docLines;
        int commentLines;
        int codeLines;
        int totalLines;
        int functionCount;

        docLines = docLine.counter(fileName);
        commentLines = commentLine.counter(fileName);
        totalLines = lineCounter.counter(fileName);
        functionCount = functionCounter.counter(fileName);
        codeLines = totalLines - docLines - commentLines - lineCounter.emptyLine
                - docLine.emptyLine - commentLine.emptyLine + commentLine.codeLineFromSingleComment;

        // Yorum Sapma Yüzdesi Hesaplama
        double YG = ((docLines + commentLines) * 0.8) / functionCount;
        double YH = (codeLines * 1.0 / functionCount) * 0.3;
        double commentDeviationPercentage = ((100 * YG) / YH) - 100;
        commentDeviationPercentage = Math.round(commentDeviationPercentage * 100.0) / 100.0; // Ondalık 2 haneli olarak yuvarla

        File file = new File(fileName);
        String fileSimpleName = file.getName();
        // Çıktıları yazdır
        System.out.println("Sınıf: " + fileSimpleName);
        System.out.println("Javadoc Satır Sayısı: " + docLines);
        System.out.println("Yorum Satır Sayısı: " + commentLines);
        System.out.println("Kod Satırı Sayısı: " + codeLines);
        System.out.println("LOC: " + totalLines);
        System.out.println("Fonksiyon Sayısı: " + functionCount);
        System.out.println("Yorum Sapma Yüzdesi: % " + commentDeviationPercentage);
        System.out.println("----------------------------------");
    }
}
