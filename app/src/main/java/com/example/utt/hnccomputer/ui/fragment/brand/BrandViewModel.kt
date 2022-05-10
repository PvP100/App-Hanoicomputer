package com.example.utt.hnccomputer.ui.fragment.brand

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.utt.hnccomputer.base.BaseViewModel
import com.example.utt.hnccomputer.base.entity.BaseObjectLoadMoreResponse
import com.example.utt.hnccomputer.entity.model.Brand
import com.example.utt.hnccomputer.entity.response.ResultResponse
import com.example.utt.hnccomputer.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BrandViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    private val _brand: MutableLiveData<BaseObjectLoadMoreResponse<ResultResponse<Brand>>> = MutableLiveData()
    val brand: LiveData<BaseObjectLoadMoreResponse<ResultResponse<Brand>>> = _brand

    fun getBrand() {
        mDisposable.add(
            repository.getBrand()
                .doOnSubscribe {
                    _brand.value = BaseObjectLoadMoreResponse<ResultResponse<Brand>>().loading()
                }
                .subscribe(
                    {
                        _brand.value = it.data?.let { it1 ->
                            BaseObjectLoadMoreResponse<ResultResponse<Brand>>().success(
                                it1, isRefresh = false, isLoadmore = false)
                        }
                    },
                    {
                        _brand.value = BaseObjectLoadMoreResponse<ResultResponse<Brand>>().error(it)
                    }
                )
        )
    }

}