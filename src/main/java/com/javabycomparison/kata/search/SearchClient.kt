package com.javabycomparison.kata.search

import com.javabycomparison.kata.analysis.FileSummary
import com.javabycomparison.kata.analysis.javaAnalyzer
import com.javabycomparison.kata.analysis.pythonAnalyzer
import com.javabycomparison.kata.analysis.unknownLanguageAnalyzer
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

class SearchClient(private val summary: Boolean) {
    fun collectAllFiles(directoryPath: String): LinkedList<FileSummary?>? {
        val summarizedFiles = LinkedList<FileSummary?>()
        val file = Paths.get(directoryPath).toFile()
        // to-do - deal with exceptions
        val allFiles =
        file.walk()
            .map { it.toPath() }
            .filter(Path::isGitOrHidden)

            if (!summary){
                allFiles.forEach { file->
                    printFileTypeMessage(file)
                }
            }

            allFiles.onEach { file ->
                  when {
                    isJavaFile(file) -> {
                        summarizedFiles.add(javaAnalyzer(file))
                    }

                    isPythonFile(file) -> {
                        summarizedFiles.add(pythonAnalyzer(file))
                    }

                    !Files.isDirectory(file) -> {
                        summarizedFiles.add(unknownLanguageAnalyzer(file))
                    }


                }


            }.toList()


        /*
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }*/
        return summarizedFiles
    }

    private fun printFileTypeMessage(file: Path) {
        when {
            isJavaFile(file) -> {
                printJavaMessage(file)
            }

            isPythonFile(file) -> {
                printPythonMessage(file)
            }

            !Files.isDirectory(file) -> {
                printUnknownLanguageMessage(file)
            }

            else -> {
                printSkippingMessage(file)
            }
        }
    }


    private fun printSkippingMessage(file: Path?) {
        println("Skipping directory $file.")
    }

    private fun printUnknownLanguageMessage(file: Path) {
        printLanguageMessage(file, " is neither a Java file nor a Python file.")
    }

    private fun printPythonMessage(file: Path) {
        printLanguageMessage(file, " is a Python file. It will be analyzed.")
    }
}

private fun printJavaMessage(file: Path?) {
    printLanguageMessage(file, " is a Java file. It will be analyzed.")
}

private fun printLanguageMessage(file: Path?, message: String) {
    println("File $file$message")
}


private fun isJavaFile(file: Path): Boolean = isFileOf(file, "java")

private fun isPythonFile(file: Path): Boolean = isFileOf(file, "py")

private fun isFileOf(file: Path, type: String): Boolean = file.toString().matches((".*\\.$type").toRegex())


private fun Path.isGitOrHidden(): Boolean {
    val isGitFile = !this.toString().contains(".git")
    val isHidden = return try {
        !Files.isHidden(this)
    } catch (e: IOException) {
        false
    }
    return isGitFile || isHidden
}
