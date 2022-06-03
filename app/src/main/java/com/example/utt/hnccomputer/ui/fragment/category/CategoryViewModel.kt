package com.example.utt.hnccomputer.ui.fragment.category

import androidx.lifecycle.MutableLiveData
import com.example.utt.hnccomputer.base.BaseViewModel
import com.example.utt.hnccomputer.base.entity.BaseObjectLoadMoreResponse
import com.example.utt.hnccomputer.entity.model.Category
import com.example.utt.hnccomputer.entity.response.ResultResponse
import com.example.utt.hnccomputer.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    val category = MutableLiveData<BaseObjectLoadMoreResponse<ResultResponse<Category>>>()

    fun getCategory(isRefresh: Boolean = false) {
        mDisposable.add(
            repository.getCategory().doOnSubscribe {
                category.value = BaseObjectLoadMoreResponse<ResultResponse<Category>>().loading().apply {
                    this.isRefresh = isRefresh
                }
            }
                .subscribe(
                    {
                        category.value = it.data?.let { it1 ->
                            BaseObjectLoadMoreResponse<ResultResponse<Category>>().success(
                                it1, isRefresh = isRefresh, isLoadmore = false
                            )
                        }
                    },
                    {
                        category.value = BaseObjectLoadMoreResponse<ResultResponse<Category>>().error(it)
                    }
                )
        )
    }

}