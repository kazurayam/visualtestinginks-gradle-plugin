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
        for (String sourceUrl: project.vt.dependencies) {
            String fileName = "${Constants.VT_EXTERNALLIBRARY_PREFIX}${sourceUrl.tokenize('/')[-1]}"
            File outFile = driversDir.resolve(fileName).toFile()
            project.download.configure({
                src sourceUrl
                dest outFile
            })
            println("downloaded ${sourceUrl} into ${driversDir} as ${fileName}")
        }
    }
    
    void diagnoze(String sourceURL, File outFile) {
        println "downloading sourceURL=${sourceURL} into ${outFile.toString()}"
    }
}
