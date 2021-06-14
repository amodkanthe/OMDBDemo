package com.example.omdbdemo.api


import com.example.omdbdemo.data.MovieDetail
import com.example.omdbdemo.data.SearchResult
import com.example.omdbdemo.util.Constants
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("/")
    suspend fun getSearchResponse(
        @Query("apikey") apikey: String,
        @Query("s") seachTerm: String
    ): Response<SearchResult>

    @GET("/")
    suspend fun getMovieDetails(
        @Query("apikey") apikey: String,
        @Query("i") imdbID: String
    ): Response<MovieDetail>

}