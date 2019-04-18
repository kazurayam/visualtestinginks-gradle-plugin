package com.kazurayam.visualtestinginks.gradle

import java.nio.file.Files
import java.nio.file.FileVisitResult
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import java.util.stream.Collectors

import org.slf4j.Logger
import org.slf4j.LoggerFactory

final class Helpers {

    static Logger logger_ = LoggerFactory.getLogger(Helpers.class)

    /**
     * Returns a short name of a Class stripping the package.
     * For example, if
     * <PRE>com.kazurayam.visualtestinginks.gradle.Helpers</Pre>
     * is given as
     * <PRE>clazz</PRE>
     * , then returns
     * <PRE>Helpers</PRE>
     *
     * @param clazz Class instance
     * @return a String as the short name of the clazz
     */
    static String getClassShortName(Class clazz) {
        String fqdn = clazz.getName()
        String packageStr = clazz.getPackage().getName()
        String shortName = fqdn.replaceFirst(packageStr + '.', '')
        return shortName
    }

    /**
     * delete the directory recursively. the specified directory will be deleted.
     */
    static void deleteDirectory(Path directory) throws IOException {
        Files.walkFileTree(directory,
            new SimpleFileVisitor<Path>() {
                @Override
                FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir)
                    return FileVisitResult.CONTINUE
                }

                @Override
                FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file)
                    return FileVisitResult.CONTINUE
                }
            }
        )
    }

    /**
     * delete the files and directories contained in the
     * targetDirectory recursively, the targetDirectory itself will
     * be retained.
     */
    static void deleteDirectoryContents(Path targetDirectory) throws IOException {
        if (Files.exists(targetDirectory)) {
            List<Path> children = Files.list(targetDirectory).collect(Collectors.toList())
            for (Path child: children) {
                if (Files.isRegularFile(child)) {
                    Files.delete(child)
                } else if (Files.isDirectory(child)) {
                    deleteDirectory(child)
                } else {
                    logger_.warn("deleteDirectoryContents ${child.toString()}" +
                        " is not a File nor a Directory")
                }
            }
        }
    }


    /**
     * Usage example:
     * <PRE>Path file = downloadFile('http://central.maven.org/maven2/ru/yandex/qatools/ashot/ashot/1.5.4/ashot-1.5.4.jar', 'Drivers')</PRE>
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

    /**
     * Usage example:
     * <PRE>Path file = downloadFile(new URL('http://central.maven.org/maven2/ru/yandex/qatools/ashot/ashot/1.5.4/ashot-1.5.4.jar'), Paths.get('Drivers'))</PRE>
     *
     * @param remoteUrl of the file to download
     * @param localDir where the file is saved
     * @returns Path of the saved file
     */
    static Path downloadFile(URL remoteUrl, Path localDir) {
        Path outFile = localDir.resolve("${remoteUrl.toString().tokenize('/')[-1]}")
        outFile.toFile().withOutputStream { out ->
            remoteUrl.withInputStream { from ->
                out << from
            }
        }
        logger_.info("downloaded ${remoteUrl} into ${localDir} directory")
        return outFile
    }

}