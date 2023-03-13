package com.example.justordinarydiaryapp.presentation.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.justordinarydiaryapp.data.repository.DiaryRepository
import com.example.justordinarydiaryapp.model.Diary
import com.example.justordinarydiaryapp.model.request.DiaryRequest
import com.example.justordinarydiaryapp.network.model.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiaryViewModel(
    private val repository: DiaryRepository,
) : ViewModel() {

    private val _createNewDiaryLiveData = MutableLiveData<ResultWrapper<Diary>>()
    val createNewDiaryLiveData: LiveData<ResultWrapper<Diary>> = _createNewDiaryLiveData

    fun createNewDiary(loginRequest: DiaryRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            _createNewDiaryLiveData.postValue(ResultWrapper.Loading)
            _createNewDiaryLiveData.postValue(repository.createNewDiary(loginRequest))
        }
    }

}