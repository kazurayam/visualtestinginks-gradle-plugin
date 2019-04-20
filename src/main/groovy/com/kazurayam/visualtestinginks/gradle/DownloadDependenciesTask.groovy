package com.kazurayam.visualtestinginks.gradle

import java.nio.file.Path

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

class DownloadDependenciesTask extends DefaultTask {

    static final String VT_DEPENDENCIES_PREFIX = 'vt-'

    @TaskAction
    void execute() {
        Project project = this.getProject()
        Path driversDir = project.projectDir.toPath().resolve('Drivers')
        for (String url: project.vt.dependencies) {
            String fileName = "${VT_DEPENDENCIES_PREFIX}${url.tokenize('/')[-1]}"
            project.download.configure({
                src url
                dest driversDir.resolve(fileName).toFile()
            })
            //logger.info("downloaded ${url} into ${driversDir} as ${fileName}")
        }
    }
}
