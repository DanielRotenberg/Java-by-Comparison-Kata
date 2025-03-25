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
        file.walk()
            .map { it.toPath() }
            .filter(Path::isGitOrHidden)

            // if summary true -> return the files
            .onEach { file ->
        /*        if (summary){
                    return@onEach
                }
*/                when {
                    isJavaFile(file) -> {
                        printJavaMessage(file, this@SearchClient.summary)
                        summarizedFiles.add(javaAnalyzer(file))
                    }

                    isPythonFile(file) -> {
                        printPythonMessage(file, this@SearchClient.summary)
                        summarizedFiles.add(pythonAnalyzer(file))
                    }

                    !Files.isDirectory(file) -> {
                        printUnknownLanguageMessage(file, this@SearchClient.summary)
                        summarizedFiles.add(unknownLanguageAnalyzer(file))
                    }

                    else -> {
                        printSkippingMessage(file, this@SearchClient.summary)
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


    private fun printSkippingMessage(file: Path?, summery: Boolean) {
        if (summery) return
        println("Skipping directory $file.")
    }

    private fun printUnknownLanguageMessage(file: Path, summery: Boolean) {
        if (summery) return
        printLanguageMessage(file, " is neither a Java file nor a Python file.")
    }

    private fun printPythonMessage(file: Path, summery: Boolean) {
        if (summery) return
        printLanguageMessage(file, " is a Python file. It will be analyzed.")
    }
}

private fun printJavaMessage(file: Path?, summery: Boolean) {
    if (summery) return
    printLanguageMessage(file, " is a Java file. It will be analyzed.")
}

private fun printLanguageMessage(file: Path?, message: String) {
    println("File $file$message")
}


private fun isJavaFile(file: Path): Boolean = isFileOf(file, "java")

private fun isPythonFile(file: Path): Boolean = isFileOf(file, "py")

private fun isFileOf(file: Path, type: String): Boolean = file.toString().matches((".*\\.$type").toRegex())


/*
fun

        if (!summary){
    summarizedFiles.forEach {
            file ->

        when {
            isJavaFile(file) -> {
                printJavaMessage(file, this@SearchClient.summary)
                summarizedFiles.add(javaAnalyzer(file))
            }

            isPythonFile(file) -> {
                printPythonMessage(file, this@SearchClient.summary)
                summarizedFiles.add(pythonAnalyzer(file))
            }

            !Files.isDirectory(file) -> {
                printUnknownLanguageMessage(file, this@SearchClient.summary)
                summarizedFiles.add(unknownLanguageAnalyzer(file))
            }

            else -> {
                printSkippingMessage(file, this@SearchClient.summary)
            }
        }
    }
}*/
private fun Path.isGitOrHidden(): Boolean {
    val gitFile = !this.toString().contains(".git")
    val isHidden = return try {
        !Files.isHidden(this)
    } catch (e: IOException) {
        false
    }
    return gitFile || isHidden
}
