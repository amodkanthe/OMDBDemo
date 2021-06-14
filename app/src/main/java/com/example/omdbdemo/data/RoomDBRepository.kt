package com.example.omdbdemo.data

import javax.inject.Inject

class RoomDBRepository @Inject constructor(private val searchDao : SearchDao) {
    suspend fun insert(searchItem: SearchItem?) = searchDao.insert(searchItem)
    suspend fun delete(searchItem: SearchItem?) = searchDao.delete(searchItem)
    suspend fun getAllSearchHistory() = searchDao.getAllSearchHistory()
}