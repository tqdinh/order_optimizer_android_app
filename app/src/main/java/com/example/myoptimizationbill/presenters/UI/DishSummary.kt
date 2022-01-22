package com.example.myoptimizationbill.presenters.UI

import android.content.Context
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.myoptimizationbill.R


class DishSummary(context: Context?) : RelativeLayout(context) {
    lateinit var inflater: LayoutInflater

    init
    {
        inflater = LayoutInflater.from(context)
        setWillNotDraw(false)
    }

    fun getView():View{
        var view:View= inflater.inflate(R.layout.item_dish_summaries, this)
        return view
    }


    override fun onDraw(canvas: Canvas) {
        // call the super method to keep any drawing from the parent side.
        super.onDraw(canvas)

    }
}