package com.kazurayam.visualtestinginks.gradle

import java.nio.file.Path

public class VTPluginExtension {

    List<String> dependencies = [
        'http://central.maven.org/maven2/ru/yandex/qatools/ashot/ashot/1.5.4/ashot-1.5.4.jar',
        'http://central.maven.org/maven2/net/coobird/thumbnailator/0.4.8/thumbnailator-0.4.8.jar',
        'http://central.maven.org/maven2/org/apache/commons/commons-lang3/3.6/commons-lang3-3.6.jar',
        'http://central.maven.org/maven2/org/apache/commons/commons-math3/3.6.1/commons-math3-3.6.1.jar',
        'https://github.com/kazurayam/junit4ks/releases/download/1.6/junit4ks-all.jar',
        'https://github.com/kazurayam/ksbackyard/releases/download/0.36.0/ksbackyard.jar',
        'https://github.com/kazurayam/Materials/releases/download/0.68.0/Materials-0.68.0.jar'
    ]

    String vcsUrlPrefix  = "https://github.com/kazurayam/VisualTestingInKatalonStudio/releases/download"

    String version = '1.9.2'

    String gradlewFileName = 'gradlew-packaged.zip'

    String vtComponentsFileName = 'vt-components.zip'

    String vtExampleFileName = 'vt-example.zip'



    String message = 'Hi from VTPlugin';

}
