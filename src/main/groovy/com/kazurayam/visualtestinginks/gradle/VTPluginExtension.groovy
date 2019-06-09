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

    String repositoryUrlPrefix = "https://github.com/kazurayam/VisualTestingInKatalonStudio/releases/download"

    String message = 'Hi from VTPlugin';

}
