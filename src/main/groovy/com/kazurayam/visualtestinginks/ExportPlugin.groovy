package com.kazurayam.visualtestinginks

import org.gradle.api.Plugin
import org.gradle.api.Project

public class ExportPlugin implements Plugin<Project> {

    public void apply(Project project) {
    
        // Add the 'export' extension object
        ExportPluginExtension extension = project.extensions.create("export", ExportPluginExtension);

        // Add a task that uses configuration from the extension object
        project.task('export') {
            doLast {
                println extension.message
            }
        }
    }
}
