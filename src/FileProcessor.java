import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileProcessor {
    public void processFiles(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    processFiles(f);
                }
            }
        } else if (file.isFile() && file.getName().endsWith(".java")) {
            if (containsClass(file)) {
                // Java dosyasında en az bir sınıf tanımı bulunuyorsa dosyanın adını yazdır
                System.out.println("Sınıf dosyası: " + file.getName());

                // CodeStatistics sınıfını kullanarak istatistikleri hesapla
                CodeStatistics.calculateStatisticsForFiles(new String[]{file.getAbsolutePath()});
            }
        }
    }

    private boolean containsClass(File file) {
        // Java dosyasının içeriğinde "class " ifadesi aranarak sınıf tanımı kontrol edilir
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Yorum satırlarını ve Java doc'ları kontrol et
                if (line.matches("^\\s*\\/\\/.*") || line.matches("^\\s*\\/\\*.*") || line.matches("^\\s*\\*.*")) {
                    // Tek satırlık yorum veya çok satırlı yorum
                    continue;
                }
                if (line.matches("^\\s*\\*\\/.*")) {
                    // Yorumun sonu
                    continue;
                }
                if (line.matches(".*\\bclass\\b.*")) {
                    // "class" kelimesini içeren satırların işlenmesi
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}