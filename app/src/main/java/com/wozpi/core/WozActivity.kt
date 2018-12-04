package com.wozpi.core

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import kotlinx.android.synthetic.main.toolbar_woz.*

abstract class WozActivity<T : ViewDataBinding?> : AppCompatActivity() {

    protected enum class STATUS{
        FIRST_LOAD,
        REFRESH,
        LOAD_MORE
    }

    protected var mStatusLoading = STATUS.FIRST_LOAD

    private var mBiding: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutView())
        mBiding = DataBindingUtil.setContentView<T>(this,setLayoutView())
        mBiding!!.setVariable(setNameViewModel(),setViewModelObject())

        initToolbar()
    }

    abstract fun setLayoutView():Int

    abstract fun setNameViewModel(): Int

    abstract fun setViewModelObject(): WozViewModel

    abstract fun initData()

    override fun onDestroy() {
        setViewModelObject().onDestroy()
        super.onDestroy()
    }

    fun addViewModel(name: Int, viewModelObject: WozViewModel){
        mBiding!!.setVariable(name,viewModelObject)
    }

    fun loadData(){

    }

    /**
     * Hide keyboard when touch outside
     * */


    protected fun isHideSoftKeyBoardTouchOutSide():Boolean{
        return true
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if(isHideSoftKeyBoardTouchOutSide()) {
            val viewFocus = window.currentFocus

            if (viewFocus is EditText && ev != null) {
                val screenLocation = IntArray(2)
                viewFocus.getLocationOnScreen(screenLocation)
                val x = ev.rawX + viewFocus.left - screenLocation[0]
                val y = ev.rawY + viewFocus.right - screenLocation[1]

                if (ev.action == MotionEvent.ACTION_DOWN) {
                    if (x < viewFocus.left || x >= viewFocus.right || y < viewFocus.top || y >= viewFocus.bottom) {
                        val iMM: InputMethodManager =
                            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        iMM.hideSoftInputFromWindow(viewFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                    }
                }
            }
        }

        return super.dispatchTouchEvent(ev)
    }


    /**
     * Init toolbar
     * */

    private fun initToolbar(){
        if(toolbarBack != null){
            setSupportActionBar(toolbarBack)
            val actionBar = supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(false)
        }

    }


}
