package com.kazurayam.visualtestinginks.gradle

import java.nio.file.Files
import java.nio.file.Path

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

class ImportExternalLibrariesTask extends DefaultTask {


    @TaskAction
    void execute() {
        Project project = this.getProject()
        Path driversDir = project.projectDir.toPath().resolve('Drivers')
        Files.createDirectories(driversDir)
        for (String url: project.vt.dependencies) {
            String fileName = "${Constants.VT_EXTERNALLIBRARY_PREFIX}${url.tokenize('/')[-1]}"
            project.download.configure({
                src url
                dest driversDir.resolve(fileName).toFile()
            })
            //logger.info("downloaded ${url} into ${driversDir} as ${fileName}")
        }
    }

}
