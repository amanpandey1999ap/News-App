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
class EverythingViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _everyThingResponse = MutableLiveData<Resource<NewsResponseModel?>>()
    val everyThingResponse : LiveData<Resource<NewsResponseModel?>>
        get() = _everyThingResponse

    fun getEverything(query: String, from: String?, to: String?, sortBy: String?) = viewModelScope.launch {
        _everyThingResponse.postValue(Resource.loading(null))
        mainRepository.getEverything(query, from, to, sortBy).let {
            if (it.isSuccessful){
                _everyThingResponse.postValue(Resource.success(it.body()))
                it.body()?.articles?.let { article -> mainRepository.addTopHeadlines(article) }
            } else {
                _everyThingResponse.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }
}