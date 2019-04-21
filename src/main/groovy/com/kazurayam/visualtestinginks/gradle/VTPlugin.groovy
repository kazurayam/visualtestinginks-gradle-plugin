package com.kazurayam.visualtestinginks.gradle

import java.nio.file.Path

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.GroovyPlugin
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.Zip

class VTPlugin implements Plugin<Project> {

    private void applyPlugins(Project project) {
        project.getPluginManager().apply(GroovyPlugin.class)
        // https://github.com/michel-kraemer/gradle-download-task
        project.getPluginManager().apply('de.undercouch.download')
    }

    /**
     *
     */
    public void apply(Project project) {

        // apply another plugins that VTPlugin depends upon
        applyPlugins(project)

        // Extension object
        VTPluginExtension extension = project.extensions.create("vt", VTPluginExtension)

        // tasks to generate VisualTesting distributables
        Task createGradlePackaged  = project.getTasks().create(
                'createGradlePackaged', Zip.class, {
                    archiveFileName = extension.vt.gradlewFileName
                    desticationDirectory = file("${project.buildDir}/dist")
                    from(".") {
                        // include the gradle wrapper
                        include "gradlew"
                        include "gradlew.bat"
                        include "gradlewks.bat"   // customized launcher using Java bundled in %KATALONSTUDIO_Home%\jre
                        include "gradle/**/*"
                    }
                })
        Task createVTComponentsZip = project.getTasks().create(
                'createVTComponentsZip') {}
        Task createVTExampleZip    = project.getTasks().create(
                'createVTExampleZip') {}
        Task cleanDist = project.getTasks().create(
                'cleanDist', Delete.class, {
                    def dirName = "build/dist"
                    project.file( dirName ).list().each { f ->
                        delete "${dirName}/${f}"
                    }
                })
        Task distributables = project.getTasks().create(
                'distributables')
        distributables.dependsOn(createGradlePackaged)
        distributables.dependsOn(createVTComponentsZip)
        distributables.dependsOn(createVTExampleZip)
        createGradlePackaged.mustRunAfter('cleanDist')
        createVTComponentsZip.mustRunAfter('cleanDist')
        createVTExampleZip.mustRunAfter('cleanDist')

        // tasks to import distributables into the people's VisualTesting projects
        Task unzipVTComponents = project.getTasks().create(
                'unzipVTComponents') {}
        Task unzipVTExample    = project.getTasks().create(
                'unzipVTExample') {}
        Task importVT = project.getTasks().create('importVT')
        importVT.dependsOn(unzipVTComponents)
        importVT.dependsOn(unzipVTExample)

        // tasks to manage jar files in the Drivers directory in the people's VisualTesting projects
        Task deleteDependencies = project.getTasks().create(
                'deleteDependencies', DeleteDependenciesTask.class)
        Task downloadDependencies = project.getTasks().create(
                'downloadDependencies', DownloadDependenciesTask.class)
        Task vtDependencies = project.getTasks().create(
                'vtDependencies')
        vtDependencies.dependsOn(deleteDependencies)
        vtDependencies.dependsOn(downloadDependencies)
        downloadDependencies.mustRunAfter('deleteDependencies')


        // Add a task 'greeting' that uses configuration from the extension object
        Task greeting = project.task('greeting') {
            doLast {
                println extension.message
            }
        }
    }
}
