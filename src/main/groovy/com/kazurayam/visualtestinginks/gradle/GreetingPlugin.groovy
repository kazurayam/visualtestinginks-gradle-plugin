package com.kazurayam.visualtestinginks.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

public class GreetingPlugin implements Plugin<Project> {
    public void apply(Project project) {
        // Add the 'greeting' extension object
        GreetingPluginExtension extension = project.extensions.create("greeting", GreetingPluginExtension);

        // Add a task that uses configuration from the extension object
        //project.getTasks().create("hello", Greeting.class, (task) -> {
        //    task.setMessage("Hello");
        //    task.setRecipient("World");
        //});
        project.task('hello') {
            doLast {
                println extension.message
            }
        }
    }
}
