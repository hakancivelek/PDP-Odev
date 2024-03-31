/**
 *
 * @author Hakan CİVELEK ve mail
 * @since 31.03.2024
 * <p>
 * * Bu sınıf, GitHub deposunun URL'sini kullanıcıdan alır ve ilgili işlemleri gerçekleştirir.
 * * Kullanıcıdan alınan URL ile bir Git deposu oluşturur, bu depoyu klonlar ve ardından dosya işlemlerini başlatır.
 * </p>
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RepositoryManager {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("GitHub Repository URL giriniz:");
        String repositoryUrl = null;
        try {
            repositoryUrl = reader.readLine().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (repositoryUrl != null && !repositoryUrl.isEmpty()) {
            GitRepository repository = new GitRepository(repositoryUrl);
            repository.cloneRepository();
            repository.processFiles();
        } else {
            System.out.println("Geçersiz Repository URL.");
        }
    }
}