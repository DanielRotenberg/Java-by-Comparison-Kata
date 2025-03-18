package com.javabycomparison.kata.analysis

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.IOException
import java.nio.file.Paths

internal class JavaAnalyzerTests {
    @Test
    @Throws(IOException::class)
    fun analyze() {
        val javaAnalyzer = JavaAnalyzer(null)
        Assertions.assertNotNull(javaAnalyzer)
        Assertions.assertNull(javaAnalyzer.analyze())
    }

    @Test
    fun analyzeShouldThrowIOException() {
        val javaAnalyzer = JavaAnalyzer(Paths.get("./XXX_unavailable_directory/"))
        try {
            javaAnalyzer.analyze()
        } catch (ioe: IOException) {
        }
    }

    @Test
    @Throws(IOException::class)
    fun analyzeJavaFizzBuzz() {
        Assertions.assertEquals(
            ResultData(0, "./src/main/resources/java_files/FizzBuzz.java", 15, 4, 0, 0),
            JavaAnalyzer(Paths.get("./src/main/resources/java_files/FizzBuzz.java")).analyze()
        )
    }
}
