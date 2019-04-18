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

    def "greeting task of VTPlugin prints Hi from VTPlugin"() {
        given:
            buildFile << '''
                plugins {
                    /* I found that an external plugin need to be declared here.
                     * I do not see why it is necessary. The following issue looks relevant:
                     * https://github.com/gradle/gradle/issues/1262
                     */
                    id 'de.undercouch.download' version '3.4.3'


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
