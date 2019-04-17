package com.kazurayam.visualtestinginks.gradle

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import spock.lang.Specification

class PortingSpec extends Specification {

    // fields
    Path specOutputDir = Paths.get('build/tmp/testOutput').resolve(Helpers.getClassShortName(PortingSpec.class))

    // fixture methods
    def setupSpec() {}
    def setup() {}
    def cleanup() {}
    def cleanupSpec() {}

    // feature methods
    def test_downloadFile() {
        setup:
            Path caseOutputDir = specOutputDir.resolve('test_downloadFile')
            Files.createDirectories(caseOutputDir)
            Helpers.deleteDirectoryContents(caseOutputDir)
        when:
            Porting.downloadFile(
                new URL('https://github.com/kazurayam/junit4ks/releases/download/1.6/junit4ks-all.jar'),
                caseOutputDir)
            Path downloadedFile = caseOutputDir.resolve('junit4ks-all.jar')
        then:
            Files.exists(downloadedFile)
    }
    // helper methods
}