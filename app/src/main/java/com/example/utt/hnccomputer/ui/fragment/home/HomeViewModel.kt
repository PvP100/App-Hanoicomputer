package com.example.utt.hnccomputer.ui.fragment.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.utt.hnccomputer.base.BaseViewModel
import com.example.utt.hnccomputer.base.entity.BaseListResponse
import com.example.utt.hnccomputer.base.entity.BaseObjectLoadMoreResponse
import com.example.utt.hnccomputer.entity.model.Banner
import com.example.utt.hnccomputer.entity.model.Brand
import com.example.utt.hnccomputer.entity.model.HomeCategory
import com.example.utt.hnccomputer.entity.response.ResultResponse
import com.example.utt.hnccomputer.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    val brand = MutableLiveData<BaseObjectLoadMoreResponse<ResultResponse<Brand>>>()
    val homeCategory = MutableLiveData<BaseListResponse<HomeCategory>>()
    val banner = MutableLiveData<BaseListResponse<Banner>>()

    fun getHomeBrand() {
        mDisposable.add(
            repository.getBrand().doOnSubscribe {

            }
                .subscribe(
                    {
                        brand.value = it.data?.let { it1 ->
                            BaseObjectLoadMoreResponse<ResultResponse<Brand>>().success(
                                it1, isRefresh = false, isLoadmore = false
                            )
                        }
                    },
                    {

                    }
                )
        )
    }

    fun getHomeCategory() {
        mDisposable.add(
            repository.getHomeCategory().doOnSubscribe {

            }
                .doOnSubscribe {
                    homeCategory.value = BaseListResponse<HomeCategory>().loading()
                }
                .subscribe(
                    {
                        homeCategory.value = BaseListResponse<HomeCategory>().success(it.data)
                    },
                    {
                        homeCategory.value = BaseListResponse<HomeCategory>().error(it)
                    }
                )
        )
    }

    fun getBanner() {
        mDisposable.add(
            repository.getBanner().doOnSubscribe {
                banner.value = BaseListResponse<Banner>().loading()
            }.subscribe(
                {
                    banner.value = BaseListResponse<Banner>().success(it.data)
                },
                {
                    banner.value = BaseListResponse<Banner>().error(it)
                }
            )
        )
    }

}