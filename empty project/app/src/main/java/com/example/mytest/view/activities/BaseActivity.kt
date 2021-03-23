package com.example.mytest.view.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var tag: String

    abstract fun getAttachedLayout(): Int

    // UI : Definition Fragment abstract methods
    abstract fun getFragmentLayout(): Int
    abstract fun getFragment(): Fragment
    abstract fun getFragmentTag()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getAttachedLayout())
        getFragmentTag()
        configureFragment(savedInstanceState)
    }

    private fun configureFragment(bundle: Bundle?){
        if (bundle == null){
            supportFragmentManager.beginTransaction()
                .add(getFragmentLayout(), getFragment(), tag)
                .commit()
        }
    }



}