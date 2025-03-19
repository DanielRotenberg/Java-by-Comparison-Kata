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
                if (isJavaFile(file)) {
                    if (!summery) {
                        println("File " + file.toString() + " is a Java file. It will be analyzed.")
                    }
                    val resultData = javaAnalyzer(file)
                    resultsList.add(resultData)
                } else if (isPythonFile(file)) {
                    if (!summery) {
                        println(
                            "File " + file.toString() + " is a Python file. It will be analyzed."
                        )
                    }
                    val resultData = pythonAnalyzer(file)
                    resultsList.add(resultData)
                } else {
                    if (!Files.isDirectory(file)) {
                        if (!summery) {
                            println(
                                "File " + file.toString() + " is neither a Java file nor a Python file."
                            )
                        }
                        resultsList.add(unknownLanguageAnalyzer(file))
                    } else {
                        if (!summery) {
                            println("Skipping directory " + file + ".")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return resultsList
    }
}

private fun isJavaFile(file: Path): Boolean = isFileOf(file, "java")

private fun isPythonFile(file: Path): Boolean = isFileOf(file, "py")

private fun isFileOf(file: Path, type: String): Boolean = file.toString().matches((".*\\.$type").toRegex())
