package com.example.recycler.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.recycler.databinding.FragmentDescriptionBinding
import com.example.recycler.repository.Plants


class DescriptionFragment : Fragment() {
    private var _binding: FragmentDescriptionBinding? = null
    private val binding by lazy { _binding!!}

    private var plantID: Int  = -100


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        plantID = arguments?.getInt(PLANT_ID_TAG)?:-100

        _binding = FragmentDescriptionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout() {
        val plant = Plants.listOfPlants[plantID]

        with(binding) {
            tvDescription.setText(plant.description)

            Glide.with(root)
                .load(plant.imageUrl)
                .error(androidx.vectordrawable.R.drawable.notify_panel_notification_icon_bg)
                .into(ivPlant)
        }
    }

    companion object {
        const val DESCRIPTION_FRAGMENT_TAG = "DESCRIPTION_FRAGMENT_TAG"
        private const val PLANT_ID_TAG = "PLANT_ID_TAG"
        fun newInstance(itemPosition : Int) =
            DescriptionFragment().apply {
                arguments = Bundle().apply {
                    putInt(PLANT_ID_TAG, itemPosition)
                }
            }
    }
}