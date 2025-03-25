package com.javabycomparison.kata.analysis

data class FileSummary(
    val type: Int = 0,
    val name: String = "",
    val LOC: Int = 0,
    val commentLOC: Int = 0,
    val numMethod: Int = 0,
    val nImports: Int = 0,
    val L: Int = 0
) {
    init {
        name.replace("\\\\".toRegex(), "/")
    }
}

fun FileSummary.languageType(
    type: Int,
): String {
    return when (type) {
        0 -> "Java"
        1 -> "Python"
        else -> "other"
    }
}

fun printFileSummary(file: FileSummary): String {
    var language = file.languageType(file.type)
    return (file.name
            + "\t"
            + language
            + "\t"
            + file.L
            + "\t"
            + file.LOC
            + "\t"
            + file.commentLOC
            + "\t"
            + file.numMethod
            + "\t"
            + file.nImports)
}