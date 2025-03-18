package com.javabycomparison.kata.analysis

import java.util.*

class ResultData {
    @JvmField
    var type: Int = 0
    @JvmField
    var name: String? = null
    var L: Int = 0
    @JvmField
    var LOC: Int = 0
    @JvmField
    var commentLOC: Int = 0
    @JvmField
    var numMethod: Int = 0
    @JvmField
    var nImports: Int = 0

    constructor(type: Int, name: String, LOC: Int, commentLOC: Int, numMethod: Int, nImports: Int) {
        this.type = type
        this.name = name.replace("\\\\".toRegex(), "/")
        this.LOC = LOC
        this.commentLOC = commentLOC
        this.numMethod = numMethod
        this.nImports = nImports
    }

    /*
  public ResultData(boolean java){
      this.javaFile = java;

  }
  */
    constructor()

    override fun equals(o: Any?): Boolean {
        if (this === o) return true

        if (o == null || javaClass != o.javaClass) return false
        val that = o as ResultData
        return type == that.type && L == that.L && LOC == that.LOC && commentLOC == that.commentLOC && numMethod == that.numMethod && nImports == that.nImports && name == that.name
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return StringJoiner(", ", ResultData::class.java.getSimpleName() + "[", "]")
            .add("type=" + type)
            .add("name='" + name + "'")
            .add("L=" + L)
            .add("LOC=" + LOC)
            .add("commentLOC=" + commentLOC)
            .add("numMethod=" + numMethod)
            .add("nImports=" + nImports)
            .toString()
    }
}
