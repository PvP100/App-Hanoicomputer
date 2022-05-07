package com.example.utt.hnccomputer.module

import android.app.Application
import android.content.Context
import com.example.utt.hnccomputer.network.LocalAssetInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun provideContext(application: Application) : Context

    @Binds
    @Singleton
    abstract fun provideLocalJson(localAssetInterface: LocalAssetInterface.LocalAssetInterfaceImpl) : LocalAssetInterface
}