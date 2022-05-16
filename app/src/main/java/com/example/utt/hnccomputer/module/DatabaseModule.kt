package com.example.utt.hnccomputer.module

import android.app.Application
import com.example.utt.hnccomputer.database.AppDatabase
import com.example.utt.hnccomputer.database.dao.MyOrderDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return AppDatabase.getInstance(application)
    }

    @Provides
    fun providePhotoBookDao(appDataBase: AppDatabase): MyOrderDao {
        return appDataBase.myOrderDao()
    }
}