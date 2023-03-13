package com.example.justordinarydiaryapp.presentation.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.justordinarydiaryapp.data.paging.DiaryPagingSource
import com.example.justordinarydiaryapp.base.paging.PagingUiModel
import com.example.justordinarydiaryapp.data.repository.DiaryRepository
import com.example.justordinarydiaryapp.model.Diary
import com.example.justordinarydiaryapp.model.request.DiaryRequest
import com.example.justordinarydiaryapp.network.model.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DiaryViewModel(
    private val repository: DiaryRepository,
) : ViewModel() {

    private val _createNewDiaryLiveData = MutableLiveData<ResultWrapper<Diary>>()
    val createNewDiaryLiveData: LiveData<ResultWrapper<Diary>> = _createNewDiaryLiveData

    private val _diaryPagingLiveData =
        MutableLiveData<PagingData<PagingUiModel<Diary>>>()
    val diaryPagingLiveData: LiveData<PagingData<PagingUiModel<Diary>>> =
        _diaryPagingLiveData

    fun createNewDiary(loginRequest: DiaryRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            _createNewDiaryLiveData.postValue(ResultWrapper.Loading)
            _createNewDiaryLiveData.postValue(repository.createNewDiary(loginRequest))
        }
    }

    fun fetchDiariesPaging() {
        viewModelScope.launch(Dispatchers.IO) {
            Pager(PagingConfig(pageSize = 10)) {
                DiaryPagingSource(repository)
            }.flow
                .map { pagingData ->
                    pagingData.map { PagingUiModel.DataItem(it) }
                }
                .map {
                    it.insertSeparators { before, after ->
                        if (before != null && after == null) {
                            PagingUiModel.SeparatorItem(
                                ""
                            )
                        } else {
                            return@insertSeparators null
                        }
                    }
                }
                .cachedIn(viewModelScope).collectLatest {
                    _diaryPagingLiveData.postValue(it)
                }
        }
    }

}