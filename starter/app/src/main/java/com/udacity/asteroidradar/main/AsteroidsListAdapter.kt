package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidLayoutBinding


class AsteroidsListAdapter(private val mainViewModel: MainViewModel) :
    ListAdapter<Asteroid, AsteroidsListAdapter.AsteroidElementViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidElementViewHolder {
        return AsteroidElementViewHolder(
            AsteroidLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AsteroidElementViewHolder, position: Int) {
        holder.bind(getItem(position), mainViewModel)
    }

    class AsteroidElementViewHolder(private var binding: AsteroidLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(asteroid: Asteroid, mainViewModel: MainViewModel) {
            binding.mainViewModel = mainViewModel
            binding.asteroid = asteroid
            binding.executePendingBindings()
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }
    }
}