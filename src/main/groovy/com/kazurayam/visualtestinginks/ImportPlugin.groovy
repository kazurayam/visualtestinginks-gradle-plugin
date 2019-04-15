package com.kazurayam.visualtestinginks

import org.gradle.api.Plugin
import org.gradle.api.Project

public class ImportPlugin implements Plugin<Project> {
    public void apply(Project project) {
        // Add the 'import' extension object
        ExportPluginExtension extension = project.extensions.create("import", ExportPluginExtension);

        // Add a task that uses configuration from the extension object
        project.task('import') {
            doLast {
                println extension.message
            }
        }
    }
}
