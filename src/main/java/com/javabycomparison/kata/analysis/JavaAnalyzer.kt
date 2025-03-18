package com.javabycomparison.kata.analysis

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

class JavaAnalyzer(private val file: Path?) : Analyzer {
    @Throws(IOException::class)
    override fun analyze(): ResultData? {
        if (file != null) {
            var imports = 0
            var LoC = 0
            var commentsLoC = 0

            try {
                val reader = Files.newBufferedReader(this.file)

                var line: String?
                while ((reader.readLine().also { line = it }) != null) {
                    LoC += 1
                    if (line!!.trim { it <= ' ' }.startsWith("import")) {
                        imports += 1
                    } else if (line.trim { it <= ' ' }.startsWith("//")
                        || line.trim { it <= ' ' }.startsWith("*")
                        || line.trim { it <= ' ' }.startsWith("/*")
                    ) {
                        commentsLoC += 1
                    }
                }
                // It is impossible to detect the number of methods at the moment.
                return ResultData(0, this.file.toString(), LoC, commentsLoC, 0, imports)
            } catch (ioe: IOException) {
                throw IOException("There was a problem reading a file!")
            }
        } else {
            return null
        }
    }
}
