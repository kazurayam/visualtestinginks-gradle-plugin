package com.kazurayam.visualtestinginks.gradle

import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

class DeleteDependenciesTask extends DefaultTask {

    @TaskAction
    void execute() {
        Project project = this.getProject()
        Path driversDir = project.projectDir.toPath().resolve('Drivers')
        List<Path> files = Files.list(driversDir).collect(Collectors.toList())
        for (Path file: files) {
            String fileName = file.getFileName().toString()
            if (fileName.startsWith(DownloadDependenciesTask.VT_DEPENDENCIES_PREFIX)) {
                Files.delete(file)
            }
        }
    }
}
