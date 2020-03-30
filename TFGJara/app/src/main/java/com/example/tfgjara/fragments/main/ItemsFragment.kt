package com.example.tfgjara.fragments.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.tfgjara.MainActivityViewModel
import com.example.tfgjara.R

class ItemsFragment : Fragment(R.layout.champion_fragment) {

    private val viewModel: MainActivityViewModel by activityViewModels()


    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }



    private fun setupViews() {
        (requireActivity() as AppCompatActivity).supportActionBar?.run {
            setTitle(R.string.main_title)
        }
    }




}
