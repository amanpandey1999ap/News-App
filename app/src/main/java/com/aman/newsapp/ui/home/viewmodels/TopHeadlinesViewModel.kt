package com.aman.newsapp.ui.home.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aman.newsapp.models.response.NewsResponseModel
import com.aman.newsapp.ui.home.repository.MainRepository
import com.aman.newsapp.networking.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlinesViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _topHeadLinesResponse = MutableLiveData<Resource<NewsResponseModel?>>()
    val topHeadlinesResponse : LiveData<Resource<NewsResponseModel?>>
    get() = _topHeadLinesResponse

    fun getTopHeadlines(country: String) = viewModelScope.launch {
        _topHeadLinesResponse.postValue(Resource.loading(null))
        mainRepository.getTopHeadlines(country).let {
            if (it.isSuccessful){
                _topHeadLinesResponse.postValue(Resource.success(it.body()))
                it.body()?.articles?.let { article -> mainRepository.addTopHeadlines(article) }
            } else {
                _topHeadLinesResponse.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }

    fun getTopHeadlinesFromLocal() = viewModelScope.launch {
        _topHeadLinesResponse.postValue(Resource.loading(null))
        val _topHeadlinesLocalResponse = mainRepository.getTopHeadlinesFromLocal()
        val topHeadlines = NewsResponseModel(_topHeadlinesLocalResponse,"Running", _topHeadlinesLocalResponse.size)
        _topHeadLinesResponse.postValue(Resource.success(topHeadlines))
    }
}