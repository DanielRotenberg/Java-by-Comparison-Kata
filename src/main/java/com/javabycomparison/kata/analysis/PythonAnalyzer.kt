package com.javabycomparison.kata.analysis

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

class PythonAnalyzer(private val file: Path) : Analyzer {
    @Throws(IOException::class)
    override fun analyze(): ResultData? {
        var number_of_imports = 0
        var lines_of_code = 0
        var number_of_methods = 0
        var comment_lines_of_code = 0

        val file_contents = Files.readAllLines(this.file)
        for (line in file_contents) {
            lines_of_code += 1
            if (line.trim { it <= ' ' }.startsWith("import")) {
                number_of_imports += 1
            }
            if (line.trim { it <= ' ' }.startsWith("from")) {
                number_of_imports += 1
                // In Python a comment starts with '#'
            } else if (line.trim { it <= ' ' }.startsWith("#")) {
                comment_lines_of_code += 1
                // In Python a method is defined with 'def'
            } else if (line.trim { it <= ' ' }.startsWith("def")) {
                number_of_methods += 1
            }
        }

        return ResultData(
            1,
            this.file.toString(),
            lines_of_code,
            comment_lines_of_code,
            number_of_methods,
            number_of_imports
        )
    }
}
