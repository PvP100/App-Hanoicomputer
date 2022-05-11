package com.example.utt.hnccomputer.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.utt.hnccomputer.R

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    fun transition(fragment: Fragment, id: Int, TAG: String, isAddToBackStack: Boolean = true, animation: Boolean = false) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (animation) {
            fragmentTransaction.setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit)
            )
        }
        fragmentTransaction.add(id, fragment)
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(TAG)
        }
        fragmentTransaction.commit()
    }

    fun transition(fragment: Fragment, id: Int, TAG: String, isAddToBackStack: Boolean = true, args : Bundle? = null) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        args?.let {
            fragment.arguments = args
        }
        fragmentTransaction.add(id, fragment)
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(TAG)
        }
        fragmentTransaction.commit()
    }

    fun showProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun hideProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

}