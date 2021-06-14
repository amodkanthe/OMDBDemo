package com.example.omdbdemo.viewmodels


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omdbdemo.BuildConfig
import com.example.omdbdemo.api.ApiService
import com.example.omdbdemo.data.RoomDBRepository
import com.example.omdbdemo.data.SearchDatabase
import com.example.omdbdemo.data.SearchItem
import com.example.omdbdemo.data.SearchResult
import com.example.omdbdemo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(private val apiService: ApiService,private val roomDBRepository: RoomDBRepository) : ViewModel(){
    private val searchResult = MutableLiveData<Resource<SearchResult?>>()

    private var searchHistory : LiveData<MutableList<SearchItem?>> = MutableLiveData<MutableList<SearchItem?>>()

    val _searchResult: LiveData<Resource<SearchResult?>>
        get() = searchResult


    val _searchHistory: LiveData<MutableList<SearchItem?>>
        get() = searchHistory

    val searchTerm = MutableLiveData<String>()

    var idRemoved = MutableLiveData<String>()

    var idAdded = MutableLiveData<String>()

    fun data(id: String?) {
        Log.d("TAG", "Current View Model Id : " + id)
        searchTerm.value = id ?: ""
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        searchResult.postValue(Resource.error("Something Went Wrong", null))
    }

    private val exceptionHandlerDB = CoroutineExceptionHandler { _, exception ->
    }

    fun fetchSearchResults() {
        viewModelScope.launch(exceptionHandler) {
            searchResult.postValue(Resource.loading(null))
            apiService.getSearchResponse(BuildConfig.API_KEY, searchTerm.value.toString()).let {
                if (it.isSuccessful) {
                    searchResult.postValue(Resource.success(it.body()))

                } else {
                    Log.d("TAG", "Network Call Failed")
                    searchResult.postValue(Resource.error(it.errorBody().toString(), null))
                }
            }
        }
    }

    fun getSearchHistory(){
        viewModelScope.launch(exceptionHandlerDB) {
            searchHistory = roomDBRepository.getAllSearchHistory()
        }
    }

    fun insertSearch(searchItem : SearchItem?){
        viewModelScope.launch(exceptionHandlerDB) {
            idAdded.value = searchItem?.imdbID
            roomDBRepository.insert(searchItem)
        }
    }

    fun deleteSearch(searchItem : SearchItem?){
        viewModelScope.launch(exceptionHandlerDB) {
            idRemoved.value = searchItem?.imdbID
            roomDBRepository.delete(searchItem)
        }
    }



}