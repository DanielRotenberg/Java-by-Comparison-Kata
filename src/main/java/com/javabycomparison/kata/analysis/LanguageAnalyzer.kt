package com.javabycomparison.kata.analysis

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

// TODO -> remove all null stuff, shouldn't be null as param
fun javaAnalyzer(file: Path): FileSummary {
    fun String.isImport() = startWith("import")
    fun String.isComment() = startWith("//") || startWith("*") || startWith("/*")

//    if (file == null) {
////        return null
//        throw Exception("path was null")
//    }
    try {
        with(Files.readAllLines(file)) {
            return FileSummary(
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
fun pythonAnalyzer(file: Path): FileSummary {
    fun String.isImport() = startWith("import") || startWith("from")
    fun String.isComment() = startWith("#")
    fun String.isMethod() = startWith("def")

    with(Files.readAllLines(file)) {
        return FileSummary(
            1,
            file.toString(),
            count(),
            count { it.isComment() },
            count { it.isMethod() },
            count { it.isImport() }
        )
    }

}
fun unknownLanguageAnalyzer(file: Path): FileSummary {
    try {
        val fileContents = Files.readAllLines(file)
        val lines = fileContents.size
        return FileSummary(2, file.toString(), lines, 0, 0, 0)
    } catch (ioException: IOException) {
        return FileSummary()
    }
}

private fun String.startWith(type: String) = trim { it <= ' ' }.startsWith(type)
