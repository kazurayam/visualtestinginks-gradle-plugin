# Project to develop a Gradle Plugin for the Visual Testing in Katalon Studio project

## Purpose of this project



## How to operate this project

### How to test the Plugin class with 

```
$ cd <projectDir>
$ ./gradlew test
```

### Publish Plugin to the Maven Local repository

```
$ cd <projectDir>
$ ./gradlew publishToMavenLocal
```

### Publishing Plugin to the Gradle Plugin Portal

Here we assume that you have logged in to the Gradle Plugin Portal and has got your API key, and stored your `gradle.publish.key` and `gradle.publish.secret`
into your `HOME_DIR/.gradle/gradle.properties` file.


And on the commandly, execute this:
```
$ cde <projectDir>
$ ./gradle publishPlugins
```

# References

- [Writing Gradle Plugins](https://guides.gradle.org/writing-gradle-plugins/)

- [Publishing Plugins to the Gradle Plugin Portal](https://guides.gradle.org/publishing-plugins-to-gradle-plugin-portal/)

- I learned how to publish a custom Gradle Plugin to the local mave repository by [How to write, test and publish a custom Gradle plugin](https://www.praqma.com/stories/gradle-plugin-bootstrap/) 
