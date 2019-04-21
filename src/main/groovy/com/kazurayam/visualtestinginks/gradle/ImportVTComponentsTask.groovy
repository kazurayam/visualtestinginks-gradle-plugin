package com.kazurayam.visualtestinginks.gradle

import java.nio.file.Files
import java.nio.file.Path

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

class ImportVTComponentsTask extends DefaultTask {

    @TaskAction
    void execute() {
        Project project = this.getProject()
        Path tempDir = Files.createTempDirectory(ImportVTComponentsTask.class.getSimpleName())
        File zipFile = tempDir.resolve(Constants.vtComponentsFileName).toFile()
        project.download.configure({
            src "${Constants.vcsUrlPrefix}/${project.vt.version}/${Constants.vtComponentsFileName}"
            dest zipFile
        })
        project.copy {
            from project.zipTree(zipFile)
            into project.projectDir
        }
        Helpers.deleteDirectories(tempDir)
    }
}
