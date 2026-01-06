package com.github.ceredira.manager;

import com.github.ceredira.config.Config;
import com.github.ceredira.model.Repository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static com.github.ceredira.utils.FolderUtils.createFolder;
import static com.github.ceredira.utils.FolderUtils.deleteFolderWithContent;
import static com.github.ceredira.utils.YamlUtils.loadFromFile;
import static com.github.ceredira.utils.YamlUtils.saveToFile;

@Getter
@Setter
@Slf4j
// ToDo: потом убрать NoArgsConstructor
@NoArgsConstructor
public class RepositoryManager {

    // private Repository repository = (Repository) readFromYamlFile(new File("repositories.yaml"), Repository.class);
    private Map<String, Repository> repositories = new HashMap<>();

    private Path root = Config.getRootPathAsPath();

    public RepositoryManager(Path root) {
        this.root = root.resolve("repository");

        // Прочитать все существующие репозитории
        try (Stream<Path> stream = Files.list(this.root)) {
            stream.filter(Files::isDirectory) // Оставляем только каталоги
                    .filter(dir -> Files.exists(dir.resolve("repository.yaml"))) // Проверяем наличие файла
                    .forEach(dir -> {
                        Repository repo = loadFromFile(dir.resolve("repository.yaml").toFile(), Repository.class);
                        repositories.put(repo.getName(), repo);
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        // repositories = new HashMap<>();
        // ToDo: Добавить поиск существующих repository.yaml в каталогах по пути var/cpm/repository
    }

    public void addRepository(String repositoryName) {
        Repository repository = new Repository(repositoryName);

        repositories.put(repository.getName(), repository);
        syncWithFilesystem(repository);

        log.info("Локальный репозиторий {} добавлен", repositoryName);
    }

    public void addRepository(String repositoryName, Map<String, String> repositoryOptions) {
        Repository repository = new Repository(repositoryName, repositoryOptions);

        repositories.put(repository.getName(), repository);
        syncWithFilesystem(repository);

        log.info("Локальный репозиторий {} добавлен", repositoryName);
    }

    public void addRepository(String repositoryName, String remoteRepositoryUrl) {
        Repository repository = new Repository(repositoryName, remoteRepositoryUrl);

        repositories.put(repository.getName(), repository);
        syncWithFilesystem(repository);

        log.info("Удаленный репозиторий {} по адресу {} добавлен", repositoryName, remoteRepositoryUrl);
    }

    public void addRepository(String repositoryName, String remoteRepositoryUrl, Map<String, String> repositoryOptions) {
        Repository repository = new Repository(repositoryName, remoteRepositoryUrl, repositoryOptions);

        repositories.put(repository.getName(), repository);
        syncWithFilesystem(repository);

        log.info("Удаленный репозиторий {} по адресу {} добавлен", repositoryName, remoteRepositoryUrl);
    }

    public void removeRepository(String repositoryName) {
        if (repositoryName == null) {
            return;
        }

        Repository repository = repositories.get(repositoryName);

        if (repository == null) {
            log.warn("Репозиторий {} не найден. Удаление невозможно.", repositoryName);
            return;
        }

        Path repositoryRoot = root.resolve(repository.getName());
        deleteFolderWithContent(repositoryRoot);

        repositories.remove(repositoryName);

        log.info("Репозиторий {} удален", repositoryName);
    }

    public Set<String> list() {
        return repositories.keySet();
    }

    public String info(String repositoryName) {
        Repository repository = repositories.get(repositoryName);
        if (repository == null) {
            throw new IllegalArgumentException("Repository with name '" + repositoryName + "' not found");
        }
        return String.valueOf(repository);
    }

    public void update() {
        throw new RuntimeException("Не реализовано");
    }

    public void update(String repositoryName) {
        throw new RuntimeException("Не реализовано");
    }

    public void enable(String repositoryName) {
        Repository repository = repositories.get(repositoryName);
        repository.setEnabled(true);

        syncWithFilesystem(repository);
        log.info("Repository \"{}\" is enabled", repositoryName);
    }

    public void disable(String repositoryName) {
        Repository repository = repositories.get(repositoryName);
        repositories.get(repositoryName).setEnabled(false);

        syncWithFilesystem(repository);
        log.info("Repository \"{}\" is disabled", repositoryName);
    }

    private void syncWithFilesystem(Repository repository) {
        // Создать каталог с именем репозитория и yaml файлом с описанием репозитория в файловой системе
        Path repositoryRoot = root.resolve(repository.getName());
        createFolder(repositoryRoot.toFile());
        Path repositoryYaml = repositoryRoot.resolve("repository.yaml");
        saveToFile(repositoryYaml.toFile(), repository, Repository.class);
    }
}
