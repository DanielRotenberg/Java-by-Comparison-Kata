package com.javabycomparison.kata.analysis

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

class AnalyzerImpl(private val file: Path) : Analyzer {
    override fun analyze(): ResultData? {
        try {
            val fileContents = Files.readAllLines(this.file)
            val l = fileContents.size
            return ResultData(2, this.file.toString(), l, 0, 0, 0)
        } catch (ioException: IOException) {
            return ResultData()
        }
    }
}
