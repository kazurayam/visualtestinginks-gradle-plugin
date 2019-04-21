package com.kazurayam.visualtestinginks.gradle

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import spock.lang.Ignore
import spock.lang.Specification

@Ignore
class HelpersSpec extends Specification {

    // fields
    Path specOutputDir = Paths.get('build/tmp/testOutput').resolve(Helpers.getClassShortName(HelpersSpec.class))

    // fixture methods
    def setupSpec() {}
    def setup() {}
    def cleanup() {}
    def cleanupSpec() {}

    // feature methods
    def test_getClassShortName() {
        expect:
            Helpers.getClassShortName(Helpers.class) == 'Helpers'
    }

    def test_deleteDirectory() {
        setup:
            Path caseOutputDir = specOutputDir.resolve('test_deleteDirectory')
            Path tmpDir = caseOutputDir.resolve('tmp')
            Files.createDirectories(tmpDir)
            Path a = tmpDir.resolve('a.txt')
            a.toFile().text = 'hello'
        expect:
            Files.exists(a)
        when:
            Helpers.deleteDirectory(tmpDir)
        then:
            ! Files.exists(tmpDir)
    }

    def test_deleteDirectoryContents() {
        setup:
            Path caseOutputDir = specOutputDir.resolve('test_deleteDirectoryContents')
            Path tmpDir = caseOutputDir.resolve('tmp')
            Files.createDirectories(tmpDir)
            Path a = tmpDir.resolve('a.txt')
            a.toFile().text = 'hello'
        expect:
            Files.exists(a)
        when:
            Helpers.deleteDirectoryContents(tmpDir)
        then:
            Files.exists(tmpDir)
            ! Files.exists(a)
    }

    def test_downloadFile() {
        setup:
            Path caseOutputDir = specOutputDir.resolve('test_downloadFile')
            Files.createDirectories(caseOutputDir)
            Helpers.deleteDirectoryContents(caseOutputDir)
        when:
            Helpers.downloadFile(
                new URL('https://github.com/kazurayam/junit4ks/releases/download/1.6/junit4ks-all.jar'),
                caseOutputDir)
            Path downloadedFile = caseOutputDir.resolve('junit4ks-all.jar')
        then:
            Files.exists(downloadedFile)
    }

    // helper methods
}
