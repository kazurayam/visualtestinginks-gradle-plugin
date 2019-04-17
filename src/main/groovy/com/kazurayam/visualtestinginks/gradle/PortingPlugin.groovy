package com.kazurayam.visualtestinginks.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.GroovyPlugin

class PortingPlugin implements Plugin<Project> {

    private void applyPlugins(Project project) {
        project.getPlugins().apply(GroovyPlugin.class)
    }

    public void apply(Project project) {
        applyPlugins(project)

        // Add extension object
        PortingPluginExtension extension = project.extensions.create("porting", PortingPluginExtension)

        // Add a task 'greeting' that uses configuration from the extension object
        project.task('greeting') {
            doLast {
                println extension.message
            }
        }
        Task cleanDist             = project.getTasks().create('cleanDist', CleanDist.class)
        Task createGradlePackaged  = project.getTasks().create('createGradlePackaged', CreateGradlePackaged)
        Task createVTComponentsZip = project.getTasks().create('createVTComponentsZip', CreateVTComponentsZip)
        Task createVTExampleZip    = project.getTasks().create('createVTExampleZip', CreateVTExampleZip)
        Task distributables = project.getTasks().create('distributables')
        distributables.dependsOn(createGradlePackaged)
        distributables.dependsOn(createVTComponentsZip)
        distributables.dependsOn(createVTExampleZip)
        createGradlePackaged.mustRunAfter('cleanDist')
        createVTComponentsZip.mustRunAfter('cleanDist')
        createVTExampleZip.mustRunAfter('cleanDist')
    }

}