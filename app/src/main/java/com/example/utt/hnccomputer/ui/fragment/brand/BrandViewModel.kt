package com.example.utt.hnccomputer.ui.fragment.brand

import com.example.utt.hnccomputer.base.BaseViewModel
import com.example.utt.hnccomputer.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BrandViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {
}