package com.dicoding.asclepius.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.Result
import com.dicoding.asclepius.data.HistoryRepository
import com.dicoding.asclepius.data.local.entity.HistoryEntity

class HistoryViewModel(repository: HistoryRepository) : ViewModel() {

    val historyList: LiveData<Result<List<HistoryEntity>>> = repository.getAllHistory()
}