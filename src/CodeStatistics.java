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

            // Dosya adını alma
            File file = new File(fileName);
            String fileSimpleName = file.getName();

            String line;
            while ((line = reader.readLine()) != null) {
                totalLines++;
                if (line.matches("^\\s*\\/\\*\\*.*")) {
                    // Javadoc satırlarını kontrol et
                    inCommentBlock = true;
                    javadocLines++;
                } else if (line.matches("^\\s*\\/\\/.*")) {
                    // Tek satırlık yorumları kontrol et
                    otherComments++;
                } else if (line.matches("^\\s*\\/\\*.*") && !line.matches("^\\s*\\/\\*\\*.*")) {
                    // Çok satırlı yorumları kontrol et
                    inCommentBlock = true;
                    otherComments++;
                } else if (inCommentBlock) {
                    // Çok satırlı yorum bloğunun sonunu kontrol et
                    if (line.matches(".*\\*\\/\\s*")) {
                        inCommentBlock = false;
                    }
                    javadocLines++;
                } else if (!line.trim().isEmpty()) {
                    // Kod satırlarını kontrol et
                    codeLines++;

                    // Fonksiyon tanımlarını kontrol et
                    if (line.matches(".*\\bvoid\\b.*\\(.*\\)\\s*\\{\\s*") ||
                            line.matches(".*\\bint\\b.*\\(.*\\)\\s*\\{\\s*") ||
                            line.matches(".*\\b\\w+\\b.*\\(.*\\)\\s*\\{\\s*")) {
                        functionCount++;
                    }
                }
            }

            javadocLines -= 4;
            // Yorum sapma yüzdesi hesapla
            double YG = ((javadocLines + otherComments) * 0.8) / functionCount;
            double YH = (double) (codeLines / functionCount) * 0.3;
            double commentDeviationPercentage = ((100 * YG) / YH) - 100;

            // Çıktıları yazdır
            System.out.println("Sınıf: " + fileSimpleName);
            System.out.println("Javadoc Satır Sayısı: " + javadocLines);
            System.out.println("Yorum Satır Sayısı: " + otherComments);
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