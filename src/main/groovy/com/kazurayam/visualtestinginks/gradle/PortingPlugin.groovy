package com.kazurayam.visualtestinginks.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project


public class PortingPlugin implements Plugin<Project> {

    public void apply(Project project) {

        // Add extension object
        PortingPluginExtension extension = project.extensions.create("porting", PortingPluginExtension)

        // Add a task 'import' that uses configuration from the extension object
        project.task('import') {
            doLast {
                println extension.message
            }
        }

        // Add a task that uses configuration from the extension object
        project.task('export') {
            doLast {
                println extension.message
            }
        }

    }
}
