package com.example.justordinarydiaryapp.presentation.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.justordinarydiaryapp.data.paging.ArchivedDiaryLocalPagingSource
import com.example.justordinarydiaryapp.data.paging.DiaryLocalPagingSource
import com.example.justordinarydiaryapp.data.paging.SearchDiaryLocalPagingSource
import com.example.justordinarydiaryapp.data.repository.DiaryRepository
import com.example.justordinarydiaryapp.model.Diary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DiaryLocalViewModel(
    private val repository: DiaryRepository.Local
) : ViewModel() {

    private val _diariesLocalLiveData = MutableLiveData<PagingData<Diary>>()
    val diariesLocalLiveData: LiveData<PagingData<Diary>> = _diariesLocalLiveData

    private val _searchDiariesLocalLiveData = MutableLiveData<PagingData<Diary>>()
    val searchDiariesLocalLiveData: LiveData<PagingData<Diary>> = _searchDiariesLocalLiveData

    private val _archivedDiariesLiveData = MutableLiveData<PagingData<Diary>>()
    val archivedDiariesLiveData: LiveData<PagingData<Diary>> = _archivedDiariesLiveData

    private val _archivedDiariesCountLiveData = MutableLiveData<Int>()
    val archivedDiariesCountLiveData: LiveData<Int> = _archivedDiariesCountLiveData

    fun loadLocalDiaries() {
        viewModelScope.launch(Dispatchers.IO) {
            Pager(
                PagingConfig(
                    pageSize = 10,
                    initialLoadSize = 10
                )
            ) {
                DiaryLocalPagingSource(repository)
            }.flow.cachedIn(viewModelScope).collectLatest {
                _diariesLocalLiveData.postValue(it)
            }
        }
    }

    fun searchLocalDiaries(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Pager(
                PagingConfig(
                    pageSize = 10,
                    initialLoadSize = 10,
                    enablePlaceholders = false
                )
            ) {
                SearchDiaryLocalPagingSource(query, repository)
            }.flow.cachedIn(viewModelScope).collect {
                _searchDiariesLocalLiveData.postValue(it)
            }
        }
    }

    fun clearLocalDiaries() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearAllLocalDiary()
        }
    }

    fun loadArchivedDiaries() {
        viewModelScope.launch(Dispatchers.IO) {
            Pager(
                PagingConfig(
                    pageSize = 10,
                    initialLoadSize = 10
                )
            ) {
                ArchivedDiaryLocalPagingSource(repository)
            }.flow.cachedIn(viewModelScope).collectLatest {
                _archivedDiariesLiveData.postValue(it)
            }
        }
    }

    fun clearLocalArchivedDiaries() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearAllArchivedDiary()
        }
    }

    fun getArchivedDiaryCount() {
        viewModelScope.launch(Dispatchers.IO) {
            _archivedDiariesCountLiveData.postValue(repository.getArchivedDiaryCount())
        }
    }

}