package com.kazurayam.visualtestinginks.gradle

import java.nio.file.Path

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

class DownloadJarsIntoDriversDirTask extends DefaultTask {

    @TaskAction
    void downloadDrivers() {
        Project project = this.getProject()
        List<String> urlList = [
            'http://central.maven.org/maven2/ru/yandex/qatools/ashot/ashot/1.5.4/ashot-1.5.4.jar',
            'http://central.maven.org/maven2/net/coobird/thumbnailator/0.4.8/thumbnailator-0.4.8.jar',
            'http://central.maven.org/maven2/org/apache/commons/commons-lang3/3.6/commons-lang3-3.6.jar',
            'http://central.maven.org/maven2/org/apache/commons/commons-math3/3.6.1/commons-math3-3.6.1.jar',
            'https://github.com/kazurayam/junit4ks/releases/download/1.6/junit4ks-all.jar',
            'https://github.com/kazurayam/ksbackyard/releases/download/0.36.0/ksbackyard.jar',
            'https://github.com/kazurayam/Materials/releases/download/0.68.0/Materials-0.68.0.jar'
            ]
        Path driversDir = project.projectDir.toPath().resolve('Drivers')
        for (String url: urlList) {
            project.download.configure {
                src url
                dest driversDir.resolve("${url.tokenize('/')[-1]}").toFile()
            }
            logger.info("downloaded ${url} into ${driversDir}")
        }
    }
}
