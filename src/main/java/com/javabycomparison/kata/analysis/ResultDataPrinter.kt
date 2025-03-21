package com.javabycomparison.kata.analysis

import java.lang.String
import java.util.*
import kotlin.Int
import kotlin.math.max

class ResultDataPrinter {
    fun print(data: ResultData): kotlin.String {
        var language = languageType(data)
        return (data.name
                + "\t"
                + language
                + "\t"
                + data.L
                + "\t"
                + data.LOC
                + "\t"
                + data.commentLOC
                + "\t"
                + data.numMethod
                + "\t"
                + data.nImports)
    }

    fun printFileName(name: kotlin.String, length: Int) = maxLengthOf(name,length)

    fun printLanguage(language: kotlin.String, length: Int) = maxLengthOf(language,length)

    fun printLOC(LOC: Int, length: Int) = maxLengthOf(LOC.toString(),length)

    fun printCommentLOC(commentLOC: kotlin.String, length: Int) = maxLengthOf(commentLOC.toString(), length)

    fun printNumMethodLOC(numMethod: Int, length: Int) = maxLengthOf(numMethod.toString(), length)

    fun printNImportsLOC(nImports: Int, length: Int) = maxLengthOf(nImports.toString(),length)

    private fun maxLengthOf(numMethod: kotlin.String, length: Int): kotlin.String {
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
}

 fun languageType(
    data: ResultData,
) = when (data.type) {
    0 -> "Java"
    1 -> "Python"
    else -> "other"
}
