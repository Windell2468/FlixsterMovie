package com.example.flixster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PersonAdapter(
    private val persons: List<Person>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(person: Person)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_person, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = persons[position]
        holder.bind(person, listener)
    }

    override fun getItemCount(): Int = persons.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(person: Person, listener: OnItemClickListener) {
            itemView.setOnClickListener { listener.onItemClick(person) }
            itemView.tvName.text = person.name
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w185/${person.profilePath}")
                .placeholder(R.drawable.ic_avatar_placeholder)
                .into(itemView.ivProfile)
        }
    }
}
