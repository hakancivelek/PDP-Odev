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