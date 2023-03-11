package com.example.convertapp.util

import com.example.convertapp.util.Constant.BINARY_BASE
import com.example.convertapp.util.Constant.DECIMAL_BASE
import com.example.convertapp.util.Constant.HEXADECIMAL_BASE
import com.example.convertapp.util.Constant.OCTAL_BASE

enum class SystemNumber(base: Int) {
    BINARY(BINARY_BASE),
    OCTAL(OCTAL_BASE),
    DECIMAL(DECIMAL_BASE),
    HEXADECIMAL(HEXADECIMAL_BASE)
}