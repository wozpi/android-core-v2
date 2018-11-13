package com.wozpi.core

import com.wozpi.core.databinding.ActivityMainBinding

class DemoActivity : WozActivity<ActivityMainBinding>() {

    override fun setLayoutView(): Int {
        return R.layout.activity_main
    }


    override fun setViewModelObject(): Any {
       return DemoViewModel()
    }

    override fun setNameViewModel(): Int {
        return BR.mainViewModel
    }


//    override fun initBindData(bindData: ActivityMainBinding) {
//        val demoViewModel = DemoViewModel()
//        bindData.mainViewModel = demoViewModel
//    }




}