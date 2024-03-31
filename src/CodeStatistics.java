/**
 *
 * @author Hakan CİVELEK ve mail
 * @since 31.03.2024
 * <p>
 * Bu sınıf, belirtilen .java dosyaları için istatistikler hesaplar.
 * Her dosyanın içeriğini okur, dosyadaki kod, yorum satırları ve Java doc satırlarını sayar,
 * ayrıca dosyadaki fonksiyonların sayısını hesaplar.
 * </p>
 * <p>
 * Bu sınıfın kullanımı için, calculateStatisticsForFiles metoduna .java dosyalarının tam yollarını içeren bir dizi gönderilmelidir.
 * Her dosya için ayrı ayrı istatistikler hesaplanır ve konsola yazdırılır.
 * </p>
 *
 * <p>
 * Not: Dosyaların istatistiklerinin doğru bir şekilde hesaplanabilmesi için dosyaların tam yolunun belirtilmesi önemlidir.
 * </p>
 */

import java.io.*;

public class CodeStatistics {
    public static void calculateStatisticsForFiles(String[] fileNames) {
        for (String fileName : fileNames) {
            calculateStatistics(fileName);
        }
    }

    private static void calculateStatistics(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int javadocLines = 0;
            int otherComments = 0;
            int codeLines = 0;
            int totalLines = 0;
            int functionCount = 0;
            boolean inCommentBlock = false;

            String line;
            while ((line = reader.readLine()) != null) {
                totalLines++;
                if (line.trim().startsWith("/**") || line.trim().startsWith("/*")) {
                    inCommentBlock = true;
                    javadocLines++;
                } else if (line.trim().startsWith("//")) {
                    otherComments++;
                } else if (inCommentBlock) {
                    if (line.trim().endsWith("*/")) {
                        inCommentBlock = false;
                    }
                    javadocLines++;
                } else if (line.trim().isEmpty()) {
                    // Skip empty lines
                } else {
                    codeLines++;
                }

                if (line.contains("(") && line.contains(")") && line.contains("{")) {
                    functionCount++;
                }
            }

            System.out.println("Dosya Adı: " + fileName);
            System.out.println("Javadoc Satır Sayısı: " + javadocLines);
            System.out.println("Diğer Yorum Satırı Sayısı: " + otherComments);
            System.out.println("Kod Satırı Sayısı: " + codeLines);
            System.out.println("Toplam Satır Sayısı: " + totalLines);
            System.out.println("Fonksiyon Sayısı: " + functionCount);
            System.out.println("----------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}