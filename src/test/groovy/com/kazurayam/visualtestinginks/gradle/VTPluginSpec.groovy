package com.kazurayam.visualtestinginks.gradle

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

    def "downloadDependentJars task downloads ashot-1.5.4.jar"() {
        given:
            buildFile << '''
                plugins {
                    id 'com.github.kazurayam.visualtestinginks'
                }
            '''
        when:
            def result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments('downloadDependentJars')
                .withPluginClasspath()
                .build()
            println result.output
        then:
            result.output.contains('ashot-1.5.4.jar')
            result.task(":downloadDependentJars").outcome == SUCCESS
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
