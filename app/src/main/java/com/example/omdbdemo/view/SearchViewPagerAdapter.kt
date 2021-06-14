package com.example.omdbdemo.view

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.omdbdemo.R


class SearchViewPagerAdapter(private val myContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                //  val homeFragment: HomeFragment = HomeFragment()
                return SearchMovieFragment.newInstance()
            }
            1 -> {
                return SearchHistoryFragment.newInstance()
            }
            else -> return Fragment()
        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if (position == 0) {
            return myContext.getString(R.string.search)
        } else {
            return myContext.getString(R.string.favourites)
        }
    }
}