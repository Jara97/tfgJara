package com.example.tfgjara.fragments.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.example.tfgjara.MainActivityViewModel
import com.example.tfgjara.R
import kotlinx.android.synthetic.main.match_history_fragment.*
import kotlinx.android.synthetic.main.profile_fragment.*

class MatchHistoryFragment : Fragment(R.layout.match_history_fragment) {

    private val viewModel: MainActivityViewModel by activityViewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        setObservers()
        setListeners()
    }

    private fun setListeners() {

    }

    private fun setupViews() {
        (requireActivity() as AppCompatActivity).supportActionBar?.run {
            setTitle(R.string.match_history)
        }

    }

    private fun setObservers() {
        viewModel.gamesData.observe(this){
            if(!it.isEmpty()){

            }
        }
    }

}