package com.kazurayam.visualtestinginks.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project


public class PortingPlugin implements Plugin<Project> {

    public void apply(Project project) {

    	// Add the 'import' extension object
        ImportPluginExtensions importExt = project.extensions.create("import", ImportPluginExtensions);

        // Add a task that uses configuration from the extension object
        project.task('import') {
            doLast {
                println importExt.message
            }
        }

        // Add the 'export' extension object
        ExportPluginExtensions exportExt = project.extensions.create("export", ExportPluginExtensions);

        // Add a task that uses configuration from the extension object
        project.task('export') {
            doLast {
                println exportExt.message
            }
        }

    }
}
