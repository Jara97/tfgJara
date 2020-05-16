package com.example.tfgjara.fragments.main

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tfgjara.MainActivityViewModel
import com.example.tfgjara.R
import com.example.tfgjara.data.Champion
import com.example.tfgjara.data.Skill
import kotlinx.android.synthetic.main.champion_fragment.*
import kotlinx.android.synthetic.main.compbuilder_fragment.*
import kotlinx.android.synthetic.main.game_fragment.*


class GameFragment : Fragment(R.layout.game_fragment) {

    private val viewModel: MainActivityViewModel by activityViewModels()

    private val listAdapter:GameFragmentAdapter = GameFragmentAdapter().apply {
    }

    private val navController: NavController by lazy {
        findNavController()
    }

    companion object {
        fun newInstance() = GameFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        setupRecyclerView()
        setListeners()
        setObservers()
        viewModel.loadPlayerNames(viewModel.getGame())

    }

    private fun setObservers() {
        viewModel.names.observe(this){
            listAdapter.loadNames(it)
        }
    }

    private fun setListeners() {
        btnBackHistory.setOnClickListener {
            navController.navigate(R.id.matchHistoryNavigation)
        }
    }

    private fun setupViews() {

        (requireActivity() as AppCompatActivity).supportActionBar?.run {
            setTitle(R.string.game_info)
            setDisplayHomeAsUpEnabled(false)
            setHomeButtonEnabled(false)
        }
        lblGameDuration.text=String.format("%.2f min",viewModel.actualGameDuration.div(60.00f)).replace(".",":")
        lblGameDate.text=viewModel.actualGameDate
        lblGameType.text=viewModel.actualGameType

    }

    private fun setupRecyclerView() {
        lstMatch2.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = listAdapter
        }
        listAdapter.submitList(viewModel.getGame())
    }



}