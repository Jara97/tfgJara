package com.example.tfgjara.fragments.main

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfgjara.MainActivityViewModel
import com.example.tfgjara.R
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.match_history_fragment.*
import kotlinx.android.synthetic.main.profile_fragment.*

class MatchHistoryFragment : Fragment(R.layout.match_history_fragment) {

    private val viewModel: MainActivityViewModel by activityViewModels()

    private val navController: NavController by lazy {
        findNavController()
    }

    private val listAdapter: MatchHistoryFragmentAdapter = MatchHistoryFragmentAdapter().apply {
        this.setOnItemClick {
            viewModel.setGame(it)
            navController.navigate(R.id.gameNavigation)
        }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        setupRecyclerView()
        setObservers()
        setListeners()
    }

    private fun setListeners() {

    }

    private fun setupRecyclerView() {
        lstMatch.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL).apply { setDrawable(context.getDrawable(R.drawable.divider)!!) })
            listAdapter.setPlayerPuuid(viewModel.actualSumonner.value!!.puuid)
            adapter = listAdapter
        }
        listAdapter.submitList(viewModel.getGamesData())

    }

    private fun setupViews() {
        (requireActivity() as AppCompatActivity).supportActionBar?.run {
            setTitle(R.string.match_history)
            setDisplayHomeAsUpEnabled(false)
            setHomeButtonEnabled(false)
        }

    }

    private fun setObservers() {

    }

}