import org.gradle.testkit.runner.GradleRunner
import static org.gradle.testkit.runner.TaskOutcome.*
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

/**
 * https://docs.gradle.org/4.10-rc-2/userguide/test_kit.html
 */
public class BuildLogicFunctionalSpec extends Specification {

    @Rule public final TemporaryFolder testProjectDir = new TemporaryFolder()

    private File buildFile

    def setup() {
        buildFile = testProjectDir.newFile("build.gradle")
    }

    def "greeting task of PortingPlugin prints Hi from PortingPlugin"() {
        given:
            buildFile << '''
                plugins {
                    id 'com.github.kazurayam.visualtestinginks-gradle-plugin'
                }
            '''
        when:
            def result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments('greeting')
                .withPluginClasspath()
                .build()
        then:
            result.output.contains('Hi from PortingPlugin')
            result.task(":greeting").outcome == SUCCESS
    }

    def "helloWorld task prints Hello world!"() {
        given:
            buildFile << '''
                task helloWorld {
                    doLast {
                        println 'Hello world!'
                    }
                }
            '''
        when:
            def result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments('helloWorld')
                .build()
        then:
            result.output.contains('Hello world!')
            result.task(":helloWorld").outcome == SUCCESS
    }

}