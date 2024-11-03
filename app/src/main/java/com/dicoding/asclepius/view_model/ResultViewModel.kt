package com.dicoding.asclepius.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.HistoryRepository
import com.dicoding.asclepius.data.local.entity.HistoryEntity
import kotlinx.coroutines.launch

class ResultViewModel(private val repository: HistoryRepository) : ViewModel() {

    private val _history = MutableLiveData<Boolean>()
    val history: LiveData<Boolean> = _history

    fun saveHistory(historyEntity: HistoryEntity) {
        viewModelScope.launch {
            repository.insertHistory(historyEntity)
            _history.value = true
        }
    }
}