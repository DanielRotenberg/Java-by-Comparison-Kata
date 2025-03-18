package com.javabycomparison.kata.analysis

import java.lang.String
import java.util.*
import kotlin.Int
import kotlin.math.max

class ResultDataPrinter {
    fun print(data: ResultData): kotlin.String {
        var language: kotlin.String?
        language = languageType(data)
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

    fun printFileName(data: ResultData, length: Int): kotlin.String {
        return (String.join(
            "",
            Collections.nCopies<kotlin.String?>(max((length - data.name!!.length).toDouble(), 0.0).toInt(), " ")
        )
                + data.name)
    }

    fun printLanguage(data: ResultData, length: Int): kotlin.String {
        val language: kotlin.String
        if (data.type == 0) language = "Java"
        else if (data.type == 1) language = "Python"
        else language = "other"
        return (String.join(
            "",
            Collections.nCopies<kotlin.String?>(max((length - language.length).toDouble(), 0.0).toInt(), " ")
        )
                + language)
    }

    fun printLOC(data: ResultData, length: Int): kotlin.String {
        return (String.join(
            "",
            Collections.nCopies<kotlin.String?>(max((length - data.LOC.toString().length).toDouble(), 0.0).toInt(), " ")
        )
                + data.LOC)
    }

    fun printCommentLOC(data: ResultData, length: Int): kotlin.String {
        return (String.join(
            "",
            Collections.nCopies<kotlin.String?>(
                max((length - data.commentLOC.toString().length).toDouble(), 0.0).toInt(), " "
            )
        )
                + data.commentLOC)
    }

    fun printNumMethodLOC(data: ResultData, length: Int): kotlin.String {
        return (String.join(
            "",
            Collections.nCopies<kotlin.String?>(
                max(
                    (length - data.numMethod.toString().length).toDouble(),
                    0.0
                ).toInt(), " "
            )
        )
                + data.numMethod)
    }

    fun printNImportsLOC(data: ResultData, length: Int): kotlin.String {
        return (String.join(
            "",
            Collections.nCopies<kotlin.String?>(
                max((length - data.nImports.toString().length).toDouble(), 0.0).toInt(),
                " "
            )
        )
                + data.nImports)
    }
}

private fun languageType(
    data: ResultData,
): kotlin.String {
    var language: kotlin.String
    if (data.type == 0) language = "Java"
    else if (data.type == 1) language = "Python"
    else language = "other"
    return language
}
