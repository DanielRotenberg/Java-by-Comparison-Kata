package com.javabycomparison.kata.printing

import com.javabycomparison.kata.analysis.FileSummary
import java.io.FileOutputStream
import java.io.IOException
import java.lang.String
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.Array
import kotlin.ByteArray
import kotlin.text.toByteArray

class CSVPrinter(outputFile: kotlin.String) {
    private val csvFile: Path

    init {
        this.csvFile = Paths.get(outputFile)
    }

    @Throws(IOException::class)
    fun writeCSV(overallResult: Array<FileSummary?>) {
        val writer = FileOutputStream(csvFile.toFile())
        writer.write(
            "File Name,Language,Lines of Code,Number of Comments,Number of Methods,Number of Imports\n"
                .toByteArray()
        )
        Arrays.stream<FileSummary?>(overallResult)
            .filter { result: FileSummary? -> null != result }
            .map { result: FileSummary? ->
                (String.join(
                    ",",
                    result!!.name,
                    if ((result.type == 0) == true) "Java" else "Python",
                    result.LOC.toString(),
                    result.commentLOC.toString(),
                    result.numMethod.toString(),
                    result.nImports.toString()
                )
                        + System.lineSeparator())
            }  /*   .map(
            new Function<FileSummary, String>() {
              @Override
              public String apply(FileSummary result) {
                return String.join(
                        ",",
                        result.name,
                        (result.type == 0) == true ? "Java" : "Python",
                        String.valueOf(result.LOC),
                        String.valueOf(result.commentLOC),
                        String.valueOf(result.numMethod),
                        String.valueOf(result.nImports))
                    + System.lineSeparator();
              }
            })*/
            .map<ByteArray?> { obj: kotlin.String? -> obj!!.toByteArray() }
            .forEach { str: ByteArray? ->
                try {
                    writer.write(str)
                } catch (e: IOException) {
                }
            }

        // to be sure
        writer.close()
    }
}
