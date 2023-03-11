package com.example.convertapp.model

import com.example.convertapp.util.Constant

class SystemNumberModel(){
    var binary : String? = null
    var octal : String? = null
    var decimal : String? = null
    var hexadecimal : String?= null

    fun convertToBinary(number: String, base: Int)  {
        val decimal = number.toInt(base)
        this.binary = Integer.toBinaryString(decimal)
    }


    fun convertToSystemNumber() {
        val decimal = Integer.parseInt(this.binary, Constant.BINARY_BASE)

        this.binary = this.binary
        this.octal = Integer.toOctalString(decimal)
        this.decimal = decimal.toString()
        this.hexadecimal =Integer.toHexString(decimal)
    }

}