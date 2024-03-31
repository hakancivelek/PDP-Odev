/**
 *
 * @author Hakan CİVELEK ve mail
 * @since 31.03.2024
 * <p>
 * Bu sınıf, bir Git deposunu temsil eder ve bu depo üzerinde çeşitli işlemleri gerçekleştirir.
 * Kullanıcının sağladığı depo URL'sini kullanarak depoyu klonlar ve ardından dosya işlemlerini başlatmak üzere FileProcessor sınıfını çağırır.
 * </p>
 */

import java.io.File;
import java.io.IOException;

public class GitRepository {
    private String repositoryUrl;

    public GitRepository(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public void cloneRepository() {
        String cloneCommand = "git clone " + repositoryUrl;
        try {
            Process process = Runtime.getRuntime().exec(cloneCommand);
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Repository başarıyla klonlandı.");
            } else {
                System.out.println("Repository klonlanırken bir hata oluştu.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void processFiles() {
        File projectDirectory = new File(getRepositoryName());
        FileProcessor processor = new FileProcessor();
        processor.processFiles(projectDirectory);
    }

    private String getRepositoryName() {
        String repositoryName = repositoryUrl.substring(repositoryUrl.lastIndexOf('/') + 1);
        return repositoryName.replaceAll(".git$", "");
    }
}