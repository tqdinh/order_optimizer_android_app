package com.example.myoptimizationbill.utils

import java.text.DecimalFormat
import java.text.NumberFormat

class Utils {

    companion object
    {
        fun formatCurrency(s:String):String
        {

            var cleanString: String = s.toString().replace("[$,.]".toRegex(), "");
            if (!cleanString.equals("")) {
                var parsed = Integer.parseInt(cleanString);

                val formatter: NumberFormat = DecimalFormat("#,###")
                var formatted: String = formatter.format(parsed)
                cleanString=formatted
            }
            return cleanString
        }


    }
}