package com.example.omdbdemo.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omdbdemo.R
import com.example.omdbdemo.data.SearchItem
import com.example.omdbdemo.databinding.FragmentSearchMovieBinding
import com.example.omdbdemo.util.Status
import com.example.omdbdemo.viewmodels.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.notify


/**
 * A simple [Fragment] subclass.
 * Use the [SearchMovieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SearchMovieFragment : Fragment() {
    private var searchTerm: String? = null
    private val searchViewModel: SearchViewModel by activityViewModels()
    private lateinit var binding: FragmentSearchMovieBinding
    private var historyList: List<SearchItem> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate<FragmentSearchMovieBinding>(
            inflater,
            R.layout.fragment_search_movie,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSearchResult.layoutManager = LinearLayoutManager(context)
        //setUpObserver()
        searchViewModel.getSearchHistory()
        observeHistory()
        binding.btnRetry.setOnClickListener {
            getSearchResults()
        }
        binding.etSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.etSearch.clearFocus()
                val imm =
                    activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm?.hideSoftInputFromWindow(binding.etSearch.getWindowToken(), 0)
                getSearchResults()
                return@OnEditorActionListener true
            }
            false
        })

    }

    fun getSearchResults() {
        if (!binding.etSearch.text.toString().trim().isEmpty()) {
            searchViewModel.data(binding.etSearch.text.toString())
            setUpObserver()
            searchViewModel.fetchSearchResults()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SearchMovieFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    fun observeHistory() {
//        searchViewModel._searchHistory.observe(viewLifecycleOwner, Observer {
//        })
//        searchViewModel.idAdded.observe(viewLifecycleOwner, Observer {
//            val adapter = binding.rvSearchResult.adapter as? SearchAdapter
//            val items = adapter?.items
//            val imdbId = it
//            val list = items?.filter {
//                it?.imdbID == imdbId
//            }
//            list?.forEach {
//                it?.isFav = true
//            }
//
//        })
//        searchViewModel.idRemoved.observe(viewLifecycleOwner, Observer {
//            val adapter = binding.rvSearchResult.adapter as? SearchAdapter
//            val items = adapter?.items
//            val imdbId = it
//            val list = items?.filter {
//                it?.imdbID == imdbId
//            }
//            list?.forEach {
//                it?.isFav = true
//            }
//
//        })
    }

    private fun setUpObserver() {
        searchViewModel._searchResult.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {

                    binding.rvSearchResult.adapter = SearchAdapter(it?.data?.search, listener =
                    {
                        activity?.supportFragmentManager?.beginTransaction()?.add(
                            R.id.container,
                            MovieDetailFragment.newInstance(it?.imdbID ?: "")
                        )?.addToBackStack("")?.commit()
                    }, listenerFav = { item, index ->
                        println("amod-----------0"+item?.isFav)
                        item?.isFav = !(item?.isFav ?: false)
                        println("amod-----------1"+item?.isFav)

                        index?.let { it1 ->
                            it?.data?.search?.set(it1, item)
                            searchViewModel.updateSearchResult(it.data)
                        };
                        if (item?.isFav ?: false) {
                            searchViewModel.insertSearch(item)
                        } else {
                            searchViewModel.deleteSearch(item)
                        }

                    })
                    if (it?.data?.search?.count() ?: 0 <= 0) {
                        val snackbar = view?.let { it1 ->
                            Snackbar.make(
                                it1, getString(R.string.no_result),
                                Snackbar.LENGTH_LONG
                            )
                        }
                        snackbar?.show()
                    }
                    binding.pbBestSellers.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding.llError.visibility = View.GONE
                    binding.pbBestSellers.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.pbBestSellers.visibility = View.GONE
                    binding.llError.visibility = View.VISIBLE
                }
            }
        })
    }
}