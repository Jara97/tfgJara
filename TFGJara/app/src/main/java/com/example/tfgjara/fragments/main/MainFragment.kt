package com.example.tfgjara.fragments.main

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


class MainFragment : Fragment(R.layout.main_fragment) {

    private val viewModel: MainActivityViewModel by activityViewModels()


    private val navController: NavController by lazy {
       findNavController()
    }

    private val listAdapter: MainFragmentAdapter = MainFragmentAdapter().apply {
        this.setOnItemClick {
            viewModel.selectChampion(it)
            navController.navigate(R.id.championNavigation)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        setupRecyclerView()
        setListeners()
        setObservers()

    }

    private fun setupViews() {
        (requireActivity() as AppCompatActivity).supportActionBar?.run {
            setTitle(R.string.main_title)
        }
    }


    private fun setupRecyclerView() {
        lstC.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(
                DividerItemDecoration(context, RecyclerView.VERTICAL).apply { setDrawable(context.getDrawable(R.drawable.divider)!!) })
            adapter = listAdapter
        }
        listAdapter.submitList(viewModel.getChampions().sortedBy { x->x.name })
        viewModel.setSort(1)
    }

    private fun setListeners() {
        lblChampion.setOnClickListener {
            if(viewModel.actualSort.value==1){
                listAdapter.submitList(listAdapter.getList().sortedByDescending {
                    viewModel.setSort(-1)
                    it.name
                })
            }
            else{
                listAdapter.submitList(listAdapter.getList().sortedBy {
                    viewModel.setSort(1)
                    it.name
                })
            }
        }

        lblCost.setOnClickListener {
            if(viewModel.actualSort.value==2){
                listAdapter.submitList(listAdapter.getList().sortedByDescending {
                    viewModel.setSort(-2)
                    it.cost
                })
            }
            else{
                listAdapter.submitList(listAdapter.getList().sortedBy {
                    viewModel.setSort(2)
                    it.cost
                })
            }
        }

        lblOrigin.setOnClickListener {
            if(viewModel.actualSort.value==3){
                listAdapter.submitList(listAdapter.getList().sortedByDescending {
                    viewModel.setSort(-3)
                    it.traits[0]
                })
            }
            else{
                listAdapter.submitList(listAdapter.getList().sortedBy {
                    viewModel.setSort(3)
                    it.traits[0]
                })
            }
        }

        lblClass.setOnClickListener {
            if(viewModel.actualSort.value==4){
                listAdapter.submitList(listAdapter.getList().sortedByDescending {
                    viewModel.setSort(-4)
                    it.traits[1]
                })
            }
            else{
                listAdapter.submitList(listAdapter.getList().sortedBy {
                    viewModel.setSort(4)
                    it.traits[1]
                })
            }
        }


    }

    private fun setObservers() {
        viewModel.actualSort.observe(this){
            if(it==1){
                lblChampion.setTextColor(resources.getColor(R.color.blueSoft))
            }
            else if(it==-1){
                lblChampion.setTextColor(resources.getColor(R.color.sortDescending))
            }
            else{
                lblChampion.setTextColor(resources.getColor(R.color.gray))
            }

            if(it==2){
                lblCost.setTextColor(resources.getColor(R.color.blueSoft))
            }
            else if(it==-2){
                lblCost.setTextColor(resources.getColor(R.color.sortDescending))
            }
            else{
                lblCost.setTextColor(resources.getColor(R.color.gray))
            }

            if(it==3){
                lblOrigin.setTextColor(resources.getColor(R.color.blueSoft))
            }
            else if(it==-3){
                lblOrigin.setTextColor(resources.getColor(R.color.sortDescending))
            }
            else{
                lblOrigin.setTextColor(resources.getColor(R.color.gray))
            }

            if(it==4){
                lblClass.setTextColor(resources.getColor(R.color.blueSoft))
            }
            else if(it==-4){
                lblClass.setTextColor(resources.getColor(R.color.sortDescending))
            }
            else{
                lblClass.setTextColor(resources.getColor(R.color.gray))
            }
        }
    }



}