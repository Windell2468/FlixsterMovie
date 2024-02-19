package com.example.flixster

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class BestMoviesRecyclerViewAdapter (
    private val movies: List<BestMovie>,
    private val mListener: OnListFragmentInteractionListener?
)   : RecyclerView.Adapter<BestMoviesRecyclerViewAdapter.MoviesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_best_movie, parent, false)
        return BookViewHolder(view)
    }
    override fun getItemCount(): Int {
        return movies.size
    }

    inner class BookViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mBookButton: View =
            mView.findViewById<View>(id.buyticket_button) // Initialize mBookButton with appropriate View type
        var mItem: BestMovie? = null
        val mMovieTitle: TextView = mView.findViewById<View>(id.movie_title) as TextView
        val mMovieAuthor: TextView = mView.findViewById<View>(id.movie_author) as TextView
        val mMovieRanking: TextView = mView.findViewById<View>(id.movieranking) as TextView
        val mMovieDescription: TextView = mView.findViewById<View>(id.movie_description) as TextView
        val mMovieImage: ImageView = mView.findViewById<View>(id.movie_image) as ImageView

        override fun toString(): String {
            return mMovieTitle.toString() + " '" + mMovieAuthor.text + "'"
        }

        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
            val movie = movies[position]

            holder.mItem = movie
            holder.mMovieTitle.text = movie.title
            holder.mMovieAuthor.text = movie.author
            holder.mMovieDescription.text = movie.description
            holder.mMovieRanking.text = movie.rank.toString()

            Glide.with(holder.mView)
                .load(movie.movieImageUrl)
                .centerInside()
                .into(holder.mMovieImage)

            holder.mView.setOnClickListener {
                holder.mItem?.let { book ->
                    mListener?.onItemClick(movie)
                }
            }
            holder.mBookButton.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(movie.flixsterUrl))
                ContextCompat.startActivity(it.context, browserIntent, null)
            }
        }
    }

