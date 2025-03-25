package com.javabycomparison.kata.analysis

import com.javabycomparison.kata.main.StaticAnalysis
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

internal class IntegrationTests {
    @Test
    fun testSmry() {
        val output: String =
            capture(
                object : Runnable {
                    override fun run() {
                        val array = arrayOfNulls<String>(2)
                        array[0] = "src/main/resources"
                        array[1] = "smry"
                        StaticAnalysis.main(arrayOf("src/main/resources", "smry"))
                    }
                })
        Assertions.assertEquals(
            ("     File Name  Language  Lines of Code  Number of Comments  Number of Methods  Number of Imports\n"
                    + "  Overall Java      Java             15                   4                  0                  0\n"
                    + "Overall Python    Python             16                   3                  3                  0\n"),
            output.replace("\\\\".toRegex(), "/").replace("\\r\\n".toRegex(), "\n")
        )
    }

    @Test
    fun testJava() {
        val output: String =
            capture(
                object : Runnable {
                    override fun run() {
                        val array = arrayOfNulls<String>(1)
                        array[0] = "src/main/resources/java_files"
                        StaticAnalysis.main(arrayOf("src/main/resources/java_files"))
                    }
                })
        Assertions.assertEquals(
            ("Skipping directory src/main/resources/java_files.\n"
                    + "File src/main/resources/java_files/FizzBuzz.java is a Java file. It will be analyzed.\n"
                    + "src/main/resources/java_files/FizzBuzz.java\tJava\t0\t15\t4\t0\t0\n"
                    + "     File Name  Language  Lines of Code  Number of Comments  Number of Methods  Number of Imports\n"
                    + "  Overall Java      Java             15                   4                  0                  0\n"
                    + "Overall Python    Python              0                   0                  0                  0\n"),
            output.replace("\\\\".toRegex(), "/").replace("\\r\\n".toRegex(), "\n")
        )
    }

    @Test
    fun testPython() {
        val output: String =
            capture(
                object : Runnable {
                    override fun run() {
                        StaticAnalysis.main(arrayOf("src/main/resources/python_files"))
                    }
                })
        Assertions.assertEquals(
            ("Skipping directory src/main/resources/python_files.\n"
                    + "File src/main/resources/python_files/function.py is a Python file. It will be analyzed.\n"
                    + "src/main/resources/python_files/function.py\tPython\t0\t16\t3\t3\t0\n"
                    + "     File Name  Language  Lines of Code  Number of Comments  Number of Methods  Number of Imports\n"
                    + "  Overall Java      Java              0                   0                  0                  0\n"
                    + "Overall Python    Python             16                   3                  3                  0\n"),
            output.replace("\\\\".toRegex(), "/").replace("\\r\\n".toRegex(), "\n")
        )
    }

    companion object {
        private fun capture(runnable: Runnable): String {
            val baos = ByteArrayOutputStream()
            val capture = PrintStream(baos)
            val console = System.out
            System.setOut(capture)
            try {
                runnable.run()
            } finally {
                System.setOut(console)
            }
            return baos.toString()
        }
    }
}
