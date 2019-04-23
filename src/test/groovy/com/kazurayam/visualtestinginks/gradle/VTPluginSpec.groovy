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

    def "createPackagedGradlew task creates a zip file"() {
        setup:
        Path targetDir = testProjectDir.getRoot().toPath()
        Helpers.copyDirectory(fixtureProject, targetDir)
        buildFile << '''
            plugins {
                id 'com.github.kazurayam.visualtestinginks'
            }
        '''
        when:
        def result = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments('createPackagedGradlew')
            .withPluginClasspath()
            .build()
        //then:
        //result.task(':createPackagedGradlew').outcome == SUCCESS
        //when:
        Path distDir = testProjectDir.root.toPath().resolve('build').resolve('dist')
        listDirectory(distDir)
        Path gradlewZip = distDir.resolve(Constants.gradlewFileName)
        then:
        assert Files.exists(gradlewZip)
    }

    def "importVTComponents task downloads and extracts the zip"() {
        setup:
        buildFile << '''
        plugins {
            id 'com.github.kazurayam.visualtestinginks'
        }
        '''
        when:
        def result = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments('importVTComponents')
            .withPluginClasspath()
            .build()
        //println result.output
        then:
        result.output.contains(Constants.vtComponentsFileName)
        result.task(":importVTComponents").outcome == SUCCESS
        when:
        Path testListenersDir = testProjectDir.root.toPath().resolve('Test Listeners')
        Path vtListener = testListenersDir.resolve('VTListener.groovy')
        then:
        Files.exists(vtListener)
    }

    def "importVTExample task downloads and extracts the zip"() {
        setup:
        buildFile << '''
        plugins {
            id 'com.github.kazurayam.visualtestinginks'
        }
        '''
        when:
        def result = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments('importVTExample')
            .withPluginClasspath()
            .build()
        listDirectory(testProjectDir.root.toPath())
        then:
        result.output.contains(Constants.vtExampleFileName)
        result.task(':importVTExample').outcome == SUCCESS
        when:
        Path testCasesDir = testProjectDir.root.toPath().resolve('Test Cases')
        listDirectory(testCasesDir)
        Path vtDir = testCasesDir.resolve('CURA')
        listDirectory(vtDir)
        Path makeIndexTC = vtDir.resolve('visitSite.tc')
        then:
        Files.exists(makeIndexTC)
    }

    /**
     *
     */
    private void listDirectory(Path dir) {
        List<Path> files = Files.list(dir).collect(Collectors.toList())
        println "listing contents of ${dir.toAbsolutePath().toString()}"
        for (Path file: files) {
            println "    ${file.toAbsolutePath().toString()}"
        }
    }

    /**
     *
     */
    def "downloadDependencies task downloads junit4ks-all.jar"() {
        setup:
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

    /**
     *
     */
    def "deleteDependencies task deletes Drivers/vt-* files"() {
        setup:
        buildFile << '''
        plugins {
            id 'com.github.kazurayam.visualtestinginks'
        }
        vt.dependencies = [
            'https://github.com/kazurayam/junit4ks/releases/download/1.6/junit4ks-all.jar'
        ]
        '''
        Path driversDir = testProjectDir.root.toPath().resolve('Drivers')
        List<Path> files
        when:
        def downloadResult = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments('downloadDependencies')
            .withPluginClasspath()
            .build()
        List<Path> filesAfterDownload = Files.list(driversDir).collect(Collectors.toList())
        then:
        filesAfterDownload.size() == 1
        when:
        def deleteResult = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments('deleteDependencies')
            .withPluginClasspath()
            .build()
        List<Path> filesAfterDelete = Files.list(driversDir).collect(Collectors.toList())
        then:
        filesAfterDelete.size() == 0      // assert vt-junit4ks-all.jar is deleted
    }



    /**
     *
     */
    @Ignore
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
