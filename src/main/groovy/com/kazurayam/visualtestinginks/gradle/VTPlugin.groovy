package com.kazurayam.visualtestinginks.gradle

import java.nio.file.Path

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.GroovyPlugin

class VTPlugin implements Plugin<Project> {

    private void applyPlugins(Project project) {
        project.getPluginManager().apply(GroovyPlugin.class)
        // https://github.com/michel-kraemer/gradle-download-task
        project.getPluginManager().apply('de.undercouch.download')
    }

    public void apply(Project project) {
        applyPlugins(project)

        // Add extension object
        VTPluginExtension extension = project.extensions.create("vt", VTPluginExtension)

        // Add a task 'greeting' that uses configuration from the extension object
        Task greeting              = project.task('greeting') {
            doLast {
                println extension.message
            }
        }

        // vt-build.gradle
        Task cleanDist             = project.getTasks().create('cleanDist') {}
        Task createGradlePackaged  = project.getTasks().create('createGradlePackaged') {}
        Task createVTComponentsZip = project.getTasks().create('createVTComponentsZip') {}
        Task createVTExampleZip    = project.getTasks().create('createVTExampleZip') {}
        Task distributables = project.getTasks().create('distributables')
        distributables.dependsOn(createGradlePackaged)
        distributables.dependsOn(createVTComponentsZip)
        distributables.dependsOn(createVTExampleZip)
        createGradlePackaged.mustRunAfter('cleanDist')
        createVTComponentsZip.mustRunAfter('cleanDist')
        createVTExampleZip.mustRunAfter('cleanDist')

        // vt-drivers.gradle
        Task deleteJarsInDriversDir = project.getTasks().create('deleteJarsInDriversDir') {}
        Task downloadJarsIntoDriversDir = project.getTasks().create('downloadJarsIntoDriversDir', DownloadJarsIntoDriversDirTask.class)

        Task drivers = project.getTasks().create('drivers')
        drivers.dependsOn(deleteJarsInDriversDir)
        drivers.dependsOn(downloadJarsIntoDriversDir)
        downloadJarsIntoDriversDir.mustRunAfter('deleteJarsInDriversDir')

        // vt-init.gradle
        Task unzipVTComponents = project.getTasks().create('unzipVTComponents') {}
        Task unzipVTExample    = project.getTasks().create('unzipVTExample') {}
        Task importVT = project.getTasks().create('importVT')
        importVT.dependsOn(unzipVTComponents)
        importVT.dependsOn(unzipVTExample)
    }

}
