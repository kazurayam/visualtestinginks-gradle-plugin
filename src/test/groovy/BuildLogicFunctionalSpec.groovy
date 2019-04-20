import org.gradle.testkit.runner.GradleRunner
import static org.gradle.testkit.runner.TaskOutcome.*
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Ignore
import spock.lang.Specification

/**
 * https://docs.gradle.org/4.10-rc-2/userguide/test_kit.html
 */
@Ignore
public class BuildLogicFunctionalSpec extends Specification {

    @Rule public final TemporaryFolder testProjectDir = new TemporaryFolder()

    private File buildFile

    def setup() {
        buildFile = testProjectDir.newFile("build.gradle")
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
