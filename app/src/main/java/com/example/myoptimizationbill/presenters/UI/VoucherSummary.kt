package com.example.myoptimizationbill.presenters.UI

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.myoptimizationbill.R


class VoucherSummary(context: Context?,val onGenerateView:  OnGenerateView?) : RelativeLayout(context) {

    lateinit var myView: View
    lateinit var inflater: LayoutInflater

    interface OnGenerateView
    {
        fun done( w:Int, h:Int);

    }

    init {
        inflater = LayoutInflater.from(context)
        setWillNotDraw(false)
    }

    fun getView(): View {
        var view = inflater.inflate(R.layout.item_voucher_summaries, this)
        return view
    }

    //override fun onDraw(canvas: Canvas) {
        // call the super method to keep any drawing from the parent side.
//        Log.d("CANVAS",canvas.width.toString()+"    "+canvas.height.toString())
//        super.onDraw(canvas)

//        canvas.save();
//        canvas.scale(0.1f, 0.1f);

//        canvas.restore();
//        this.onDraw(canvas)
      //  onGenerateView?.done(canvas.width,canvas.height)

    //}



    private fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = desiredSize
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
        }
        if (result < desiredSize) {
            Log.e("ChartView", "The view is too small, the content might get cut")
        }
        return result
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }
}