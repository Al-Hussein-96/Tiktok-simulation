package com.alhussein.videotimeline.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alhussein.videotimeline.model.PostModel
import com.alhussein.videotimeline.model.ResultData
import com.alhussein.videotimeline.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class TimeLineViewModel @Inject constructor(private val dataRepository: DataRepository ):ViewModel() {
    fun getDataList(): LiveData<ResultData<ArrayList<PostModel>?>> {
        return flow {
            emit(ResultData.Loading())
            emit(ResultData.Success(dataRepository.getPostsData()))
        }.asLiveData(Dispatchers.IO)
    }


}