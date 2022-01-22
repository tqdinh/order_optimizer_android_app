package com.example.myoptimizationbill.framework

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object MyViewModelFactory:ViewModelProvider.Factory {
    lateinit var application: Application
    lateinit var dependencies:Interactors

    fun inject(application: Application,dependencies:Interactors){

        MyViewModelFactory.application=application
        MyViewModelFactory.dependencies=dependencies

    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {


        if(MyViewModel::class.java.isAssignableFrom(modelClass))
        {
            return modelClass.getConstructor(Application::class.java,Interactors::class.java)
                .newInstance(application, dependencies)
        }
        else
            throw IllegalAccessException("View model must extend MyViewModel")

    }

//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//
//    }


}