package com.example.omdbdemo.data

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.omdbdemo.R
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


data class SearchResult(

    @field:SerializedName("Response")
    val response: String? = null,

    @field:SerializedName("totalResults")
    val totalResults: String? = null,

    @field:SerializedName("Search")
    val search: List<SearchItem?>? = null
)

@Entity(tableName = "searchitem")
data class SearchItem(


    @ColumnInfo(name = "type")
    @field:SerializedName("Type")
    var type: String = "",

    @ColumnInfo(name = "year")
    @field:SerializedName("Year")
    var year: String = "",

    @ColumnInfo(name = "id")
    @PrimaryKey
    @field:SerializedName("imdbID")
    var imdbID: String = "",

    @ColumnInfo(name = "poster")
    @field:SerializedName("Poster")
    var poster: String = "",

    @ColumnInfo(name = "title")
    @field:SerializedName("Title")
    var title: String = "",

    @ColumnInfo(name = "isfav")
    var isFav: Boolean = false



)

@BindingAdapter("bind:imageUrl")
public fun loadImage(view: ImageView, imageUrl: String?) {
    Picasso.get()
        .load(imageUrl)
        .placeholder(R.color.light_salmon)
        .fit()
        .centerCrop()
        .into(view, object : Callback {
            override fun onSuccess() {
                // Log.d(TAG, "success")
            }

            override fun onError(e: Exception?) {
            }
        })
}

@BindingAdapter("app:favSrc")
fun setSendState(v: ImageView, isFav: Boolean) {
    val drawableRes: Int =
        if (isFav) R.drawable.ic_fav_fill else R.drawable.ic_fav
    v.setImageResource(drawableRes)
}