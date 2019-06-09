package com.kazurayam.visualtestinginks.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.GroovyPlugin
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
        def extension = project.extensions.create("vt", VTPluginExtension)

        String DIST = "${project.buildDir}/dist"

        // ---------------------------------------------------------------------------
        // tasks which import distributables into the people's VisualTesting projects
        Task importVTComponents = project.getTasks().create(
                'importVTComponents', ImportVTComponentsTask.class)
        Task importVTExample    = project.getTasks().create(
                'importVTExample', ImportVTExampleTask.class)

        // -------------------------------------------------------------------------
        // task which enables a new Katalon Project of VisualTesting
        Task enableVisualTesting = project.getTasks().create(
                'enableVisualTesting')
        enableVisualTesting.dependsOn(importVTComponents)
        enableVisualTesting.dependsOn(importVTExample)



        // for DEBUG: Add a task 'greeting' that uses configuration from the extension object
        Task greeting = project.task('greeting') {
            doLast {
                println extension.message
            }
        }
    }
}
