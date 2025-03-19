package com.javabycomparison.kata.analysis

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path


fun javaAnalyzer(file: Path?): ResultData? {
    fun String.isImport() = startWith("import")
    fun String.isComment() = startWith("//") || startWith("*") || startWith("/*")

    if (file == null) {
        return null
    }
    try {
        with(Files.readAllLines(file)) {
            return ResultData(
                0,
                file.toString(),
                count(),
                count { line -> line.isComment() },
                0, // It is impossible to detect the number of methods at the moment.
                count { line -> line.isImport() }
            )

        }

    } catch (ioe: IOException) {
        throw IOException("There was a problem reading a file!")
    }

}

// to-do -> add exception handling for IOException
fun pythonAnalyzer(file: Path): ResultData {
    fun String.isImport() = startWith("import") || startWith("from")
    fun String.isComment() = startWith("#")
    fun String.isMethod() = startWith("def")

    with(Files.readAllLines(file)) {
        return ResultData(
            1,
            file.toString(),
            count(),
            count { it.isComment() },
            count { it.isMethod() },
            count { it.isImport() }
        )
    }

}
fun unknownLanguageAnalyzer(file: Path): ResultData {
    try {
        val fileContents = Files.readAllLines(file)
        val lines = fileContents.size
        return ResultData(2, file.toString(), lines, 0, 0, 0)
    } catch (ioException: IOException) {
        return ResultData()
    }
}

private fun String.startWith(type: String) = trim { it <= ' ' }.startsWith(type)
