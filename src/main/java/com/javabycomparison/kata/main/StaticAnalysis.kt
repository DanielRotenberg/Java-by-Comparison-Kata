package com.javabycomparison.kata.main

import com.javabycomparison.kata.analysis.FileSummary
import com.javabycomparison.kata.printing.printFileSummary
import com.javabycomparison.kata.printing.CSVPrinter
import com.javabycomparison.kata.printing.ResultPrinter
import com.javabycomparison.kata.search.SearchClient
import java.io.IOException
import java.util.*

class StaticAnalysis {
    private fun run(directoryPath: String, smry: Boolean): Array<FileSummary?>? {
        val results: LinkedList<FileSummary>? = SearchClient(smry).collectAllFiles(directoryPath) as LinkedList<FileSummary>?
        if (results != null) {
            if (results.size != 0) {
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

                var l = 0
                while (l < results.size) {
                    val resultData = results.get(l)
                    if (!smry) {
                        println(printFileSummary(resultData))
                    }
                    if (resultData.type == 0) {
                        javaLOC += resultData.LOC
                        javaCommentLOC += resultData.commentLOC
                        javaNumMethod += resultData.numMethod
                        javanImports += resultData.nImports
                    } else if (resultData.type == 1) {
                        pyLOC += resultData.LOC
                        pyCommentLOC += resultData.commentLOC
                        pyNumMethod += resultData.numMethod
                        pynImports += resultData.nImports
                    } else {
                        LOC += resultData.LOC
                        commentLOC += resultData.commentLOC
                        numMethod += resultData.numMethod
                        nImports += resultData.nImports
                    }
                    l = l + 1
                }
                return arrayOf<FileSummary>(
                    FileSummary(0, "Overall Java", javaLOC, javaCommentLOC, javaNumMethod, javanImports),
                    FileSummary(1, "Overall Python", pyLOC, pyCommentLOC, pyNumMethod, pynImports),
                    FileSummary(2, "Overall Other", LOC, commentLOC, numMethod, nImports),
                ) as Array<FileSummary?>?
            } else {
                return arrayOf<FileSummary>(FileSummary()) as Array<FileSummary?>?
            }
        }
        System.err.println("There was a problem with the result!")

        return null
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            analyze(if (args.size == 0) null else args[0], if (args.size == 2) (args[1] == "smry") else false)
        }

        private fun analyze(p: String?, smry: Boolean) {
            val overallResult = StaticAnalysis().run(if (p == null) "./src/" else p, smry)
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
