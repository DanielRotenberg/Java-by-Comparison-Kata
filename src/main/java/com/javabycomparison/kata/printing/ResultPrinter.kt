package com.javabycomparison.kata.printing

import com.javabycomparison.kata.analysis.FileSummary
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
    fun printOverallResults(overallResult: Array<FileSummary>) {
        val r1 = overallResult[0]
        val r2 = overallResult[1]

        val stringBuilderForHeader = StringBuilder()
        val stringBuilderForFirstResult = StringBuilder()
        val stringBuilderForSecondResult = StringBuilder()

        appendFileToHeader(stringBuilderForHeader, r1.name!!, r2.name!!)
        appendToBody(stringBuilderForFirstResult,r1.name!!,r2.name!!,FILE_NAME)
        appendToBody(stringBuilderForSecondResult,r2.name!!,r1.name!!,FILE_NAME)



        val languageR1 = languageType(r1.type)
        val languageR2 = languageType(r2.type)
        appendLanguageToHeader(stringBuilderForHeader, languageR2, languageR2)
        appendToBody(stringBuilderForFirstResult,languageR1,languageR2,LANGUAGE)
        appendToBody(stringBuilderForSecondResult,languageR2,languageR1,LANGUAGE)

        appendLOCToHeader(stringBuilderForHeader, r1.LOC, r2.LOC, this@ResultPrinter.LOC)
        appendToBody(stringBuilderForFirstResult,r1.LOC.toString(),r2.LOC.toString(),LOC)
        appendToBody(stringBuilderForSecondResult,r2.LOC.toString(),r1.LOC.toString(),LOC)

        appendCommentLOCToHeader(stringBuilderForHeader, r1.commentLOC, r2.commentLOC, this@ResultPrinter.COMMENT_LOC)
        appendToBody(stringBuilderForFirstResult,r1.commentLOC.toString(),r2.commentLOC.toString(),COMMENT_LOC)
        appendToBody(stringBuilderForSecondResult,r2.commentLOC.toString(),r1.commentLOC.toString(),COMMENT_LOC)

        appendNumMethodsToHeader(stringBuilderForHeader, r1.numMethod, r2.numMethod, this@ResultPrinter.NUM_METHODS)
        appendToBody(stringBuilderForFirstResult,r1.numMethod.toString(),r2.numMethod.toString(),NUM_METHODS)
        appendToBody(stringBuilderForSecondResult,r2.numMethod.toString(),r1.numMethod.toString(),NUM_METHODS)

        appendImportsToHeader(stringBuilderForHeader, r1.nImports, r2.nImports, this@ResultPrinter.N_IMPORTS)
        appendToBody(stringBuilderForFirstResult,r1.nImports.toString(),r2.nImports.toString(),N_IMPORTS)
        appendToBody(stringBuilderForSecondResult,r2.nImports.toString(),r1.nImports.toString(),N_IMPORTS)


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
        appendToHeader(stringBuilderForHeader, nImports1.toString(), nImports2.toString(), type)
    }

    private fun appendNumMethodsToHeader(
        stringBuilderForHeader: StringBuilder,
        numMethod1: Int,
        numMethod2: Int,
        type: kotlin.String
    ) {
        appendToHeader(stringBuilderForHeader, numMethod1.toString(), numMethod2.toString(), type)
    }

    private fun appendCommentLOCToHeader(
        stringBuilderForHeader: StringBuilder,
        commentLOC1: Int,
        commentLOC2: Int,
        commentLOC: kotlin.String
    ) {

        appendToHeader(stringBuilderForHeader, commentLOC1.toString(), commentLOC2.toString(), commentLOC)
    }

    private fun appendToHeader(
        stringBuilderForHeader: StringBuilder,
        n1: kotlin.String,
        n2: kotlin.String,
        type: kotlin.String
    ) {

        stringBuilderForHeader
            .append(
                String.join(
                    "",
                    Collections.nCopies<kotlin.String?>(
                        max(
                            (longestLengthOf(n1, n2, type) - type.length).toDouble(),
                            0.0
                        ).toInt(), " "
                    )
                )
            )
            .append(type)

    }

    fun appendToBody(builder: StringBuilder,param1:kotlin.String,param2: kotlin.String,type: kotlin.String){
        builder.append(
            maxLengthOf(param1, longestLengthOf(param1,param2,type))
        )
    }



    private fun appendLOCToHeader(
        stringBuilderForHeader: StringBuilder,
        LOC1: Int,
        LOC2: Int,
        type: kotlin.String
    ) {
        appendToHeader(stringBuilderForHeader, LOC1.toString(), LOC2.toString(), type)
    }

    private fun appendLanguageToHeader(
        stringBuilderForHeader: StringBuilder,
        languageR1: kotlin.String,
        languageR2: kotlin.String,
    ) {
        appendToHeader(stringBuilderForHeader, languageR1, languageR2, LANGUAGE)

    }

    private fun appendFileToHeader(
        stringBuilderForHeader: StringBuilder,
        name1: kotlin.String,
        name2: kotlin.String
    ) {
        appendToHeader(stringBuilderForHeader, name1, name2, FILE_NAME)
    }

    private fun calculateFileNameLength(name1: kotlin.String, name2: kotlin.String): Int {
        // returns the length of the longest string of the three
        return longestLengthOf(name1, name2, FILE_NAME)
    }

    private fun calculateLanguageLength(language1: kotlin.String, language2: kotlin.String): Int {
        return longestLengthOf(language1.length.toString(), language2.length.toString(), LANGUAGE)
        // returns the length of the longest string of the three
    }

    private fun calculateLOCLength(LOC1: Int, LOC2: Int): Int =
        // returns the length of the longest string of the three
        longestLengthOf(LOC1.toString(), LOC2.toString(), LOC)

    private fun calculateCommentLOCLength(commentLOC1: Int, commentLOC2: Int): Int =
        // returns the length of the longest string of the three
        longestLengthOf(commentLOC1.toString(), commentLOC2.toString(), COMMENT_LOC)

    private fun calculateNumMethodsLength(numMethod1: Int, numMethod2: Int): Int =
        // returns the length of the longest string of the three
        longestLengthOf(numMethod1.toString(), numMethod2.toString(), NUM_METHODS)

    private fun calculateNImportsLength(nImports1: Int, nImports2: Int): Int =
        // returns the length of the longest string of the three
        longestLengthOf(nImports1.toString(), nImports2.toString(), N_IMPORTS)

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

    private fun appendToBuilder(
        stringBuilder: StringBuilder,
        n1: kotlin.String,
        n2: kotlin.String,
        type: kotlin.String
    ) {

        stringBuilder
            .append(
                String.join(
                    "",
                    Collections.nCopies<kotlin.String?>(
                        max(
                            (longestLengthOf(n1, n2, type) - type.length).toDouble(),
                            0.0
                        ).toInt(), " "
                    )
                )
            )
            .append(type)

    }





}

fun detectLanguageType(type: Int): kotlin.String = if (type == 0) "Java" else "Python"


fun maxLengthOf(numMethod: kotlin.String, length: Int): kotlin.String {
    return (String.join(
        "",
        Collections.nCopies<kotlin.String?>(
            max(
                (length - numMethod.length).toDouble(),
                0.0
            ).toInt(), " "
        )
    )
            + numMethod)
}