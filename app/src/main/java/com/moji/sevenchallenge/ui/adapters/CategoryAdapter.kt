package com.moji.sevenchallenge.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moji.sevenchallenge.R
import com.moji.sevenchallenge.models.Category
import com.moji.sevenchallenge.models.MovieTitle
import kotlinx.android.synthetic.main.item_categories.view.*

class CategoryAdapter(var categoryList: List<Category>, private val mListener: ItemClickListener) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categories, parent, false)
        val holder = CategoryViewHolder(view)
        holder.setup()
        return holder
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.bind(category)
    }


    override fun getItemCount(): Int {
        return categoryList.size
    }


    inner class CategoryViewHolder(layout: View) : RecyclerView.ViewHolder(layout) {

        private val textCategoryName: TextView = layout.textCategoryName
        private val recyclerMovieTitles: RecyclerView = layout.recyclerMovieTitles
        private val movieTitleAdapter: MovieTitleAdapter

        init {
            movieTitleAdapter = MovieTitleAdapter(listOf(), object : MovieTitleAdapter.ItemClickListener{
                override fun onItemClick(movieTitle: MovieTitle) {
                    mListener.onItemClick(movieTitle)
                }
            })
        }

        fun bind(category: Category){
            textCategoryName.text = category.displayName
            recyclerMovieTitles.tag = category.name

            (recyclerMovieTitles.adapter as? MovieTitleAdapter)?.reload(category.movieTitles)

        }

        fun setup(){
            recyclerMovieTitles.apply {
                layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = movieTitleAdapter
            }
        }

    }

    /**
     * Listener to handle click event of recyclerview
     */
    interface ItemClickListener {
        fun onItemClick(movieTitle: MovieTitle)
    }
}
