package com.wozpi.core

import android.content.Context
import android.databinding.Bindable
import android.view.View
import com.wozpi.core.service.ApiCallback


class DemoViewModel(context: Context) : WozViewModel(context){

    var name = "Go Pdypham"

    @Bindable
    fun getNameBeautiful():String{
        return name
    }

    fun setNameBeautiful(value:String){
        this.name = value
        notifyPropertyChanged(BR.nameBeautiful)
    }

    private fun getProfile(){


        callApi(DemoApiService.instances.getUserService().getProfile(),object : ApiCallback<User>{
            override fun getResult(data: User) {

            }

        })

    }


    fun onClickChange(v: View){
        getProfile()
    }


}