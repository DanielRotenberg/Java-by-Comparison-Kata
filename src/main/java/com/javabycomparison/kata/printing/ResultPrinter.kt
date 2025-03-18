package com.javabycomparison.kata.printing

import com.javabycomparison.kata.analysis.ResultData
import com.javabycomparison.kata.analysis.ResultDataPrinter
import java.lang.String
import java.util.*
import kotlin.Array
import kotlin.Int
import kotlin.math.max
import kotlin.text.StringBuilder

object ResultPrinter {
    // These are the Strings of the metrics
    private const val FILE_NAME = "File Name"
    private const val LANGUAGE = "  Language"
    private const val LOC = "  Lines of Code"
    private const val COMMENT_LOC = "  Number of Comments"
    private const val NUM_METHODS = "  Number of Methods"
    private const val N_IMPORTS = "  Number of Imports"

    @JvmStatic
    fun printOverallResults(overallResult: Array<ResultData>) {
        val r1 = overallResult[0]
        val r2 = overallResult[1]

        val stringBuilderForHeader = StringBuilder()
        val stringBuilderForFirstResult = StringBuilder()
        val stringBuilderForSecondResult = StringBuilder()
        stringBuilderForHeader
            .append(
                String.join(
                    "",
                    Collections.nCopies<kotlin.String?>(
                        max((calculateFileNameLength(r1, r2) - FILE_NAME.length).toDouble(), 0.0).toInt(), " "
                    )
                )
            )
            .append(FILE_NAME)
        val rdp = ResultDataPrinter()
        stringBuilderForFirstResult.append(rdp.printFileName(r1, calculateFileNameLength(r1, r2)))
        stringBuilderForSecondResult.append(rdp.printFileName(r2, calculateFileNameLength(r1, r2)))
        stringBuilderForHeader
            .append(
                String.join(
                    "",
                    Collections.nCopies<kotlin.String?>(
                        max((calculateLanguageLength(r1, r2) - LANGUAGE.length).toDouble(), 0.0).toInt(), " "
                    )
                )
            )
            .append(LANGUAGE)
        stringBuilderForFirstResult.append(rdp.printLanguage(r1, calculateLanguageLength(r1, r2)))
        stringBuilderForSecondResult.append(rdp.printLanguage(r2, calculateLanguageLength(r1, r2)))
        stringBuilderForHeader
            .append(
                String.join(
                    "",
                    Collections.nCopies<kotlin.String?>(
                        max(
                            (calculateLOCLength(r1, r2) - LOC.length).toDouble(),
                            0.0
                        ).toInt(), " "
                    )
                )
            )
            .append(LOC)
        stringBuilderForFirstResult.append(rdp.printLOC(r1, calculateLOCLength(r1, r2)))
        stringBuilderForSecondResult.append(rdp.printLOC(r2, calculateLOCLength(r1, r2)))
        stringBuilderForHeader
            .append(
                String.join(
                    "",
                    Collections.nCopies<kotlin.String?>(
                        max((calculateCommentLOCLength(r1, r2) - COMMENT_LOC.length).toDouble(), 0.0).toInt(), " "
                    )
                )
            )
            .append(COMMENT_LOC)
        stringBuilderForFirstResult.append(rdp.printCommentLOC(r1, calculateCommentLOCLength(r1, r2)))
        stringBuilderForSecondResult.append(rdp.printCommentLOC(r2, calculateCommentLOCLength(r1, r2)))
        stringBuilderForHeader
            .append(
                String.join(
                    "",
                    Collections.nCopies<kotlin.String?>(
                        max((calculateNumMethodsLength(r1, r2) - NUM_METHODS.length).toDouble(), 0.0).toInt(), " "
                    )
                )
            )
            .append(NUM_METHODS)
        stringBuilderForFirstResult.append(
            rdp.printNumMethodLOC(r1, calculateNumMethodsLength(r1, r2))
        )
        stringBuilderForSecondResult.append(
            rdp.printNumMethodLOC(r2, calculateNumMethodsLength(r1, r2))
        )
        stringBuilderForHeader
            .append(
                String.join(
                    "",
                    Collections.nCopies<kotlin.String?>(
                        max((calculateNImportsLength(r1, r2) - N_IMPORTS.length).toDouble(), 0.0).toInt(), " "
                    )
                )
            )
            .append(N_IMPORTS)
        stringBuilderForFirstResult.append(rdp.printNImportsLOC(r1, calculateNImportsLength(r1, r2)))
        stringBuilderForSecondResult.append(rdp.printNImportsLOC(r2, calculateNImportsLength(r1, r2)))
        println(stringBuilderForHeader.toString())
        println(stringBuilderForFirstResult.toString())
        println(stringBuilderForSecondResult.toString())
    }

    private fun calculateFileNameLength(r1: ResultData, r2: ResultData): Int {
        // returns the length of the longest string of the three
        return max(
            max(r1.name.toString().length.toDouble(), r2.name.toString().length.toDouble()),
            FILE_NAME.length.toDouble()
        ).toInt()
    }

    private fun calculateLanguageLength(r1: ResultData, r2: ResultData): Int {
        val languageR1 = if ((r1.type == 0) == true) "Java" else "Python"
        val languageR2 = if ((r2.type == 0) == true) "Java" else "Python"

        // returns the length of the longest string of the three
        return max(max(languageR1.length.toDouble(), languageR2.length.toDouble()), LANGUAGE.length.toDouble()).toInt()
    }

    private fun calculateLOCLength(r1: ResultData, r2: ResultData): Int {
        // returns the length of the longest string of the three
        return max(
            max(r1.LOC.toString().length.toDouble(), r2.LOC.toString().length.toDouble()), LOC.length.toDouble()
        ).toInt()
    }

    private fun calculateCommentLOCLength(r1: ResultData, r2: ResultData): Int {
        // returns the length of the longest string of the three
        return max(
            max(r1.commentLOC.toString().length.toDouble(), r2.commentLOC.toString().length.toDouble()),
            COMMENT_LOC.length.toDouble()
        ).toInt()
    }

    private fun calculateNumMethodsLength(r1: ResultData, r2: ResultData): Int {
        // returns the length of the longest string of the three
        return max(
            max(r1.numMethod.toString().length.toDouble(), r2.numMethod.toString().length.toDouble()),
            NUM_METHODS.length.toDouble()
        ).toInt()
    }

    private fun calculateNImportsLength(r1: ResultData, r2: ResultData): Int {
        // returns the length of the longest string of the three
        return max(
            max(r1.nImports.toString().length.toDouble(), r2.nImports.toString().length.toDouble()),
            N_IMPORTS.length.toDouble()
        ).toInt()
    }
}
