package com.kazurayam.visualtestinginks.gradle

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

public class Porting extends DefaultTask {

    private String message;
    private String recipient;

    @Input
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    @Input
    public String getRecipient() {
        return recipient;
    }
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @TaskAction
    void sayGreeting() {
        System.out.printf("%s, %s!\n", getMessage(), getRecipient());
    }



    /**
     * Usage example:
     * <PRE>downloadFile('http://central.maven.org/maven2/ru/yandex/qatools/ashot/ashot/1.5.4/ashot-1.5.4.jar', 'Drivers')</PRE>
     *
     * @param remoteUrl of the file to download
     * @param localDir where the file is saved
     * @returns Path of the saved file
     */
    static Path downloadFile(String remoteUrl, String localDir) {
        return downloadFile(new URL(remoteUrl), Paths.get(localDir))
    }

    static Path downloadFile(String remoteUrl, Path localDir) {
        return downloadFile(new URL(remoteUrl, localDir))
    }

    static Path downloadFile(URL remoteUrl, String localDir) {
        return downloadFile(remoteUrl, Paths.get(localDir))
    }

    static Path downloadFile(URL remoteUrl, Path localDir) {
        Path outFile = localDir.resolve("${remoteUrl.toString().tokenize('/')[-1]}")
        outFile.toFile().withOutputStream { out ->
            remoteUrl.withInputStream { from ->
                out << from
            }
        }
        println "downloaded ${remoteUrl} into ${localDir} directory"
        return outFile
    }
}
