Developer Guide of Gradle Plugin com.github.visualtestinginks
====================================

## Overview

1. In this `visualtestinginks-gradle-plugin` project, the developer (kazurayam) develops the Gradle Plugin `com.github.kazurayam.visualtestinginks` . He publishes the plugn to the [Gradle Plugin Portal](https://plugins.gradle.org/plugin/com.github.kazurayam.visualtestinginks).
2. The developer (kazurayam) applies the Gradle Plugin `com.github.kazurayam.visualtestinginks` to build the [`VisualTestingInKataloStudio`](https://github.com/kazurayam/VisualTestingInKatalonStudio) project. With build.gradle, he creates a set of distributable zip files which contain the resources(codes) that makes  `VisualTestingInKatalonStudio`. He uploads those files to the [Releases page](https://github.com/kazurayam/VisualTestingInKatalonStudio)
3. Any Katalon Studio user who is interested in `VisualTestingInKatalonStudio` can use [Gradle Build Tool](https://gradle.org/) and the Gradle Plugin [`com.github.kazurayam.visualtestinginks`](https://plugins.gradle.org/plugin/com.github.kazurayam.visualtestinginks) to make his/her own Katalon Studio project capable of screenshot comparison testings in a way that `VisualTestingInKatalonStudio` promotes.

In this Developer Guide, I will describe the 1st and 2nd usecases above. The 3rd usecase will be described in the [User Guide](./userguide.md).

## 1. Developing the Plugin

In the `visualtestinginks-gradle-plugin` project, the developer (kazurayam, it's me) can execute the following 3 Gradle tasks.

### build.gradle

[visualtestinginks-gradle-plugin/build.gradle](../build.gradle)

### Operation

#### (a) How to test the Plugin scripts using Spock locally

```
$ cd <projectDir>
$ ./gradlew test
```

kazurayam can see the test report at `<projectDir>/build/reports/tests/test/index.html`

#### (b) How to publish the Plugin into the Maven Local repository on my PC

```
$ cd <projectDir>
$ ./gradlew publishToMavenLocal
```

#### (c) How to publish the Plugin to the Gradle Plugin Portal

See [How do I add my plugin to the plugin portal?](https://plugins.gradle.org/docs/submit) for definitive instruction.

The developer (it's me! kazurayam) has done the following stuff of preparation:

1. kazurayam has created an account : https://plugins.gradle.org/u/kazurayam
2. kazurayam has got his API key allocated by the Portal site.
3. kazurayam stored `gradle.publish.key` and `gradle.publish.secret` into the `HOME_DIR/.gradle/gradle.properties` file of his PC and Mac.

In the command line, execute this:

```
$ cd <projectDir>
$ ./gradlew publishPlugins
```


## 2. Distributing VisualTestingInKatalonStudio


### Task tree


In the VisualTestingInKatalonStudio project, kazurayam created [`build.gradle`](https://github.com/kazurayam/VisualTestingInKatalonStudio/blob/master/build.gradle) file as follows:
```
plugins {
    id "com.github.kazurayam.visualtestinginks" version="X.X.X"
}
```
where `X.X.X` portion should be the latest version of the Gradle Plugin `com.kazurayam.visualtestinginks` publshed at the [Gradle Plugin Portal]()

By applying the plugin, a set of Gradle tasks become executable. Among others, the `:distributables` task is the entry point of execution for kazurayam to generate the zip files.

```
:distributables
+--- :cleanDist
|    \--- :createDist
+--- :createDistributableGradlewWithVersion
|    \--- :createDistributableGradlew
+--- :createVTComponentsWithVersion
|    \--- :createVTComponents
\--- :createVTExampleWithVersion
     \--- :createVTExample
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

kazurayam will upload the 3 zip files to the [Github Releases page](https://github.com/kazurayam/VisualTestingInKatalonStudio/releases) for distribution.
