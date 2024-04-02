import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeStatistics {
    public static void calculateStatisticsForFiles(String[] fileNames) {
        for (String fileName : fileNames) {
            calculateStatistics(fileName);
        }
    }

    private static void calculateStatistics(String fileName) {
        Regex regex = new Regex();
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
                    if (line.trim().contains("/**")) {
                        inJavadocBlock = true;
                        //Solunda kod satırı olup olmadığını kontrol ediyor ve varsa codeLines'ı 1 arttırıyor
                        String regexForCodeLine = "^(?!\\s*\\/\\*\\*).*;$";
                        boolean isThereAnyCodeBeforeDoc = regex.Search(line, regexForCodeLine);
                        if (isThereAnyCodeBeforeDoc) {
                            codeLines++;
                        }

                        //İçerisinde bir şeyler yazan tek satır doc varsa javadocLines'ı 1 arttırıyor
                        String checkSingleLineDoc = "/\\*\\*\\s*\\*/";
                        boolean singleLineValidDocLine = regex.Search(line, checkSingleLineDoc);
                        if (singleLineValidDocLine) {
                            javadocLines++;
                        }

                        //İlk doclineda boşluk harici bir şeyler varsa  ve
                        //singleLine docline değilse doc varsa javadocLines'ı 1 arttırıyor
                        String checkForFirstLine = "/\\*\\*\\s+(?=\\S)";
                        boolean isThereAnyDocLineNearFirstLine = regex.Search(line, checkForFirstLine);
                        if (isThereAnyDocLineNearFirstLine && !singleLineValidDocLine) {
                            javadocLines++;
                        }
                    }
                    // Çok satırlı doclinesa buradan devam ediyor
                    else {
                        if (line.trim().contains("*/")) {
                            String checkIsItValidDoc = "(?<=\\S)\\*/";
                            boolean validLastLineDoc = regex.Search(line, checkIsItValidDoc);
                            if (validLastLineDoc) {
                                javadocLines++;
                            }
                            inJavadocBlock = false;
                        } else if (!line.equals("/**")) {
                            javadocLines++;
                        }
                    }
                }

                if (line.trim().contains("/*")) {
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