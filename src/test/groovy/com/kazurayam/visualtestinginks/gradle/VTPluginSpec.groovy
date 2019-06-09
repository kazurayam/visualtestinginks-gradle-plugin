package com.kazurayam.visualtestinginks.gradle

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors

import org.gradle.testkit.runner.GradleRunner
import static org.gradle.testkit.runner.TaskOutcome.*
import org.junit.Rule
import org.junit.rules.TemporaryFolder

import spock.lang.Ignore
import spock.lang.IgnoreRest
import spock.lang.Specification

class VTPluginSpec extends Specification {

    @Rule public final TemporaryFolder testProjectDir = new TemporaryFolder()

    private File buildFile
    private Path fixtureProject

    def setup() {
        buildFile = testProjectDir.newFile("build.gradle")
        fixtureProject = Paths.get('.', 'src', 'test', 'resources', 'fixture', 'vt-project')
    }

    /**
     *
     */
    def ":enableVisualTesting task depends on :importVTComponents, :importVTExample"() {
        given:
        buildFile << '''
        plugins {
            id 'com.github.kazurayam.visualtestinginks'
        }
        vt.version = '1.10.0'
        '''
        when:
        def result = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments("enableVisualTesting")
            .withPluginClasspath()
            .build()
        then:
        result.task(":enableVisualTesting").outcome == SUCCESS

    }

    def ":importVTComponents task downloads and extracts the zip"() {
        setup:
        buildFile << '''
        plugins {
            id 'com.github.kazurayam.visualtestinginks'
        }
        vt.version = '1.10.0'
        '''
        when:
        def result = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments('importVTComponents')
            .withPluginClasspath()
            .build()
        listDirectory(testProjectDir.root.toPath())
        then:
        result.output.contains("${Constants.VT_DIST_COMPONENTS_PREFIX}-1.10.0.zip")
        result.task(":importVTComponents").outcome == SUCCESS
        when:
        Path testListenersDir = testProjectDir.root.toPath().resolve('Test Listeners')
        Path vtListener = testListenersDir.resolve('VTListener.groovy')
        then:
        Files.exists(vtListener)
    }

    def ":importVTExample task downloads and extracts the zip"() {
        setup:
        buildFile << '''
        plugins {
            id 'com.github.kazurayam.visualtestinginks'
        }
        vt.version = '1.10.0'
        '''
        when:
        def result = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments("importVTExample")
            .withPluginClasspath()
            .build()
        listDirectory(testProjectDir.root.toPath())
        then:
        result.output.contains("vt-example-1.10.0.zip")
        result.task(":importVTExample").outcome == SUCCESS
        when:
        Path testCasesDir = testProjectDir.root.toPath().resolve('Test Cases')
        listDirectory(testCasesDir)
        Path vtDir = testCasesDir.resolve('CURA')
        listDirectory(vtDir)
        Path makeIndexTC = vtDir.resolve('visitSite.tc')
        then:
        Files.exists(makeIndexTC)
    }


    def "let us have a look at taskTree of the :enableVisualTesting task"() {
        setup:
        buildFile << '''
            plugins {
                id 'com.github.kazurayam.visualtestinginks'
                id 'com.dorongold.task-tree' version '1.3.1'
            }
            vt.version = '1.10.0'
        '''
        when:
        def result = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments('enableVisualTesting', 'taskTree')
            .withPluginClasspath()
            .build()
        println "${result.output}"
        then:
        result.task(':taskTree').outcome == SUCCESS
    }

    /**
     *
     */
    @Ignore
    def ":greeting task prints Hi from VTPlugin"() {
        given:
        buildFile << '''
        plugins {
            id 'com.github.kazurayam.visualtestinginks'
        }
        '''
        when:
        def result = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments("greeting")
            .withPluginClasspath()
            .build()
        then:
        result.output.contains("Hi from VTPlugin")
        result.task(":greeting").outcome == SUCCESS
    }


    /**
     * for DEBUG
     */
    private void listDirectory(Path dir) {
        List<Path> files = Files.list(dir).collect(Collectors.toList())
        println "listing contents of ${dir.toAbsolutePath().toString()}"
        for (Path file: files) {
            println "    ${file.toAbsolutePath().toString()}"
        }
    }

}
