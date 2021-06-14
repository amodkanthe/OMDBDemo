package com.example.omdbdemo.data

import androidx.lifecycle.LiveData
import androidx.room.*



@Dao
interface SearchDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(searchItem: SearchItem?)

    @Delete
    suspend fun delete(searchItem: SearchItem?)

    @Query("SELECT * FROM searchitem WHERE id IN (:searchIds)")
    suspend fun loadAllByIds(searchIds: IntArray): List<SearchItem>

    // Simple query without parameters that returns values.
    @Query("SELECT * from searchitem")
     fun getAllSearchHistory(): LiveData<MutableList<SearchItem?>>

    // Query with parameter that returns a specific word or words.
    @Query("SELECT * FROM searchitem WHERE id LIKE :id ")
    suspend fun findWord(id: String?): List<SearchItem?>?
}