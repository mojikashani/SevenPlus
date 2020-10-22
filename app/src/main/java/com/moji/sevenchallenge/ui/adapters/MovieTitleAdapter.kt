package com.moji.sevenchallenge.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.moji.sevenchallenge.BuildConfig
import com.moji.sevenchallenge.R
import com.moji.sevenchallenge.models.MovieTitle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie_title.view.*

class MovieTitleAdapter(var movieTitleList: List<MovieTitle>, private val mListener: ItemClickListener) : RecyclerView.Adapter<MovieTitleAdapter.MovieTitleViewHolder>() {


    fun reload(movieTitleList: List<MovieTitle>) {
        this.movieTitleList = movieTitleList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTitleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_title, parent, false)
        return MovieTitleViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieTitleViewHolder, position: Int) {
        val movieTitle = movieTitleList[position]
        holder.bind(movieTitle)
    }

    override fun getItemCount(): Int {
        return movieTitleList.size
    }


    inner class MovieTitleViewHolder(layout: View) : RecyclerView.ViewHolder(layout) {

        private val imgThumbnail: ImageView = layout.imageThumbnail

        fun bind(movieTitle: MovieTitle) {

            movieTitle.posterPath?.let { imgName ->
                val imgURl = BuildConfig.THUMBNAIL_URL_SMALL + imgName
                Picasso.get().load(imgURl).into(imgThumbnail)
            }

            setFadeAnimation(itemView)

            itemView.setOnClickListener {
                mListener.onItemClick(movieTitle)
            }
        }

        private fun setFadeAnimation(view: View) {
            val anim = AlphaAnimation(0.0f, 1.0f)
            anim.duration = 300
            view.startAnimation(anim)
        }
    }


    /**
     * Listener to handle click event of recyclerview
     */
    interface ItemClickListener {
        fun onItemClick(movieTitle: MovieTitle)
    }
}
