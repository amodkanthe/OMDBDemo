package com.example.omdbdemo.di

import android.content.Context
import com.example.omdbdemo.api.ApiService
import com.example.omdbdemo.data.RoomDBRepository
import com.example.omdbdemo.data.SearchDao
import com.example.omdbdemo.data.SearchDatabase
import com.example.omdbdemo.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private val interceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient.Builder().addNetworkInterceptor(interceptor).build()

    @Provides
    @Singleton
    fun provideSearchDao(@ApplicationContext appContext: Context) : SearchDao {
        return SearchDatabase.getDatabase(appContext).searchDao()
    }

    @Provides
    @Singleton
    fun provideSearchDaoDBRepository(searchDao: SearchDao) = RoomDBRepository(searchDao)

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}