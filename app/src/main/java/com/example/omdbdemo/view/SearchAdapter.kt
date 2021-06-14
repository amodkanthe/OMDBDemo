package com.example.omdbdemo.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.omdbdemo.data.SearchItem
import com.example.omdbdemo.data.setSendState
import com.example.omdbdemo.databinding.SearchResultRowLayoutBinding

class SearchAdapter(
    var items: List<SearchItem?>?,
    private val listener: ((item: SearchItem?) -> Unit)?,
    private val listenerFav: ((item: SearchItem?) -> Unit)?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SearchItemViewHolder(
            SearchResultRowLayoutBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? SearchItemViewHolder)?.bind(items?.get(position) as? SearchItem)
    }

    override fun getItemCount(): Int {
        return items?.count() ?: 0
    }

    inner class SearchItemViewHolder(val binding: SearchResultRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: SearchItem?) {
            binding?.model = model
            binding?.ivfav.setOnClickListener(View.OnClickListener {
                this@SearchAdapter.listenerFav?.invoke(this@SearchAdapter.items?.get(adapterPosition))
                notifyItemChanged(adapterPosition)
            })
            //binding.ivfav.setImageResource((items?.get(adapterPosition)?.isFav ?: false) ? R.dr)
            binding?.root.setOnClickListener(View.OnClickListener {
                this@SearchAdapter.listener?.invoke(this@SearchAdapter.items?.get(adapterPosition))

            })
           // binding?.ivfav.setIma
            binding?.executePendingBindings()
        }
    }
}