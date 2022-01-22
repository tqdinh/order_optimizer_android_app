package com.example.myoptimizationbill.presenters.Fragments

import android.widget.EditText
import androidx.fragment.app.Fragment
import java.text.DecimalFormat
import java.text.NumberFormat

open class BaseFragment :Fragment() {


    fun showLoading(data:Any)
    {

    }

    fun convertFromEditTextToInt(editText: EditText)
    {

    }

    fun convertFromEditTextToDouble(editText: EditText)
    {

    }

    fun formatCurrency(number:Int?):String
    {

        val formatter: NumberFormat = DecimalFormat("#,###")
        var formated: String = formatter.format(number)
        return formated
    }
}