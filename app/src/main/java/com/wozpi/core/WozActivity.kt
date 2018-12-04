package com.wozpi.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.wozpi.core.R.id.toolBarBack

abstract class WozActivity<T : ViewDataBinding?> : AppCompatActivity() {

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

    abstract fun setViewModelObject(): Any

    /**
     * Hide keyboard when touch outside
     * */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val viewFocus = window.currentFocus

        if(viewFocus is EditText && ev != null){
            val screenLocation = IntArray(2)
            viewFocus.getLocationOnScreen(screenLocation)
            val x = ev.rawX + viewFocus.left - screenLocation[0]
            val y = ev.rawY + viewFocus.right - screenLocation[1]

            if(ev.action == MotionEvent.ACTION_DOWN){
                if(x < viewFocus.left || x >= viewFocus.right || y < viewFocus.top || y >= viewFocus.bottom){
                    val iMM: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    iMM.hideSoftInputFromWindow(viewFocus.windowToken,InputMethodManager.HIDE_NOT_ALWAYS)
                }
            }
        }

        return super.dispatchTouchEvent(ev)
    }

    /**
     * start activity
     * */

    protected fun startActivity(cl: Class<Activity>){
        val intent = Intent(this,cl)
        startActivity(intent)
    }

    /**
     * Init toolbar
     * */

    private fun initToolbar(){
        val toolBarBack = findViewById<Toolbar>(toolBarBack)
        if(toolBarBack != null){
            setSupportActionBar(toolBarBack)
            val actionBar = supportActionBar
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(false)

                actionBar.title = null

            }
        }

    }

}
