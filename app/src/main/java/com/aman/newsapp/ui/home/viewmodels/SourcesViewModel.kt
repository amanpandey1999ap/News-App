package com.aman.newsapp.ui.home.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aman.newsapp.models.response.NewsSourceResponseModel
import com.aman.newsapp.ui.home.repository.MainRepository
import com.aman.newsapp.networking.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SourcesViewModel @Inject constructor(private val mainRepository: MainRepository): ViewModel(){
    private val _sourcesResponse = MutableLiveData<Resource<NewsSourceResponseModel?>>()
    val sourcesResponse : LiveData<Resource<NewsSourceResponseModel?>>
        get() = _sourcesResponse

    fun getSources() = viewModelScope.launch {
        _sourcesResponse.postValue(Resource.loading(null))
        mainRepository.getSources().let {
            if (it.isSuccessful){
                _sourcesResponse.postValue(Resource.success(it.body()))
                it.body()?.sources?.let { source -> mainRepository.addSources(source) }
            } else {
                _sourcesResponse.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }

    fun getSourcesFromLocal() = viewModelScope.launch {
        _sourcesResponse.postValue(Resource.loading(null))
        val _sourcesLocalResponse = mainRepository.getSourcesFromLocal()
        val sources = NewsSourceResponseModel(_sourcesLocalResponse,"Running")
        _sourcesResponse.postValue(Resource.success(sources))
    }
}