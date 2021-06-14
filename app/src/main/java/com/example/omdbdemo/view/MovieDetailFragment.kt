package com.example.omdbdemo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omdbdemo.R
import com.example.omdbdemo.data.MovieCategories
import com.example.omdbdemo.data.MovieGenericItem
import com.example.omdbdemo.data.MoviePosterItem
import com.example.omdbdemo.data.MovieTitleItem
import com.example.omdbdemo.databinding.FragmentMovieDetailBinding
import com.example.omdbdemo.databinding.FragmentSearchMovieBinding
import com.example.omdbdemo.util.Status
import com.example.omdbdemo.viewmodels.MovieDetailViewModel
import com.example.omdbdemo.viewmodels.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_IMDBID = "imbd_id"


/**
 * A simple [Fragment] subclass.
 * Use the [MovieDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private var imdbID: String? = null

    private val movieDetailViewModel: MovieDetailViewModel by viewModels()
    private lateinit var binding: FragmentMovieDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imdbID = it.getString(ARG_IMDBID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentMovieDetailBinding>(
            inflater,
            R.layout.fragment_movie_detail,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvDetails.layoutManager = LinearLayoutManager(context)
        movieDetailViewModel.imdbID.value = imdbID
        setUpObserver()
        getMovieDetails()
        view.isClickable = true
        context?.let{view.setBackgroundColor(ContextCompat.getColor(it,R.color.white))}
        binding.btnRetry.setOnClickListener(View.OnClickListener {
            getMovieDetails()
        })


    }

    fun getMovieDetails(){
        movieDetailViewModel.fetchDetails()
    }

    private fun setUpObserver() {
        movieDetailViewModel.movieDetail.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    var items : MutableList<Any> = arrayListOf()
                    items.add(MovieTitleItem(it?.data?.title,it?.data?.type,it?.data?.year,it?.data?.rated,it?.data?.runtime))
                    items.add(MoviePosterItem(it?.data?.poster))
                    items.add(MovieCategories(it?.data?.genre?.replace(" ","")?.split(",")))
                    items.add(MovieGenericItem(getString(R.string.plot),it?.data?.plot))
                    items.add(MovieGenericItem(getString(R.string.actors),it?.data?.actors))
                    items.add(MovieGenericItem(getString(R.string.imdb_rating),it?.data?.imdbRating))
                    items.add(MovieGenericItem(getString(R.string.director),it?.data?.director))
                    items.add(MovieGenericItem(getString(R.string.Writer),it?.data?.writer))
                    binding.rvDetails.adapter = MovieDetailsAdapter(items)
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
    companion object {

        @JvmStatic
        fun newInstance(imdbId: String) =
            MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_IMDBID, imdbId)
                }
            }
    }
}