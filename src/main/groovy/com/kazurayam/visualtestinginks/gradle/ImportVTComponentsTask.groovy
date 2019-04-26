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
        def extension = project.extensions.findByName("vt")
        if (extension == null) {
            extension = project.extensions.create("vt", VTPluginExtension)
        }
        String fileName = "${Constants.VT_DIST_COMPONENTS_PREFIX}-${project.vt.version}.zip"
        String sourceURL = "${project.vt.repositoryUrlPrefix}/${project.vt.version}/${fileName}"
        Path tempDir = Files.createTempDirectory(ImportVTComponentsTask.class.getSimpleName())
        File outFile = tempDir.resolve(fileName).toFile()
        diagnoze(sourceURL, outFile)
        project.download.configure({
            src sourceURL
            dest outFile
            overwrite true
        })
        project.copy {
            from project.zipTree(outFile)
            into project.projectDir
        }
        Helpers.deleteDirectory(tempDir)
    }
    
    void diagnoze(String sourceURL, File outFile) {
        println "downloading sourceURL=${sourceURL} into ${outFile.toString()}"
        println "System.getProperty('http.proxyHost')=${System.getProperty('http.proxyHost')}"
        println "System.getProperty('http.proxyPort')=${System.getProperty('http.proxyPort')}"
        println "System.getProperty('https.proxyHost')=${System.getProperty('https.proxyHost')}"
        println "System.getProperty('https.proxyPort')=${System.getProperty('https.proxyPort')}"
    }
}
