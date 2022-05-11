package com.example.utt.hnccomputer.module

import android.content.Context
import android.content.SharedPreferences
import com.example.utt.hnccomputer.adapter.brand.BrandAdapter
import com.example.utt.hnccomputer.adapter.category.CategoryAdapter
import com.example.utt.hnccomputer.base.adapter.category.CategoryAdapter
import com.example.utt.hnccomputer.base.adapter.home.HomeProductAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Provides
    @Singleton
    fun provideCategoryAdapter(context: Context): CategoryAdapter {
        return CategoryAdapter(context)
    }

    @Provides
    @Singleton
    fun provideBrandAdapter(context: Context): BrandAdapter {
        return BrandAdapter(context)
    }

    @Provides
    @Singleton
    fun provideProductAdapter(context: Context): HomeProductAdapter {
        return HomeProductAdapter(context)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("HncPreferences", Context.MODE_PRIVATE)
    }

}