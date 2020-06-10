package com.example.tfgjara.fragments.main

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.ColorFilter
import android.graphics.LightingColorFilter
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.*
import com.example.tfgjara.MainActivityViewModel
import com.example.tfgjara.R
import com.example.tfgjara.data.Champion
import kotlinx.android.synthetic.main.compbuilder_fragment.*
import java.lang.Exception


class CompBuilderFragment : Fragment(R.layout.compbuilder_fragment) {

    private val viewModel: MainActivityViewModel by activityViewModels()

    private val listAdapter:CompBuilderAdapter = CompBuilderAdapter().apply {
        this.setOnItemClick {
            selectChampion(it)
        }
    }


    companion object {
        fun newInstance() = CompBuilderFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        setupRecyclerView()
        setListeners()
        locateChampions()
    }

    private fun setupViews() {
        (requireActivity() as AppCompatActivity).supportActionBar?.run {
            setTitle(R.string.compbuilder_title)
        }
        listAdapter.submitList(viewModel.getChampions())
    }

    private fun setupRecyclerView() {
        lstCompChampions.run {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 6)
            itemAnimator = DefaultItemAnimator()
            adapter = listAdapter
        }
        listAdapter.submitList(viewModel.getChampions())
    }

    private fun setListeners() {
        imgcompChampion0.setOnClickListener {
            viewModel.getActualCompChampions()[0]=null
            locateChampions()
        }
        imgcompChampion1.setOnClickListener {
            viewModel.getActualCompChampions()[1]=null
            locateChampions()
        }
        imgcompChampion2.setOnClickListener {
            viewModel.getActualCompChampions()[2]=null
            locateChampions()
        }
        imgcompChampion3.setOnClickListener {
            viewModel.getActualCompChampions()[3]=null
            locateChampions()
        }
        imgcompChampion4.setOnClickListener {
            viewModel.getActualCompChampions()[4]=null
            locateChampions()
        }
        imgcompChampion5.setOnClickListener {
            viewModel.getActualCompChampions()[5]=null
            locateChampions()
        }
        imgcompChampion6.setOnClickListener {
            viewModel.getActualCompChampions()[6]=null
            locateChampions()
        }
        imgcompChampion7.setOnClickListener {
            viewModel.getActualCompChampions()[7]=null
            locateChampions()
        }
        imgcompChampion8.setOnClickListener {
            viewModel.getActualCompChampions()[8]=null
            locateChampions()
        }
        imgcompChampion9.setOnClickListener {
            viewModel.getActualCompChampions()[9]=null
            locateChampions()
        }

    }

    private fun selectChampion(champion:Champion) {
        if(viewModel.getActualCompChampions().count{ it==null }>0){
            viewModel.addActualCompChampions(champion)
            locateChampions()
        }
       else{
            AlertDialog.Builder(context!!)
                .setTitle("TeamComp is full")
                .setMessage("You must remove one of the current champions before adding another")
                .setCancelable(true)
                .setPositiveButton("Accept") { _, _ -> }
                .create().show()
        }

    }

    private fun locateChampions() {
        imgcompChampion0.setImageResource(selectImg(viewModel.getActualCompChampions().get(0)?.name))
        imgcompChampion1.setImageResource(selectImg(viewModel.getActualCompChampions().get(1)?.name))
        imgcompChampion2.setImageResource(selectImg(viewModel.getActualCompChampions().get(2)?.name))
        imgcompChampion3.setImageResource(selectImg(viewModel.getActualCompChampions().get(3)?.name))
        imgcompChampion4.setImageResource(selectImg(viewModel.getActualCompChampions().get(4)?.name))
        imgcompChampion5.setImageResource(selectImg(viewModel.getActualCompChampions().get(5)?.name))
        imgcompChampion6.setImageResource(selectImg(viewModel.getActualCompChampions().get(6)?.name))
        imgcompChampion7.setImageResource(selectImg(viewModel.getActualCompChampions().get(7)?.name))
        imgcompChampion8.setImageResource(selectImg(viewModel.getActualCompChampions().get(8)?.name))
        imgcompChampion9.setImageResource(selectImg(viewModel.getActualCompChampions().get(9)?.name))
        viewModel.getActualCompChampions().forEach {
            if(it!=null){
                it.traits.forEach {
                    viewModel.addActualCompTraits(it)
                }
            }
        }
        setTraits(viewModel.actualCompTraits)
        lblListTraits.text=viewModel.actualCompTraits.toString().replace("{","").replace("}","").replace("="," ")
        viewModel.resetActualCompTraits()

    }

    private fun setTraits(traits: MutableMap<String, Int>) {

        var map=traits.toList().sortedByDescending { (_ , value) -> value }.toMap().toMutableMap()
        var map2:MutableMap<String, Int> = mutableMapOf()

        map.forEach {
            if(viewModel.getChampionTrait(it.key).sets[0].min<=it.value){
                map2[it.key] = it.value
            }
        }
        printTraits(map2)

    }

    private fun printTraits(map2: MutableMap<String, Int>) {
        val views:Array<ImageView> = arrayOf(imgCompTrait0,imgCompTrait1,imgCompTrait2,imgCompTrait3,imgCompTrait4,imgCompTrait5,imgCompTrait6,imgCompTrait7)
        var count:Int=0
        views.forEach {
            try {
                it.setImageResource(selectTraitImg(map2.toList()[count].first))
                setTraitColor(it,count,map2.toList())
                count++
            }
           catch (e:Exception){
               it.setImageResource(resources.getIdentifier("transparente","drawable",context!!.getPackageName()))
           }
        }

        viewModel.actualCompTraits=map2
    }

    private fun setTraitColor(view: ImageView, i: Int, list: List<Pair<String, Int>>) {

        val filter3: ColorFilter = LightingColorFilter(resources.getColor(R.color.tier1), resources.getColor(R.color.background))
        val filter2: ColorFilter = LightingColorFilter(resources.getColor(R.color.tier2), resources.getColor(R.color.background))
        val filter1: ColorFilter = LightingColorFilter(resources.getColor(R.color.tier3), resources.getColor(R.color.background))
        val filter4: ColorFilter = LightingColorFilter(resources.getColor(R.color.tier4), resources.getColor(R.color.background))

        if(list[i].second==1&&viewModel.getChampionTrait(list[i].first).sets[0].min==list[i].second){
            view.setColorFilter(filter3)
        }
        else if(list[i].first.toLowerCase().equals("chrono")&&list[i].second==8){
            view.setColorFilter(filter4)
        }
        else if(viewModel.getChampionTrait(list[i].first).sets.size==3&&viewModel.getChampionTrait(list[i].first).sets[2].min<=list[i].second){
            view.setColorFilter(filter3)
        }
        else if(viewModel.getChampionTrait(list[i].first).sets.size==3&&viewModel.getChampionTrait(list[i].first).sets[1].min<=list[i].second){
            view.setColorFilter(filter2)
        }
        else if(viewModel.getChampionTrait(list[i].first).sets.size==2&&viewModel.getChampionTrait(list[i].first).sets[1].min<=list[i].second){
            view.setColorFilter(filter3)
        }
        else if(list[i].first.toLowerCase().equals("chrono")&&(list[i].second==4||list[i].second==5)){
            view.setColorFilter(filter2)
        }
        else if(list[i].first.toLowerCase().equals("chrono")&&(list[i].second==6||list[i].second==7)){
            view.setColorFilter(filter3)
        }
        else{
            view.setColorFilter(filter1)
        }


    }

    fun selectImg(name: String?): Int {
        if(name!=null){
            val resId: Int = resources.getIdentifier(name.toLowerCase().replace(" ","").replace("'",""), "drawable", context!!.getPackageName())
            return resId
        }
        else{
            return resources.getIdentifier("cuadrado","drawable", context!!.getPackageName())
        }
    }

    fun selectTraitImg(name: String): Int {
        val resId: Int = getResources()
            .getIdentifier(name.toLowerCase().replace(" ","").replace("'","").replace("tft3_","").replace("set3_","").replace("-",""), "drawable", context!!.getPackageName())
        return resId
    }

}