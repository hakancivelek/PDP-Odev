/**
 *
 * @author Hakan CİVELEK - hakan.civelek2@ogr.sakarya.edu.tr
 * @since 31.03.2024
 * <p>
 * Bu sınıf, belirli dosyaların kod istatistiklerini hesaplar.
 * </p>
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CodeStatistics {
    public static void calculateStatisticsForFiles(String[] fileNames) {
        for (String fileName : fileNames) {
            calculateStatistics(fileName);
        }
    }

    private static void calculateStatistics(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int javadocLines = 0;
            int commentLines = 0;
            int codeLines = 0;
            int totalLines = 0;
            int functionCount = 0;

            boolean inJavadocBlock = false;
            boolean inMultiLineCommentBlock = false;

            File file = new File(fileName);
            String fileSimpleName = file.getName();

            String line;
            while ((line = reader.readLine()) != null) {
                totalLines++;

                if (inJavadocBlock) {
                    if (line.trim().endsWith("*/")) {
                        if (line.trim().length() > 3) {
                            javadocLines++;
                        }
                        inJavadocBlock = false;
                    } else {
                        javadocLines++;
                    }
                } else if (line.trim().startsWith("/**")) {
                    inJavadocBlock = true;
                    if (line.trim().length() > 4) {
                        javadocLines++;
                    }
                } else if (line.trim().startsWith("/*")) {
                    inMultiLineCommentBlock = true;
                    if (!line.trim().endsWith("*/")) {
                        // Yorum bloğu bitene kadar devam eder
                        while ((line = reader.readLine()) != null) {
                            totalLines++;
                            if (line.trim().startsWith("/*") && line.trim().length() > 3) {
                                commentLines++;
                            } else if (line.trim().endsWith("*/")) {
                                if (line.trim().length() > 3) {
                                    commentLines++;
                                }
                                inMultiLineCommentBlock = false;
                                break;
                            } else {
                                commentLines++;
                            }
                        }
                    }
                } else if (line.contains("//")) {
                    // Tek satırlık yorumu say
                    String[] splitLine = line.split("//");
                    String codePart = splitLine[0].trim();
                    if (!codePart.isEmpty()) {
                        codeLines++;
                        // Fonksiyon tanımlarını kontrol et
                        String functionPattern = ".*\\b([a-zA-Z_$][a-zA-Z_$0-9]*)\\b\\s+([a-zA-Z_$][a-zA-Z_$0-9]*)\\s*\\(.*\\)\\s*\\{?\\s*$";
                        if (codePart.matches(functionPattern)) {
                            functionCount++;
                        }
                    }
                    commentLines++;
                } else if (!inMultiLineCommentBlock && !line.trim().isEmpty() && !line.trim().startsWith("//")) {
                    // Yorum içinde değilse ve boş satır değilse ve tek satırlık yorum satırı değilse kod satırıdır
                    codeLines++;
                    // Fonksiyon tanımlarını kontrol et
                    String functionPattern = ".*\\b([a-zA-Z_$][a-zA-Z_$0-9]*)\\b\\s+([a-zA-Z_$][a-zA-Z_$0-9]*)\\s*\\(.*\\)\\s*\\{?\\s*$";
                    if (line.matches(functionPattern)) {
                        functionCount++;
                    }
                }
            }

            // Yorum Sapma Yüzdesi Hesaplama
            double YG = ((javadocLines + commentLines) * 0.8) / functionCount;
            double YH = (codeLines * 1.0 / functionCount) * 0.3;
            double commentDeviationPercentage = ((100 * YG) / YH) - 100;
            commentDeviationPercentage = Math.round(commentDeviationPercentage * 100.0) / 100.0; // Ondalık 2 haneli olarak yuvarla

            // Çıktıları yazdır
            System.out.println("Sınıf: " + fileSimpleName);
            System.out.println("Javadoc Satır Sayısı: " + javadocLines);
            System.out.println("Yorum Satır Sayısı: " + commentLines);
            System.out.println("Kod Satırı Sayısı: " + codeLines);
            System.out.println("LOC: " + totalLines);
            System.out.println("Fonksiyon Sayısı: " + functionCount);
            System.out.println("Yorum Sapma Yüzdesi: % " + commentDeviationPercentage);
            System.out.println("----------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
