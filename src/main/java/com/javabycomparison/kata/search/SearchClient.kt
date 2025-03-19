package com.javabycomparison.kata.search

import com.javabycomparison.kata.analysis.ResultData
import com.javabycomparison.kata.analysis.javaAnalyzer
import com.javabycomparison.kata.analysis.pythonAnalyzer
import com.javabycomparison.kata.analysis.unknownLanguageAnalyzer
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.util.stream.Collectors

class SearchClient(private val summery: Boolean) {
    fun collectAllFiles(directoryPath: String): LinkedList<ResultData?>? {
        val resultsList = LinkedList<ResultData?>()
        try {
            for (file in Files.walk(Paths.get(directoryPath))
                .filter { path: Path? -> !path.toString().contains(".git") }
                .filter { path: Path? ->
                    try {
                        return@filter !Files.isHidden(path)
                    } catch (e: IOException) {
                        return@filter false
                    }
                }
                .sorted()
                .collect(Collectors.toList())) {
                when {
                    isJavaFile(file) -> {
                        printJavaMessage(file, this@SearchClient.summery)
                        resultsList.add(javaAnalyzer(file))
                    }

                    isPythonFile(file) -> {
                        printPythonMessage(file, this@SearchClient.summery)
                        resultsList.add(pythonAnalyzer(file))
                    }

                    !Files.isDirectory(file) -> {
                        printUnknownLanguageMessage(file, this@SearchClient.summery)
                        resultsList.add(unknownLanguageAnalyzer(file))
                    }

                    else -> {
                            printSkippingMessage(file, this@SearchClient.summery)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return resultsList
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
