package com.javabycomparison.kata.analysis

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

class JavaAnalyzer(private val file: Path?) : Analyzer {
    @Throws(IOException::class)
    override fun analyze(): ResultData? {
        if (file == null) {
            return null
        }
        try {

            val javaTypes = Files.readAllLines(file).map(::toJavaType)
            return ResultData(
                0,
                this.file.toString(),
                javaTypes.count(),
                javaTypes.count { type -> type == JavaTypes.Comment },
                0,  // It is impossible to detect the number of methods at the moment.
                javaTypes.count { type -> type == JavaTypes.Import },
            )
        } catch (ioe: IOException) {
            throw IOException("There was a problem reading a file!")
        }

    }

}

enum class JavaTypes {
    Import, Comment, Unknown;
}

private fun toJavaType(line: String): JavaTypes {
    return when {
        line.isImport() -> JavaTypes.Import
        line.isComment() -> JavaTypes.Comment
        else -> JavaTypes.Unknown
    }
}

private fun String.isImport() = startWith("import")
private fun String.isComment() = startWith("//") || startWith("*") || startWith("/*")


private fun String.startWith(type: String) = trim { it <= ' ' }.startsWith(type)