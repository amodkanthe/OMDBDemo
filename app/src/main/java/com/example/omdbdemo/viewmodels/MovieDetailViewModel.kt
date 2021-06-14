package com.example.omdbdemo.viewmodels


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omdbdemo.BuildConfig
import com.example.omdbdemo.api.ApiService
import com.example.omdbdemo.data.*
import com.example.omdbdemo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val apiService: ApiService) : ViewModel(){
    val movieDetail = MutableLiveData<Resource<MovieDetail?>>()
    val imdbID = MutableLiveData<String>()

    fun data(id: String?) {
        Log.d("TAG", "Current View Model Id : " + id)
        imdbID.value = id ?: ""
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        movieDetail.postValue(Resource.error("Something Went Wrong", null))
    }

    private val exceptionHandlerDB = CoroutineExceptionHandler { _, exception ->
    }

    fun fetchDetails() {
        viewModelScope.launch(exceptionHandler) {
            movieDetail.postValue(Resource.loading(null))
            apiService.getMovieDetails(BuildConfig.API_KEY, imdbID.value.toString()).let {
                if (it.isSuccessful) {
                    movieDetail.postValue(Resource.success(it.body()))

                } else {
                    Log.d("TAG", "Network Call Failed")
                    movieDetail.postValue(Resource.error(it.errorBody().toString(), null))
                }
            }
        }
    }



}