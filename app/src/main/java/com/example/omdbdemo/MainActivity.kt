package com.example.omdbdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.example.omdbdemo.databinding.ActivityMainBinding
import com.example.omdbdemo.view.SearchMovieFragment
import com.example.omdbdemo.view.SearchViewPagerAdapter

import com.example.omdbdemo.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewpager.adapter = SearchViewPagerAdapter(this,supportFragmentManager)
        binding.tabs.setupWithViewPager(binding.viewpager)
    }
}