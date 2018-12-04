package com.wozpi.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.BaseObservable
import android.databinding.Bindable
import com.wozpi.core.service.ApiCallback
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

open class WozViewModel(context: Context) : BaseObservable(), CallbackViewModel {

    override fun onDestroy() {
        for (e in listApiService){
            e.unsubscribe()
        }
    }

    protected val mContext = context
    private var listApiService = ArrayList<Subscription>()


    @Bindable
    open fun getTitleToolbar():String ?{
        return null
    }

    /**
     * start activity
     * */

    protected fun startActivity(cl: Class<Activity>){
        val intent = Intent(mContext,cl)
        mContext.startActivity(intent)
    }


    /**
     * create and call Api
     * */

    protected fun <T> callApi(observableHolder: Observable<T>,callback: ApiCallback<T>){

        val holder = observableHolder
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                callback.getResult(result)
            }, { throwable ->
                // show back button

            })

        listApiService.add(holder)
    }
}