package com.javabycomparison.kata.printing

import com.javabycomparison.kata.analysis.FileSummary
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

        println(headers(r1, r2))

        println(body(r1, r2))

        println(body(r2, r1))
    }

    private fun headers(r1: FileSummary, r2: FileSummary): String {
        val languageR1 = languageType(r1.type)
        val languageR2 = languageType(r2.type)
        return buildString {
            appendTo(r1.name!!, r2.name!!, FILE_NAME, FILE_NAME)
            appendTo(languageR1, languageR2, LANGUAGE, LANGUAGE)
            appendTo(r1.LOC.toString(), r2.LOC.toString(), LOC, LOC)
            appendTo(r1.commentLOC.toString(), r2.commentLOC.toString(), COMMENT_LOC, COMMENT_LOC)
            appendTo(r1.numMethod.toString(), r2.numMethod.toString(), NUM_METHODS, NUM_METHODS)
            appendTo(r1.nImports.toString(), r2.nImports.toString(), N_IMPORTS, N_IMPORTS)
        }
    }

 /*
  to-do -> think of a way to reduce duplication between header and body code
  fun buildSummary(
        r1: FileSummary,
        r2: FileSummary,
        languageR1: String,
        languageR2: String,
        appendLast: String
    ): String {
        return buildString {
            appendTo(r1.name!!, r2.name!!, FILE_NAME, appendLast)
            appendTo(languageR1, languageR2, LANGUAGE, appendLast)
            appendTo(r1.LOC.toString(), r2.LOC.toString(), LOC, appendLast)
            appendTo(r1.commentLOC.toString(), r2.commentLOC.toString(), COMMENT_LOC,appendLast)
            appendTo(r1.numMethod.toString(), r2.numMethod.toString(), NUM_METHODS, appendLast)
            appendTo(r1.nImports.toString(), r2.nImports.toString(), N_IMPORTS, appendLast)
        }
    }*/


    private fun body(r1: FileSummary, r2: FileSummary): String {
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
        param1: String,
        param2: String,
        type: String,
        appendLast: String
    ) {
        val content: String = nCopies(
            max(
                (longestLengthOf(param1, param2, type) - appendLast.length).toDouble(),
                0.0
            ).toInt(), " "
        ).joinToString(separator = "")

        append(content)
        append(appendLast)
    }

    private fun longestLengthOf(
        nImports1: String,
        nImports2: String,
        type: String
    ): Int {
        return max(
            max(nImports1.length.toDouble(), nImports2.length.toDouble()),
            type.length.toDouble()
        ).toInt()
    }


}

fun printFileSummary(file: FileSummary): String {
    var language = languageType(file.type)
    return (file.name
            + "\t"
            + language
            + "\t"
            + file.L
            + "\t"
            + file.LOC
            + "\t"
            + file.commentLOC
            + "\t"
            + file.numMethod
            + "\t"
            + file.nImports)
}

fun languageType(
     type: Int,
): String {
     return when (type) {
         0 -> "Java"
         1 -> "Python"
         else -> "other"
     }
 }