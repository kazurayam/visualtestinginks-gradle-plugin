package com.kazurayam.visualtestinginks.gradle

import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

import org.gradle.testkit.runner.GradleRunner
import static org.gradle.testkit.runner.TaskOutcome.*
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class VTPluginSpec extends Specification {

    @Rule public final TemporaryFolder testProjectDir = new TemporaryFolder()

    private File buildFile

    def setup() {
        buildFile = testProjectDir.newFile("build.gradle")
    }

    def "downloadDependencies task downloads junit4ks-all.jar"() {
        given:
            buildFile << '''
                plugins {
                    id 'com.github.kazurayam.visualtestinginks'
                }
                vt.dependencies = [
                    'https://github.com/kazurayam/junit4ks/releases/download/1.6/junit4ks-all.jar'
                    ]
            '''
        when:
            def result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments('downloadDependencies')
                .withPluginClasspath()
                .build()
            println result.output
        then:
            result.output.contains('junit4ks-all.jar')
            result.task(":downloadDependencies").outcome == SUCCESS
        when:
            Path driversDir = testProjectDir.root.toPath().resolve('Drivers')
            List<Path> files = Files.list(driversDir).collect(Collectors.toList())
            List<String> fileNames = files.stream().map({Path p -> p.getFileName().toString()}).collect(Collectors.toList())
        then:
            fileNames.contains('vt-junit4ks-all.jar')
    }

    def "greeting task prints Hi from VTPlugin"() {
        given:
            buildFile << '''
                plugins {
                    id 'com.github.kazurayam.visualtestinginks'
                }
            '''
        when:
            def result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments('greeting')
                .withPluginClasspath()
                .build()
        then:
            result.output.contains('Hi from VTPlugin')
            result.task(":greeting").outcome == SUCCESS
    }

}
