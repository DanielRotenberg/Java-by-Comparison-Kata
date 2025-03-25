package com.javabycomparison.kata.main

import com.javabycomparison.kata.analysis.FileSummary
import com.javabycomparison.kata.analysis.printFileSummary
import com.javabycomparison.kata.printing.CSVPrinter
import com.javabycomparison.kata.printing.ResultPrinter
import com.javabycomparison.kata.search.SearchClient
import java.io.IOException
import java.util.*
import kotlin.collections.forEach

class StaticAnalysis {
    private fun run(directoryPath: String, summary: Boolean): Array<FileSummary?>? {
        val results: LinkedList<FileSummary>? =
            SearchClient(summary).collectAllFiles(directoryPath) as LinkedList<FileSummary>?
        return when {
            results == null -> {
                System.err.println("There was a problem with the result!")
                null
            }

            results.isEmpty() -> {
                arrayOf<FileSummary>(FileSummary()) as Array<FileSummary?>?
            }

            else -> {
                summaries(results, summary)
            }
        }
    }

    // language summary and file summary
    private fun summaries(
        files: LinkedList<FileSummary>,
        summary: Boolean
    ): Array<FileSummary?>? {
        var javaLOC = 0
        var javaCommentLOC = 0
        var javaNumMethod = 0
        var javanImports = 0

        var pyLOC = 0
        var pyCommentLOC = 0
        var pyNumMethod = 0
        var pynImports = 0

        var LOC = 0
        var commentLOC = 0
        var numMethod = 0
        var nImports = 0
        // result
        files.onEach { file ->
            if (!summary) {
                println(printFileSummary(file))
            }
        }
        return languageSummary(files)
    }

    fun languageSummary( files: LinkedList<FileSummary>): Array<FileSummary?>? {
        var javaLOC = 0
        var javaCommentLOC = 0
        var javaNumMethod = 0
        var javanImports = 0

        var pyLOC = 0
        var pyCommentLOC = 0
        var pyNumMethod = 0
        var pynImports = 0
        files.forEach { file ->

            if (file.type == 0) {
                javaLOC += file.LOC
                javaCommentLOC += file.commentLOC
                javaNumMethod += file.numMethod
                javanImports += file.nImports
            } else if (file.type == 1) {
                pyLOC += file.LOC
                pyCommentLOC += file.commentLOC
                pyNumMethod += file.numMethod
                pynImports += file.nImports
            } /*else {
                LOC += file.LOC
                commentLOC += file.commentLOC
                numMethod += file.numMethod
                nImports += file.nImports
            }*/
        }

        return arrayOf<FileSummary>(
            FileSummary(0, "Overall Java", javaLOC, javaCommentLOC, javaNumMethod, javanImports),
            FileSummary(1, "Overall Python", pyLOC, pyCommentLOC, pyNumMethod, pynImports),
//            FileSummary(2, "Overall Other", LOC, commentLOC, numMethod, nImports),
        ) as Array<FileSummary?>?
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val directoryForAnalysis = if (args.isEmpty()) null else args[0]
            val isSummary = if (args.size == 2) (args[1] == "smry") else false

            analyze(
                directoryForAnalysis,
                isSummary
            )
        }

        private fun analyze(directory: String?, smry: Boolean) {
            val overallResult = StaticAnalysis().run(directory ?: "./src/", smry)
            if (overallResult == null) {
                System.err.println("Something went terribly wrong")
                return
            }

            ResultPrinter.printOverallResults(overallResult as Array<FileSummary>)
            try {
                CSVPrinter("output.csv").writeCSV(overallResult)
            } catch (e: IOException) {
                System.err.println("Something went a bit wrong")
            }
        }
    }
}
