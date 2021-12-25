package com.alhussein.videotimeline.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alhussein.videotimeline.model.Post
import com.alhussein.videotimeline.model.ResultData
import com.alhussein.videotimeline.remotedatasource.model.PostModel
import com.alhussein.videotimeline.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class TimelineViewModel @Inject constructor(private val dataRepository: DataRepository):ViewModel() {
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading


//    fun getDataList() : LiveData<ResultData<List<Post>?>> {
//        return flow {
//            emit(ResultData.Loading())
//            emit(ResultData.Success(dataRepository.latestPosts))
//        }.asLiveData(Dispatchers.IO)
//    }
}