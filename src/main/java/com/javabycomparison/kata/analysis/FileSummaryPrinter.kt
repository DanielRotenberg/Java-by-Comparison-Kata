package com.javabycomparison.kata.analysis

import java.lang.String
import java.util.*
import kotlin.Int
import kotlin.math.max

class FileSummaryPrinter {
    fun print(file: FileSummary): kotlin.String {
        var language = languageType(file)
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

 /*   fun printFileName(name: kotlin.String, length: Int) = maxLengthOf(name,length)

    fun printLanguage(language: kotlin.String, length: Int) = maxLengthOf(language,length)

    fun printLOC(LOC: Int, length: Int) = maxLengthOf(LOC.toString(),length)

    fun printCommentLOC(commentLOC: kotlin.String, length: Int) = maxLengthOf(commentLOC.toString(), length)

    fun printNumMethodLOC(numMethod: Int, length: Int) = maxLengthOf(numMethod.toString(), length)

    fun printNImportsLOC(nImports: Int, length: Int) = maxLengthOf(nImports.toString(),length)*/


}

 fun languageType(
     data: FileSummary,
) = when (data.type) {
    0 -> "Java"
    1 -> "Python"
    else -> "other"
}

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