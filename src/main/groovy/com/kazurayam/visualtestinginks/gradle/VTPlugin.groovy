package com.kazurayam.visualtestinginks.gradle

import java.nio.file.Path

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.GroovyPlugin
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.bundling.Zip

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
                    archiveFileName = extension.gradlewFileName
                    destinationDirectory = project.file("${project.buildDir}/dist")
                    from(".") {
                        // include the gradle wrapper
                        include "gradlew"
                        include "gradlew.bat"
                        include "gradlewks.bat"   // customized launcher using Java bundled in %KATALONSTUDIO_Home%\jre
                        include "gradle/**/*"
                    }
                })
        Task createVTComponentsZip = project.getTasks().create(
                'createVTComponentsZip', Zip.class, {
                    archiveFileName = extension.vtComponentsFileName
                    destinationDirectory = project.file("${project.buildDir}/dist")
                    from(".") {
                        include "Test Cases/VT/**"
                        include "Scripts/VT/**"
                        include "Test Listeners/**/VT*"
                        include "Test Suites/VT/**"
                        include "Keywords/com/kazurayam/visualtesting/**"
                        include "run-console-mode.*"
                    }
                    from(".") {
                        include ".gitignore"
                    }
                })
        Task createVTExampleZip    = project.getTasks().create(
                'createVTExampleZip', Zip.class, {
                    archiveFileName = extension.vtExampleFileName
                    destinationDirectory = project.file("${project.buildDir}/dist")
                    from(".") {
                        include "Profiles/CURA*"
                        include "Test Cases/CURA/**"
                        include "Object Repository/CURA/**"
                        include "Test Suites/CURA/**"
                        include "Keywords/com/kazurayam/**"
                        include "Scripts/CURA/**"
                    }
                })
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
        Task importVTComponents = project.getTasks().create(
                'importVTComponents', ImportVTComponentsTask.class)
        Task importVTExample    = project.getTasks().create(
                'importVTExample', ImportVTExampleTask.class)
        Task importVT = project.getTasks().create(
                'importVT')
        importVT.dependsOn(importVTComponents)
        importVT.dependsOn(importVTExample)

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
