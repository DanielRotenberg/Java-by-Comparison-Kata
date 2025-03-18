package com.javabycomparison.kata.analysis

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException
import java.nio.file.Paths

internal class PythonAnalyzerTests {
    private var pythonResult: ResultData? = null

    @BeforeEach
    @Throws(IOException::class)
    fun analyzePython() {
        pythonResult =
            PythonAnalyzer(Paths.get("./src/main/resources/python_files/function.py")).analyze()
    }

    @Test
    fun analyzePythonFile() {
        Assertions.assertEquals(
            pythonResult,
            ResultData(1, "./src/main/resources/python_files/function.py", 16, 3, 3, 0)
        )
    }

    @Test
    fun analyzeFractionOfComments() {
        Assertions.assertEquals(5.66, (pythonResult!!.LOC.toFloat() / pythonResult!!.commentLOC).toDouble(), 1.5)
    }
}
