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
                            (calculateLOCLength(r1.LOC, r2.LOC) - LOC.length).toDouble(),
                            0.0
                        ).toInt(), " "
                    )
                )
            )
            .append(LOC)
        stringBuilderForFirstResult.append(rdp.printLOC(r1, calculateLOCLength(r1.LOC, r2.LOC)))
        stringBuilderForSecondResult.append(rdp.printLOC(r2, calculateLOCLength(r1.LOC, r2.LOC)))
        stringBuilderForHeader
            .append(
                String.join(
                    "",
                    Collections.nCopies<kotlin.String?>(
                        max(
                            (calculateCommentLOCLength(r1.commentLOC, r2.commentLOC) - COMMENT_LOC.length).toDouble(),
                            0.0
                        ).toInt(), " "
                    )
                )
            )
            .append(COMMENT_LOC)
        stringBuilderForFirstResult.append(
            rdp.printCommentLOC(
                r1, calculateCommentLOCLength(
                    r1.commentLOC,
                    r2.commentLOC
                )
            )
        )
        stringBuilderForSecondResult.append(
            rdp.printCommentLOC(
                r2, calculateCommentLOCLength(
                    r1.commentLOC,
                    r2.commentLOC
                )
            )
        )
        stringBuilderForHeader
            .append(
                String.join(
                    "",
                    Collections.nCopies<kotlin.String?>(
                        max(
                            (calculateNumMethodsLength(r1.numMethod, r2.numMethod) - NUM_METHODS.length).toDouble(),
                            0.0
                        ).toInt(), " "
                    )
                )
            )
            .append(NUM_METHODS)
        stringBuilderForFirstResult.append(
            rdp.printNumMethodLOC(r1, calculateNumMethodsLength(r1.numMethod, r2.numMethod))
        )
        stringBuilderForSecondResult.append(
            rdp.printNumMethodLOC(r2, calculateNumMethodsLength(r1.numMethod, r2.numMethod))
        )
        stringBuilderForHeader
            .append(
                String.join(
                    "",
                    Collections.nCopies<kotlin.String?>(
                        max(
                            (calculateNImportsLength(r1.nImports, r2.nImports) - N_IMPORTS.length).toDouble(),
                            0.0
                        ).toInt(), " "
                    )
                )
            )
            .append(N_IMPORTS)
        stringBuilderForFirstResult.append(rdp.printNImportsLOC(r1, calculateNImportsLength(r1.nImports, r2.nImports)))
        stringBuilderForSecondResult.append(rdp.printNImportsLOC(r2, calculateNImportsLength(r1.nImports, r2.nImports)))
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
        fun detectLanguageType(type: Int): kotlin.String = if ((type == 0) == true) "Java" else "Python"

        val languageR1 = detectLanguageType(r1.type)
        val languageR2 = detectLanguageType(r2.type)

        return longestLengthOf(languageR1.length,languageR2.length,LANGUAGE)
        // returns the length of the longest string of the three
    }

    private fun calculateLOCLength(LOC1: Int, LOC2: Int): Int =
        // returns the length of the longest string of the three
        longestLengthOf(LOC1, LOC2,LOC)

    private fun calculateCommentLOCLength(commentLOC1: Int, commentLOC2: Int): Int =
        // returns the length of the longest string of the three
        longestLengthOf(commentLOC1, commentLOC2, COMMENT_LOC)

    private fun calculateNumMethodsLength(numMethod1: Int, numMethod2: Int): Int =
        // returns the length of the longest string of the three
        longestLengthOf(numMethod1, numMethod2, NUM_METHODS)

    private fun calculateNImportsLength(nImports1: Int, nImports2: Int): Int =
        // returns the length of the longest string of the three
        longestLengthOf(nImports1, nImports2, N_IMPORTS)

    private fun longestLengthOf(
        nImports1: Int,
        nImports2: Int,
        type: kotlin.String
    ): Int {
        return max(
            max(nImports1.toString().length.toDouble(), nImports2.toString().length.toDouble()),
            type.length.toDouble()
        ).toInt()
    }
}
