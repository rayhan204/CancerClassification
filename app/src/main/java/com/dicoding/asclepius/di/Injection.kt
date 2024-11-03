package com.dicoding.asclepius.di

import android.content.Context
import com.dicoding.asclepius.data.HistoryRepository
import com.dicoding.asclepius.data.local.HistoryRoomDatabase

object Injection {
    fun provideRepository(context: Context): HistoryRepository {
        val database = HistoryRoomDatabase.getDatabase(context)
        val dao = database.historyDao()
        return HistoryRepository.getInstance(dao)
    }
}