package com.example.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.recycler.databinding.PlantItemBinding
import com.example.recycler.repository.Plant


class PlantAdapter(
    val listOfPlants: List<Plant>,
    private val glide: RequestManager,
    private val onItemClick: (Int) -> Unit,
) : RecyclerView.Adapter<PlantHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantHolder =
        PlantHolder(
            PlantItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ),
            onItemClick = onItemClick,
            glide = glide
        )

    override fun onBindViewHolder(holder: PlantHolder, position: Int) {
        holder.bind(listOfPlants[position])
    }
    override fun getItemCount(): Int = listOfPlants.size

}