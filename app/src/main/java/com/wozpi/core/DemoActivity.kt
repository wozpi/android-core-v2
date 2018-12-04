package com.wozpi.core

import com.wozpi.core.databinding.ActivityMainBinding

class DemoActivity : WozActivity<ActivityMainBinding>() {

    override fun initData() {

    }

    override fun setLayoutView(): Int {
        return R.layout.activity_main
    }


    override fun setViewModelObject(): WozViewModel {
       return DemoViewModel(this)
    }

    override fun setNameViewModel(): Int {
        return BR.mainViewModel
    }



}