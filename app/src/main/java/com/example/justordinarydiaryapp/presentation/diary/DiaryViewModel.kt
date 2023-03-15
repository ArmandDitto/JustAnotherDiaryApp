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
import com.example.justordinarydiaryapp.base.paging.PagingUiModel
import com.example.justordinarydiaryapp.data.paging.DiaryPagingSource
import com.example.justordinarydiaryapp.data.repository.DiaryRepository
import com.example.justordinarydiaryapp.model.Diary
import com.example.justordinarydiaryapp.model.request.DiaryRequest
import com.example.justordinarydiaryapp.network.model.PagingWrapper
import com.example.justordinarydiaryapp.network.model.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DiaryViewModel(
    private val repository: DiaryRepository.Remote,
) : ViewModel() {

    private val _createNewDiaryLiveData = MutableLiveData<ResultWrapper<Diary>>()
    val createNewDiaryLiveData: LiveData<ResultWrapper<Diary>> = _createNewDiaryLiveData

    private val _diaryPagingLiveData =
        MutableLiveData<PagingData<PagingUiModel<Diary>>>()
    val diaryPagingLiveData: LiveData<PagingData<PagingUiModel<Diary>>> =
        _diaryPagingLiveData

    private val _diaryDetailLiveData = MutableLiveData<ResultWrapper<Diary>>()
    val diaryDetailLiveData: LiveData<ResultWrapper<Diary>> = _diaryDetailLiveData

    private val _editDiaryLiveData = MutableLiveData<ResultWrapper<Diary>>()
    val editDiaryLiveData: LiveData<ResultWrapper<Diary>> = _editDiaryLiveData

    private val _diariesLiveData = MutableLiveData<ResultWrapper<PagingWrapper<List<Diary>>>>()
    val diariesLiveData: LiveData<ResultWrapper<PagingWrapper<List<Diary>>>> = _diariesLiveData

    private val _archiveDiaryLiveData = MutableLiveData<ResultWrapper<Diary>>()
    val archiveDiaryLiveData: LiveData<ResultWrapper<Diary>> = _archiveDiaryLiveData

    private val _unarchiveDiaryLiveData = MutableLiveData<ResultWrapper<Diary>>()
    val unarchiveDiaryLiveData: LiveData<ResultWrapper<Diary>> = _unarchiveDiaryLiveData

    fun createNewDiary(diaryRequest: DiaryRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            _createNewDiaryLiveData.postValue(ResultWrapper.Loading)
            _createNewDiaryLiveData.postValue(repository.createNewDiary(diaryRequest))
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

    fun getDiaryDetail(diaryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _diaryDetailLiveData.postValue(ResultWrapper.Loading)
            _diaryDetailLiveData.postValue(repository.getDiaryDetail(diaryId))
        }
    }

    fun editDairy(diaryId: String, request: DiaryRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            _editDiaryLiveData.postValue(ResultWrapper.Loading)
            _editDiaryLiveData.postValue(repository.getEditDiary(diaryId, request))
        }
    }

    fun fetchDiaries(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (page == 1) {
                _diariesLiveData.postValue(ResultWrapper.Loading)
                _diariesLiveData.postValue(repository.getDiaries(page))
            } else {
                _diariesLiveData.postValue(repository.getDiaries(page))
            }
        }
    }

    fun archiveDiary(diaryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _archiveDiaryLiveData.postValue(ResultWrapper.Loading)
            _archiveDiaryLiveData.postValue(repository.archiveDiary(diaryId))
        }
    }

    fun unarchiveDiary(diaryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _unarchiveDiaryLiveData.postValue(ResultWrapper.Loading)
            _unarchiveDiaryLiveData.postValue(repository.unarchiveDiary(diaryId))
        }
    }

}