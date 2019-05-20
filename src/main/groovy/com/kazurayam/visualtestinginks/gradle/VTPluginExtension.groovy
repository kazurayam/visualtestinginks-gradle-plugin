package com.kazurayam.visualtestinginks.gradle

public class VTPluginExtension {

    /**
     *  The version of the "VisualTestingInKatalonStudio" project.
     *  The default is '0.0.0'. If the version is left as default,
     *  :distributables task will create
     *      <PRE>build/dist/vt-components-0.0.0.zip</PRE>
     *  file, and :importVTCompnents task will try to download URL of
     *      <PRE>https://github.com/kazurayam/VisualTestingInKatalonStudio/releases/download/0.0.0/vt-components-0.0.0.zip</PRE>
     *  which does not exists, so the task invokation will fail.
     *  Therefore the vt.version should alwasy be overridden by build.grade, for example:
     *      <PRE>vt.version = '1.10.0'</PRE>
     */
    String version = '0.0.0'

    List<String> dependencies = [
        'http://central.maven.org/maven2/ru/yandex/qatools/ashot/ashot/1.5.4/ashot-1.5.4.jar',
        'http://central.maven.org/maven2/net/coobird/thumbnailator/0.4.8/thumbnailator-0.4.8.jar',
        'http://central.maven.org/maven2/org/apache/commons/commons-lang3/3.6/commons-lang3-3.6.jar',
        'http://central.maven.org/maven2/org/apache/commons/commons-math3/3.6.1/commons-math3-3.6.1.jar',
        'https://github.com/kazurayam/junit4ks/releases/download/1.6/junit4ks-all.jar',
        'https://github.com/kazurayam/ksbackyard/releases/download/0.36.0/ksbackyard.jar',
        'https://github.com/kazurayam/Materials/releases/download/0.70.6/Materials-0.70.6.jar'
    ]

    String repositoryUrlPrefix = "https://github.com/kazurayam/VisualTestingInKatalonStudio/releases/download"

    String message = 'Hi from VTPlugin';

}
