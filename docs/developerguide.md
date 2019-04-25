Developer Guide of Gradle Plugin com.github.visualtestinginks
====================================

## Operation

### How to test the Plugin scripts locally before publishing

```
$ cd <projectDir>
$ ./gradlew test
```

### How to publish the Plugin into the Maven Local repository on my PC

```
$ cd <projectDir>
$ ./gradlew publishToMavenLocal
```

### How to publish the Plugin to the Gradle Plugin Portal

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

## References

- [Writing Gradle Plugins](https://guides.gradle.org/writing-gradle-plugins/)

- [Publishing Plugins to the Gradle Plugin Portal](https://guides.gradle.org/publishing-plugins-to-gradle-plugin-portal/)

- I learned how to publish a custom Gradle Plugin to the local mave repository by [How to write, test and publish a custom Gradle plugin](https://www.praqma.com/stories/gradle-plugin-bootstrap/)
