package com.javabycomparison.kata.printing

import com.javabycomparison.kata.analysis.FileSummary
import com.javabycomparison.kata.analysis.languageType
import java.lang.String
import java.util.*
import java.util.Collections.nCopies
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

        val stringBuilderForFirstResult = StringBuilder()
        val stringBuilderForSecondResult = StringBuilder()


        appendToBuilder(stringBuilderForFirstResult, r1.name!!, r2.name!!, FILE_NAME)
       /* println("-----------------------")
        println(stringBuilderForFirstResult)
        println("-----------------------")*/

        appendToBuilder(stringBuilderForSecondResult, r2.name!!, r1.name!!, FILE_NAME)


        val languageR1 = languageType(r1.type)
        val languageR2 = languageType(r2.type)
        appendToBuilder(stringBuilderForFirstResult, languageR1, languageR2, LANGUAGE)

      /*  println("-----------------------")
        println(stringBuilderForFirstResult)
        println("-----------------------")*/
        appendToBuilder(stringBuilderForSecondResult, languageR2, languageR1, LANGUAGE)

        appendToBuilder(stringBuilderForFirstResult, r1.LOC.toString(), r2.LOC.toString(), LOC)
        appendToBuilder(stringBuilderForSecondResult, r2.LOC.toString(), r1.LOC.toString(), LOC)

        appendToBuilder(stringBuilderForFirstResult, r1.commentLOC.toString(), r2.commentLOC.toString(), COMMENT_LOC)
        appendToBuilder(stringBuilderForSecondResult, r2.commentLOC.toString(), r1.commentLOC.toString(), COMMENT_LOC)

        appendToBuilder(stringBuilderForFirstResult, r1.numMethod.toString(), r2.numMethod.toString(), NUM_METHODS)
        appendToBuilder(stringBuilderForSecondResult, r2.numMethod.toString(), r1.numMethod.toString(), NUM_METHODS)

        appendToBuilder(stringBuilderForFirstResult, r1.nImports.toString(), r2.nImports.toString(), N_IMPORTS)
        appendToBuilder(stringBuilderForSecondResult, r2.nImports.toString(), r1.nImports.toString(), N_IMPORTS)


        println(headers(r1, r2))

        println(stringBuilderForFirstResult.toString())
//        println(body(r1,r2).joinToString(separator = ""))

        println(stringBuilderForSecondResult.toString())
    }

    private fun headers(r1: FileSummary, r2: FileSummary): kotlin.String {
        val languageR1 = languageType(r1.type)
        val languageR2 = languageType(r2.type)
      return  buildString {
            appendTo(r1.name!!, r2.name!!, FILE_NAME, FILE_NAME)
            appendTo(languageR1, languageR2, LANGUAGE, LANGUAGE)
            appendTo(r1.LOC.toString(), r2.LOC.toString(), LOC, LOC)
            appendTo(r1.commentLOC.toString(), r2.commentLOC.toString(), COMMENT_LOC, COMMENT_LOC)
            appendTo(r1.numMethod.toString(), r2.numMethod.toString(), NUM_METHODS, NUM_METHODS)
            appendTo(r1.nImports.toString(), r2.nImports.toString(), N_IMPORTS, N_IMPORTS)
        }
    }

    private fun body(r1: FileSummary, r2: FileSummary): kotlin.String {
        val languageR1 = languageType(r1.type)
        val languageR2 = languageType(r2.type)
        return buildString {
            appendTo(r1.name!!, r2.name!!, FILE_NAME, r1.name!!)
            appendTo(languageR1, languageR2, LANGUAGE, languageR1)
            appendTo(r1.LOC.toString(), r2.LOC.toString(), LOC, r1.LOC.toString())
            appendTo(r1.commentLOC.toString(), r2.commentLOC.toString(), COMMENT_LOC, r1.commentLOC.toString())
            appendTo(r1.numMethod.toString(), r2.numMethod.toString(), NUM_METHODS, r1.numMethod.toString())
            appendTo(r1.nImports.toString(), r2.nImports.toString(), N_IMPORTS, r1.nImports.toString())
        }
    }


    fun StringBuilder.appendTo(
        param1: kotlin.String,
        param2: kotlin.String,
        type: kotlin.String,
        appendLast: kotlin.String = ""
    ) {
        val content: kotlin.String = nCopies(
            max(
                (longestLengthOf(param1, param2, type) - type.length).toDouble(),
                0.0
            ).toInt(), " "
        ).joinToString(separator = "")

        append(content)
        append(appendLast)
    }


    private fun appendToHeader(
        stringBuilderForHeader: StringBuilder,
        n1: kotlin.String,
        n2: kotlin.String,
        type: kotlin.String
    ) {

        val content = nCopies(
            max(
                (longestLengthOf(n1, n2, type) - type.length).toDouble(),
                0.0
            ).toInt(), " "
        ).joinToString(separator = "")

        stringBuilderForHeader
            .append(content)
            .append(type)

    }

    // header -> append type, body -> append param
    fun appendToBuilder(builder: StringBuilder, param1: kotlin.String, param2: kotlin.String, type: kotlin.String) {
       /* val content: kotlin.String = nCopies(
            max(
                (longestLengthOf(param1, param2, type) - type.length).toDouble(),
                0.0
            ).toInt(), " "
        ).joinToString(separator = "")*/
        val content = String.join(
            "",
            nCopies(
                max(
                    (longestLengthOf(param1, param2, type) - param1.length).toDouble(),
                    0.0
                ).toInt(), " "
            )
        )

        builder
            .append(content)
            .append(param1)
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

    /*    private fun appendToBuilder(
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

        }*/


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