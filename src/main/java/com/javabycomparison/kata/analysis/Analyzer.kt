package com.javabycomparison.kata.analysis

import java.io.IOException

interface Analyzer {
    /** This method analyzes code.  */
    @Throws(IOException::class)
    fun analyze(): ResultData?
}
