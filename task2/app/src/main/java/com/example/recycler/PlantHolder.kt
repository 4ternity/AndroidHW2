package com.example.recycler

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.recycler.databinding.PlantItemBinding
import com.example.recycler.repository.Plant

class PlantHolder(
    private val binding: PlantItemBinding,
    val onItemClick: (Int) -> Unit,
    private val glide: RequestManager,
) : RecyclerView.ViewHolder(binding.layoutItem) {

    init {
        with(binding) {
            root.setOnClickListener {

                onItemClick(adapterPosition)
            }
        }
    }

    fun bind(plant: Plant) {

        with(binding) {
            tvPlantName.text = plant.name
            this.plant = plant

            if (plant.colorID != 0) {
                layoutItem.setBackgroundColor(
                    ContextCompat.getColor(
                        root.context,
                        plant.colorID
                    )
                )
            }


            glide
                .load(plant.imageUrl)
                .listener(object:RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {

                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressCircular.visibility = ViewGroup.INVISIBLE
                        return false
                    }

                } )

                .into(ivPlant)
        }
    }
}