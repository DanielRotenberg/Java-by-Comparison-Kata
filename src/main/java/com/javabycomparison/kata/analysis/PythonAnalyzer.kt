package com.javabycomparison.kata.analysis

import java.nio.file.Files
import java.nio.file.Path

// to-do -> add exception handling for IOException
fun pythonAnalyze(file: Path): ResultData {
    val pythonTypes = Files.readAllLines(file).map(::toPythonType)
    return ResultData(
        1,
        file.toString(),
        pythonTypes.count(),
        pythonTypes.count { type -> type == PythonTypes.Comment },
        pythonTypes.count { type -> type == PythonTypes.Method },
        pythonTypes.count { type -> type == PythonTypes.Import },
    )
}

enum class PythonTypes {
    Import, Comment, Method, Unknown;
}


private fun toPythonType(line: String): PythonTypes {
    return when {
        line.isImport() -> PythonTypes.Import
        line.isComment() -> PythonTypes.Comment
        line.isMethod() -> PythonTypes.Method
        else -> PythonTypes.Unknown
    }
}

private fun String.isImport() = startWith("import") || startWith("from")
private fun String.isComment() = startWith("#")
private fun String.isMethod() = startWith("def")

private fun String.startWith(type: String) = trim { it <= ' ' }.startsWith(type)
