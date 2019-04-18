package com.kazurayam.visualtestinginks.gradle

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import spock.lang.Ignore
import spock.lang.Specification

@Ignore
class PortingSpec extends Specification {

    // fields
    Path specOutputDir = Paths.get('build/tmp/testOutput').resolve(Helpers.getClassShortName(PortingSpec.class))

    Porting porting
    
    // fixture methods
    def setupSpec() {}
    def setup() {
        porting = new Porting()
    }
    def cleanup() {}
    def cleanupSpec() {}

    // feature methods

    // helper methods
}