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

        // managing jar files in the Drivers directory
        Task deleteDependencies = project.getTasks().create('deleteDependencies', DeleteDependenciesTask.class)
        Task downloadDependencies = project.getTasks().create('downloadDependencies', DownloadDependenciesTask.class)
        Task vtDependencies = project.getTasks().create('vtDependencies')
        vtDependencies.dependsOn(deleteDependencies)
        vtDependencies.dependsOn(downloadDependencies)
        downloadDependencies.mustRunAfter('deleteDependencies')

        // vt-init.gradle
        Task unzipVTComponents = project.getTasks().create('unzipVTComponents') {}
        Task unzipVTExample    = project.getTasks().create('unzipVTExample') {}
        Task importVT = project.getTasks().create('importVT')
        importVT.dependsOn(unzipVTComponents)
        importVT.dependsOn(unzipVTExample)
    }

}
