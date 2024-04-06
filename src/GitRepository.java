/**
 *
 * @author Hakan CİVELEK - hakan.civelek2@ogr.sakarya.edu.tr
 * @since 31.03.2024
 * <p>
 * Bu sınıf, bir Git deposunu temsil eder ve bu depo üzerinde çeşitli işlemleri gerçekleştirir.
 * Kullanıcının sağladığı depo URL'sini kullanarak depoyu klonlar ve ardından dosya işlemlerini başlatmak üzere FileProcessor sınıfını çağırır.
 * </p>
 */

import java.io.File;

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
            if (exitCode != 0) {
                System.out.println("Repository klonlanırken bir hata oluştu.\n");
            }
        } catch (Exception e) {
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