package com.example.omdbdemo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omdbdemo.R
import com.example.omdbdemo.databinding.FragmentSearchHistoryBinding
import com.example.omdbdemo.databinding.FragmentSearchMovieBinding
import com.example.omdbdemo.viewmodels.SearchViewModel


class SearchHistoryFragment : Fragment() {

    private lateinit var binding: FragmentSearchHistoryBinding
    private val searchViewModel: SearchViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentSearchHistoryBinding>(
            inflater,
            R.layout.fragment_search_history,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvHistory.layoutManager = LinearLayoutManager(context)
        searchViewModel.getSearchHistory()
        observeResult()
    }

    fun observeResult() {
        searchViewModel._searchHistory.observe(viewLifecycleOwner, Observer {
            binding.rvHistory.adapter = SearchAdapter(it, listener = {
                activity?.supportFragmentManager?.beginTransaction()?.add(R.id.container,MovieDetailFragment.newInstance(it?.imdbID ?: ""))?.addToBackStack("")?.commit()
            }, listenerFav = {
                searchViewModel.deleteSearch(searchItem = it)
            }
            )
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchHistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            SearchHistoryFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}