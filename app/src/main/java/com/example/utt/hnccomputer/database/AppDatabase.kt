package com.example.utt.hnccomputer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.utt.hnccomputer.database.dao.MyOrderDao
import com.example.utt.hnccomputer.database.entity.MyOrderInformation

@Database(
    entities = [MyOrderInformation::class],
    version = AppDatabase.VERSION
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "myorder.db"
        const val VERSION = 1

        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()

        @JvmStatic
        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
                return INSTANCE!!
            }
        }
    }

    abstract fun myOrderDao(): MyOrderDao

}