package com.javabycomparison.kata.analysis

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.IOException
import java.nio.file.Paths

internal class JavaAnalyzerTests {
    @Test
    fun analyze() {
        val javaAnalyzer = javaAnalyzer(null)
        Assertions.assertNull(javaAnalyzer)
    }

    @Test
    fun analyzeShouldThrowIOException() {
        val exception = assertThrows<IOException> { javaAnalyzer(Paths.get("./XXX_unavailable_directory/")) }
        Assertions.assertEquals("There was a problem reading a file!", exception.message)
    }

    @Test
    fun analyzeJavaFizzBuzz() {
        Assertions.assertEquals(
            FileSummary(0, "./src/main/resources/java_files/FizzBuzz.java", 15, 4, 0, 0),
            javaAnalyzer(Paths.get("./src/main/resources/java_files/FizzBuzz.java"))
        )
    }
}
