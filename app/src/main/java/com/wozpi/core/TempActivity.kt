package com.wozpi.core

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wozpi.core.databinding.ActivityMainBinding

class TempActivity : AppCompatActivity() {
    private var mData: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mData = DataBindingUtil.setContentView(this,R.layout.activity_main)
    }
}