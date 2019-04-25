package com.kazurayam.visualtestinginks.gradle

import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

class DeleteExternalLibrariesTask extends DefaultTask {

    @TaskAction
    void execute() {
        Project project = this.getProject()
        Path driversDir = project.projectDir.toPath().resolve('Drivers')
        if (Files.exists(driversDir)) {
            List<Path> files = Files.list(driversDir).collect(Collectors.toList())
            for (Path file: files) {
                String fileName = file.getFileName().toString()
                if (fileName.startsWith(Constants.VT_EXTERNALLIBRARY_PREFIX)) {
                    Files.delete(file)
                }
            }
        }
    }
}
