# visualtestinginks --- a Gradle Plugin

author: kazurayam

April, 2019

## Overview

This project is a [Gradle](https://gradle.org/) project where kazurayam (it's me) develops a custom Gradle Plugin [`com.github.kazurayam.visualtestinginks`](https://plugins.gradle.org/plugin/com.github.kazurayam.visualtestinginks).

The Gradle Plugin `com.github.kazurayam.visualtestinginks` is available for any [Katalon Studio](https://www.katalon.com/) users at the [Gradle Plugin Portal](https://plugins.gradle.org/plugin/com.github.kazurayam.visualtestinginks).

The Gradle Plugin `com.github.kazurayam.visualtestinginks` is developed in order to make my [`VisualTestingInKatalonStudio`](https://github.com/kazurayam/VisualTestingInKatalonStudio) project portable to other Katalon Studio projects. With the resources imported, his/her Katalon projects becomes capable of screenshot-comparison testing just as [`VisualTestingInKatalonStudio`](https://github.com/kazurayam/VisualTestingInKatalonStudio) does.


The Gradle Plugin `com.github.kazurayam.visualtestinginks` is designed with 2 usecases in mind.

1. Anyone can use this Gradle plugin to turn his/her [Katalon Studio](https://www.katalon.com/) project capable of screenshot-comparison testing by importing a set of distributables (zip files, jar files) from Internet (Maven Repositories and GitHub).
2. The developer of [`VisualTestingInKatalonStudio`](https://github.com/kazurayam/VisualTestingInKatalonStudio) project (it's me! kazurayam) uses this plugin to prepare the distributables (zip files) which contain Visual Testing components. He will post the zip files to the [GitHub Releases page](https://github.com/kazurayam/VisualTestingInKatalonStudio/releases) so that anyone can download these files.


## 2. Building the distributables of Visual Testing

The Developer (kazurayam, it's me!) uses this Gradle Plugin `com.github.kazurayam.visualtestinginks` to generate distributables of ["VisualTestingInKatalonStudio" project](https://github.com/kazurayam/VisualTestingInKatalonStudio).

In the VisualTestingInKatalonStudio project, kazurayam created [`build.gradle`](https://github.com/kazurayam/VisualTestingInKatalonStudio/blob/master/build.gradle) file as follows:
```
plugins {
    id "com.github.kazurayam.visualtestinginks" version="0.1.0"
}
```

By applying the plugin, a set of Gradle task become exacutable. Among others, the `:distributables` task is the single entry point of execution.

```
:distributables
+--- :cleanDist
|    `--- :createDist
+--- :createDistributableGradlew
+--- :createVTComponents
`--- :createVTExample
```

kazuarayam will execute the task as follows:

```
$ cd %VisualTestingInKatalonStudio%
$ gradle distributables
```

Once done, in the `build\dist` directory, 3 zip files will be created:

```
build\dist
|-- distributable-gradlew.zip
|-- vt-components.zip
`-- vt-example.zip
```

The contents of these zip files are explained in another document [Developer Guide](https://kazurayam.github.io/visualtestinginks-gradle-plugin/developerguide).

kazurayam will upload these zip files to the [Github Releases page](https://github.com/kazurayam/VisualTestingInKatalonStudio/releases) for distribution.


## 1. Turning his/her Katalon Studio project capable of Visual Testing

By applying `com.github.kazurayam.visualtestinginks` in `build.gradle` file, a Katalon Studio user can import the components (Test Cases, Test Suites, Test Listener, Keyword, external jar fiales as dependencies) of "Visual Testing in Katalon Studio" into his/her Katalon Studio project. Also he/she can import the resources a example Visual Testing.

```
:enableVisualTesting
+--- :importVTComponents
+--- :importVTExample
`--- :updateDrivers
     +--- :deleteExternalLibraries
     `--- :importExternalLibraries
```

See https://kazurayam.github.io/visualtestinginks-gradle-plugin/ for more information.
