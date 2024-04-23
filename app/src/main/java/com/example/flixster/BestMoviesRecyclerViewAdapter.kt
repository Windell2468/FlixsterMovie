package com.example.flixster

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BestMoviesRecyclerViewAdapter(
    private val movies: List<BestMovie>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<BestMoviesRecyclerViewAdapter.MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_best_movie, parent, false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MoviesViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: BestMovie? = null
        val mMovieTitle: TextView = mView.findViewById(R.id.movie_title)
        val mMovieDescription: TextView = mView.findViewById(R.id.movie_description)
        val mMovieImage: ImageView = mView.findViewById(R.id.movie_image)

        override fun toString(): String {
            return mMovieTitle.text.toString() + " '" + mMovieDescription.text + "'"
        }
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = movies[position]

        holder.mItem = movie
        holder.mMovieTitle.text = movie.title
        holder.mMovieDescription.text = movie.description

        // Use the context from parent view
        val context: Context = holder.itemView.context

        // Check if movieImageUrl is not null before loading with Glide
        movie.movieImageUrl?.let {
            Glide.with(context)
                .load(it)
                .centerInside()
                .into(holder.mMovieImage)
        }

        holder.mView.setOnClickListener {
            holder.mItem?.let { bestMovie ->
                mListener?.onItemClick(bestMovie)
            }
        }
    }
}
