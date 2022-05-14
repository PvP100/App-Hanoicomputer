package com.example.utt.hnccomputer.ui.fragment.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.utt.hnccomputer.base.BaseViewModel
import com.example.utt.hnccomputer.base.entity.BaseObjectLoadMoreResponse
import com.example.utt.hnccomputer.entity.model.Product
import com.example.utt.hnccomputer.entity.response.ResultResponse
import com.example.utt.hnccomputer.network.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: ProductRepository) : BaseViewModel() {

    private val _product: MutableLiveData<BaseObjectLoadMoreResponse<ResultResponse<Product>>> = MutableLiveData()
    val product: LiveData<BaseObjectLoadMoreResponse<ResultResponse<Product>>> = _product

    fun getSearch(value: String) {
        mDisposable.add(repository.getSearch(
            value
        ).doOnSubscribe {
            _product.value = BaseObjectLoadMoreResponse<ResultResponse<Product>>().loading()
            }.subscribe(
                {
                    _product.value = it.data?.let { it1 ->
                        BaseObjectLoadMoreResponse<ResultResponse<Product>>().success(
                            it1, isRefresh = false, isLoadmore = false
                        )
                    }
                },
                {
                    _product.value = BaseObjectLoadMoreResponse<ResultResponse<Product>>().error(it)
                }
            ))
    }

}