package com.wozpi.core

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

abstract class WozActivity<T : ViewDataBinding?> : AppCompatActivity() {

    private var mBiding: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutView())
        mBiding = DataBindingUtil.setContentView<T>(this,setLayoutView())
    }

    abstract fun setLayoutView():Int
}
