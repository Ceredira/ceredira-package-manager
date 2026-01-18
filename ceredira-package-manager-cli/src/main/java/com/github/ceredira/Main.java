package com.github.ceredira;

import com.github.ceredira.manager.RepositoryManager;
import com.github.ceredira.model.Repository;
import com.github.ceredira.model.RepositoryType;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

@Command(name = "cpm", mixinStandardHelpOptions = true,
        version = "1.0",
        description = "Ceredira package manager",
        subcommands = {Main.RepositoryGroup.class})
public class Main implements Callable<Integer> {

    private static final RepositoryManager repositoryManager = new RepositoryManager();

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        CommandLine.usage(this, System.out);
        return 0;
    }

    // --- ПРОМЕЖУТОЧНАЯ КОМАНДА 'repository' ---
    @Command(name = "repository",
            description = "Операции с репозиторием",
            subcommands = {
                    AddCmd.class,
                    RemoveCmd.class,
                    ListCmd.class,
                    EnableCmd.class,
                    DisableCmd.class,
                    InfoCmd.class
            })
    static class RepositoryGroup implements Runnable {
        @Override
        public void run() {
            // Если введено просто 'repository' без подкоманды
            CommandLine.usage(this, System.out);
        }
    }

    // --- ДЕЙСТВИЯ (ПОДКОМАНДЫ ДЛЯ REPOSITORY) ---

    @Command(name = "add", description = "Добавить в репозиторий")
    static class AddCmd implements Runnable {
        @Parameters(index = "0")
        String name;
        @Parameters(index = "1", arity = "0..1")
        String url;
        @Option(names = {"-b", "--branch"})
        String branch;
        @Option(names = {"-d", "--description"})
        String description;

        public void run() {
            if (url == null) {
                repositoryManager.addRepository(name);
            } else {
                repositoryManager.addRepository(name, url);
            }

            if (branch != null) {
                Map<String, String> options = new HashMap<>();
                options.put("branch", branch);

                repositoryManager.getRepositories().get(name).setProperties(options);
            }

            if (description != null) {
                repositoryManager.getRepositories().get(name).setDescription(description);
            }
        }
    }

    @Command(name = "remove", description = "Удалить из репозитория")
    static class RemoveCmd implements Runnable {
        @Parameters(index = "0")
        String name;

        public void run() {
            repositoryManager.removeRepository(name);
        }
    }

    @Command(name = "list", description = "Список объектов репозитория")
    static class ListCmd implements Runnable {
        public void run() {
            for (String repositoryName : repositoryManager.list()) {
                System.out.println("  * " + repositoryName);
            }
        }
    }

    @Command(name = "enable", description = "Активировать репозиторий")
    static class EnableCmd implements Runnable {
        @Parameters(index = "0")
        String name;

        public void run() {
            repositoryManager.getRepositories().get(name).setEnabled(true);
        }
    }

    @Command(name = "disable", description = "Деактивировать репозиторий")
    static class DisableCmd implements Runnable {
        @Parameters(index = "0")
        String name;

        public void run() {
            repositoryManager.getRepositories().get(name).setEnabled(false);
        }
    }

    @Command(name = "info", description = "Информация о репозитории")
    static class InfoCmd implements Runnable {
        @Parameters(index = "0")
        String name;

        public void run() {
            Repository repository = repositoryManager.getRepositories().get(name);
            System.out.println("** Репозиторий: " + repository.getName());
            System.out.println("  * Название: " + repository.getName());
            System.out.println("  * Описание: " + repository.getDescription());
            System.out.println("  * Тип: " + repository.getType());

            if (repository.getType().equals(RepositoryType.REMOTE)) {
                System.out.println("  * Адрес: " + repository.getUrl());
            }
        }
    }
}