package com.javabycomparison.kata.search

import com.javabycomparison.kata.analysis.AnalyzerImpl
import com.javabycomparison.kata.analysis.JavaAnalyzer
import com.javabycomparison.kata.analysis.PythonAnalyzer
import com.javabycomparison.kata.analysis.ResultData
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.util.stream.Collectors

class SearchClient(private val smry: Boolean) {
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
                    if (!smry) {
                        println("File " + file.toString() + " is a Java file. It will be analyzed.")
                    }
                    val resultData = JavaAnalyzer(file).analyze()
                    resultsList.add(resultData)
                } else if (isPythonFile(file)) {
                    if (!smry) {
                        println(
                            "File " + file.toString() + " is a Python file. It will be analyzed."
                        )
                    }
                    val resultData = PythonAnalyzer(file).analyze()
                    resultsList.add(resultData)
                } else {
                    if (!Files.isDirectory(file)) {
                        if (!smry) {
                            println(
                                "File " + file.toString() + " is neither a Java file nor a Python file."
                            )
                        }
                        resultsList.add(AnalyzerImpl(file).analyze())
                    } else {
                        if (!smry) {
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

    private fun isJavaFile(file: Path): Boolean {
        if (file.toString().matches(".*\\.java".toRegex())) {
            return true
        } else {
            return false
        }
    }

    private fun isPythonFile(file: Path): Boolean {
        if (file.getFileName().toString().matches(".*\\.py".toRegex())) {
            return true
        }
        return false
    }
}
