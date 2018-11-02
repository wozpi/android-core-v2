package com.wozpi.core

import com.wozpi.core.databinding.ActivityMainBinding

class DemoActivity : WozActivity<ActivityMainBinding>() {
    override fun setLayoutView(): Int {
        return R.layout.activity_main
    }
}