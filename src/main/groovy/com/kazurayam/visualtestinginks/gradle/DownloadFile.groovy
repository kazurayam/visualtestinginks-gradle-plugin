package com.kazurayam.visualtestinginks.gradle

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class DownloadFile extends DefaultTask {

    @TaskAction
    void doTaskAction() {
        System.out.println("do downloadFile")
    }
}