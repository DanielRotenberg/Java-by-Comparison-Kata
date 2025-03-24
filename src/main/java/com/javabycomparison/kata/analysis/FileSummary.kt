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
