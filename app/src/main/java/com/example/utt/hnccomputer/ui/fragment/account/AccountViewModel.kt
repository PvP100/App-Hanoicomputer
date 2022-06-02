package com.example.utt.hnccomputer.ui.fragment.account

import android.content.SharedPreferences
import com.example.utt.hnccomputer.base.BaseViewModel
import com.example.utt.hnccomputer.extension.clearAll
import com.example.utt.hnccomputer.extension.setBoolean
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val sharedPreferences: SharedPreferences) : BaseViewModel() {

    fun logout() {
        sharedPreferences.clearAll()
    }

}