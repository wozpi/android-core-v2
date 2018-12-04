package com.wozpi.core

import android.databinding.Bindable
import android.util.Log
import android.view.View


class DemoViewModel : WozViewModel(){

    var name = "Go Pdypham"

    @Bindable
    fun getNameBeautiful():String{
        return name
    }

    fun setNameBeautiful(value:String){
        this.name = value
        notifyPropertyChanged(BR.nameBeautiful)
    }


    fun onClickChange(v: View){
        setNameBeautiful("wozpi")
        Log.e("WOW","inside")
    }
}