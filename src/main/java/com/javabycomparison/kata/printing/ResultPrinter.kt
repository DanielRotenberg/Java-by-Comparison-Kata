package com.javabycomparison.kata.printing

import com.javabycomparison.kata.analysis.ResultData
import com.javabycomparison.kata.analysis.ResultDataPrinter
import com.javabycomparison.kata.analysis.languageType
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

        appendFileToHeader(stringBuilderForHeader, r1, r2)

        val resultDataPrinter = ResultDataPrinter()
        stringBuilderForFirstResult.append(resultDataPrinter.printFileName(r1.name!!, calculateFileNameLength(r1, r2)))
        stringBuilderForSecondResult.append(resultDataPrinter.printFileName(r2.name!!, calculateFileNameLength(r1, r2)))

        appendLanguageToHeader(stringBuilderForHeader, r1, r2)
        val languageR1 = detectLanguageType(r1.type)
        val languageR2 = detectLanguageType(r2.type)

        stringBuilderForFirstResult.append(resultDataPrinter.printLanguage(
            languageType(r1),
            calculateLanguageLength(languageR1, languageR2)
        ))
        stringBuilderForSecondResult.append(resultDataPrinter.printLanguage(
            languageType(r2),
            calculateLanguageLength(languageR1, languageR2)
        ))

        appendLOCToHeader(stringBuilderForHeader, r1.LOC, r2.LOC, this@ResultPrinter.LOC)

        stringBuilderForFirstResult.append(resultDataPrinter.printLOC(r1.LOC, calculateLOCLength(r1.LOC, r2.LOC)))
        stringBuilderForSecondResult.append(resultDataPrinter.printLOC(r2.LOC, calculateLOCLength(r1.LOC, r2.LOC)))

        appendCommentLOCToHeader(stringBuilderForHeader, r1.commentLOC, r2.commentLOC, this@ResultPrinter.COMMENT_LOC)

        stringBuilderForFirstResult.append(
            resultDataPrinter.printCommentLOC(
                r1.commentLOC.toString(), calculateCommentLOCLength(
                    r1.commentLOC,
                    r2.commentLOC
                )
            )
        )
        stringBuilderForSecondResult.append(
            resultDataPrinter.printCommentLOC(
                r2.commentLOC.toString(), calculateCommentLOCLength(
                    r1.commentLOC,
                    r2.commentLOC
                )
            )
        )

        appendNumMethodsToHeader(stringBuilderForHeader, r1.numMethod, r2.numMethod, this@ResultPrinter.NUM_METHODS)

        stringBuilderForFirstResult.append(
            resultDataPrinter.printNumMethodLOC(r1.numMethod, calculateNumMethodsLength(r1.numMethod, r2.numMethod))
        )
        stringBuilderForSecondResult.append(
            resultDataPrinter.printNumMethodLOC(r2.numMethod, calculateNumMethodsLength(r1.numMethod, r2.numMethod))
        )

        appendImportsToHeader(stringBuilderForHeader, r1.nImports, r2.nImports, this@ResultPrinter.N_IMPORTS)

        stringBuilderForFirstResult.append(resultDataPrinter.printNImportsLOC(
            r1.nImports,
            calculateNImportsLength(r1.nImports, r2.nImports)
        ))
        stringBuilderForSecondResult.append(resultDataPrinter.printNImportsLOC(
            r2.nImports,
            calculateNImportsLength(r1.nImports, r2.nImports)
        ))

        println(stringBuilderForHeader.toString())
        println(stringBuilderForFirstResult.toString())
        println(stringBuilderForSecondResult.toString())
    }

    private fun appendImportsToHeader(
        stringBuilderForHeader: StringBuilder,
        nImports1: Int,
        nImports2: Int,
        type: kotlin.String
    ) {
        appendToHeader(stringBuilderForHeader, nImports1, nImports2, type)
    }

    private fun appendNumMethodsToHeader(
        stringBuilderForHeader: StringBuilder,
        numMethod1: Int,
        numMethod2: Int,
        type: kotlin.String
    ) {
        appendToHeader(stringBuilderForHeader, numMethod1, numMethod2, type)
    }

    private fun appendCommentLOCToHeader(
        stringBuilderForHeader: StringBuilder,
        commentLOC1: Int,
        commentLOC2: Int,
        commentLOC: kotlin.String
    ) {

        appendToHeader(stringBuilderForHeader, commentLOC1, commentLOC2, commentLOC)
    }

    private fun appendToHeader(
        stringBuilderForHeader: StringBuilder,
        n1: Int,
        n2: Int,
        type: kotlin.String
    ) {

        stringBuilderForHeader
            .append(
                String.join(
                    "",
                    Collections.nCopies<kotlin.String?>(
                        max(
                            (calculateNImportsLength(n1, n2) - type.length).toDouble(),
                            0.0
                        ).toInt(), " "
                    )
                )
            )
            .append(type)

    }

    private fun appendLOCToHeader(
        stringBuilderForHeader: StringBuilder,
        LOC1: Int,
        LOC2: Int,
        type: kotlin.String
    ) {
//        appendToHeader(stringBuilderForHeader, LOC1, LOC2, type)
               stringBuilderForHeader
                   .append(
                       String.join(
                           "",
                           Collections.nCopies<kotlin.String?>(
                               max(
                                   (calculateLOCLength(LOC1, LOC2) - type.length).toDouble(),
                                   0.0
                               ).toInt(), " "
                           )
                       )
                   )
                   .append(type)
    }

    private fun appendLanguageToHeader(
        stringBuilderForHeader: StringBuilder,
        r1: ResultData,
        r2: ResultData
    ) {
        val languageR1 = detectLanguageType(r1.type)
        val languageR2 = detectLanguageType(r2.type)


        stringBuilderForHeader
            .append(
                String.join(
                    "",
                    Collections.nCopies<kotlin.String?>(
                        max(( longestLengthOf(languageR1.length, languageR2.length, LANGUAGE) - LANGUAGE.length).toDouble(), 0.0).toInt(), " "
                    )
                )
            )
            .append(LANGUAGE)
    }

    private fun appendFileToHeader(
        stringBuilderForHeader: StringBuilder,
        r1: ResultData,
        r2: ResultData
    ) {
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
    }

    private fun calculateFileNameLength(r1: ResultData, r2: ResultData): Int =
        // returns the length of the longest string of the three
        longestLengthOf(r1.name!!,r2.name!!,FILE_NAME)

    private fun calculateLanguageLength(language1: kotlin.String, language2: kotlin.String): Int {
        return longestLengthOf(language1.length, language2.length, LANGUAGE)
        // returns the length of the longest string of the three
    }

    private fun calculateLOCLength(LOC1: Int, LOC2: Int): Int =
        // returns the length of the longest string of the three
        longestLengthOf(LOC1, LOC2, LOC)

    private fun calculateCommentLOCLength(commentLOC1: Int, commentLOC2: Int): Int =
        // returns the length of the longest string of the three
        longestLengthOf(commentLOC1, commentLOC2, COMMENT_LOC)

    private fun calculateNumMethodsLength(numMethod1: Int, numMethod2: Int): Int =
        // returns the length of the longest string of the three
        longestLengthOf(numMethod1, numMethod2, NUM_METHODS)

    private fun calculateNImportsLength(nImports1: Int, nImports2: Int): Int =
        // returns the length of the longest string of the three
        longestLengthOf(nImports1, nImports2, N_IMPORTS)

    //to-do -> remove this function
    private fun longestLengthOf(
        nImports1: Int,
        nImports2: Int,
        type: kotlin.String
    ): Int = longestLengthOf(nImports1.toString(),nImports2.toString(),type)

    private fun longestLengthOf(
        nImports1: kotlin.String,
        nImports2: kotlin.String,
        type: kotlin.String
    ): Int {
        return max(
            max(nImports1.length.toDouble(), nImports2.length.toDouble()),
            type.length.toDouble()
        ).toInt()
    }
}

fun detectLanguageType(type: Int): kotlin.String = if (type == 0) "Java" else "Python"
