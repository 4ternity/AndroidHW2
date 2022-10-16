package com.example.task2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.task2.databinding.ActivityMainBinding
import com.example.task2.fragments.SecondFragment
import com.example.task2.fragments.ThirdFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val containerId: Int = R.id.main_fragments_container
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState != null) {
            return
        }
        supportFragmentManager.beginTransaction().add(
            containerId,
            ThirdFragment.getInstance(Bundle()),
            ThirdFragment.THIRD_FRAGMENT_TAG
        ).addToBackStack (null)
            .commit()
    }
}