package com.kazurayam.visualtestinginks.gradle

import java.nio.file.Path

public class VTPluginExtension {

    /**
     *  The version of "VisualTestingInKatalonStudio" project.
     *  The default is '0.0.0', which should not be used.
     *  Should be always overridden by build.gradle
     */
    String version = '0.0.0'

    List<String> dependencies = [
        'http://central.maven.org/maven2/ru/yandex/qatools/ashot/ashot/1.5.4/ashot-1.5.4.jar',
        'http://central.maven.org/maven2/net/coobird/thumbnailator/0.4.8/thumbnailator-0.4.8.jar',
        'http://central.maven.org/maven2/org/apache/commons/commons-lang3/3.6/commons-lang3-3.6.jar',
        'http://central.maven.org/maven2/org/apache/commons/commons-math3/3.6.1/commons-math3-3.6.1.jar',
        'https://github.com/kazurayam/junit4ks/releases/download/1.6/junit4ks-all.jar',
        'https://github.com/kazurayam/ksbackyard/releases/download/0.36.0/ksbackyard.jar',
        'https://github.com/kazurayam/Materials/releases/download/0.68.0/Materials-0.68.0.jar'
    ]

    String message = 'Hi from VTPlugin';

    // https://guides.gradle.org/implementing-gradle-plugins/
    void setVersion(String version) {
        this.version = version
    }
    
    String getDistributableGradlewFileName() {
        return "distributable-gradlew-${version}.zip"
    }

    String getDistributableVTComponentsFileName() {
        return "vt-components-${version}.zip"
    }

    String getDistributableVTExampleFileName() {
        return "vt-example-${version}.zip"
    }

}
