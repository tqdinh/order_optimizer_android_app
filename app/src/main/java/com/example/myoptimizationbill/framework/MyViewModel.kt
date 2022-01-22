package com.example.myoptimizationbill.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.myoptimizationbill.MyApplication

open class MyViewModel(application: Application,val interactors: Interactors) :AndroidViewModel(application){
    protected val application: MyApplication =getApplication()

}