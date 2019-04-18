package com.kazurayam.visualtestinginks.gradle

import java.nio.file.Path

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.GroovyPlugin

class VTPlugin implements Plugin<Project> {

    private void applyPlugins(Project project) {
        project.getPlugins().apply(GroovyPlugin.class)

	// https://github.com/michel-kraemer/gradle-download-task
	project.getPlugins().apply('de.undercouch.download')
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

        // vt-drivers.gradle
	List<String> urlList = [
	    'http://central.maven.org/maven2/ru/yandex/qatools/ashot/ashot/1.5.4/ashot-1.5.4.jar',
	    'http://central.maven.org/maven2/net/coobird/thumbnailator/0.4.8/thumbnailator-0.4.8.jar',
      	    'http://central.maven.org/maven2/org/apache/commons/commons-lang3/3.6/commons-lang3-3.6.jar',
    	    'http://central.maven.org/maven2/org/apache/commons/commons-math3/3.6.1/commons-math3-3.6.1.jar',
	    'https://github.com/kazurayam/junit4ks/releases/download/1.6/junit4ks-all.jar',
	    'https://github.com/kazurayam/ksbackyard/releases/download/0.36.0/ksbackyard.jar',
	    'https://github.com/kazurayam/Materials/releases/download/0.68.0/Materials-0.68.0.jar'
	    ]

	Path outDir = project.projectDir.toPath().resolve('Drivers')
	
	Task deleteJarsInDriversDir     = project.getTasks().create('deleteJarsInDriversDir', DeleteJarsInDriversDir.class)

        Task downloadJarsIntoDriversDir = project.getTasks().create('downloadJarsIntoDriversDir') {
            doLast {    	
		for (String url: urlList) {
		    // download extension is provided by the community plugin "de.undercouch.download"
                    download {
                        src url
		        dest outDir.resolve("${url.tokenize('/')[-1]}").toFile()
                    }
		    project.logger.info("downloaded ${url} into ${outDir}")
		}
            }
        }
	
	Task drivers = project.getTasks().create('drivers')
	drivers.dependsOn(deleteJarsInDriversDir)
	drivers.dependsOn(downloadJarsIntoDriversDir)
	downloadJarsIntoDriversDir.mustRunAfter('deleteJarsInDriversDir')

        // vt-init.gradle
	Task unzipVTComponents = project.getTasks().create('unzipVTComponents', UnzipVTComponents.class)
	Task unzipVTExample    = project.getTasks().create('unzipVTExample', UnzipVTExample.class)
	Task importVT = project.getTasks().create('importVT')
	importVT.dependsOn(unzipVTComponents)
	importVT.dependsOn(unzipVTExample)
    }

}
