package com.example.tfgjara.fragments.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tfgjara.MainActivityViewModel
import com.example.tfgjara.R
import com.example.tfgjara.data.Champion
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.compbuilder_fragment_item.view.*
import kotlinx.android.synthetic.main.main_fragment_item.view.*
import kotlinx.android.synthetic.main.match_history_fragment.*

class CompBuilderAdapter : RecyclerView.Adapter<CompBuilderAdapter.ViewHolder>() {

    private var data: List<Champion> = emptyList()
    private var onItemClick:((Champion)->Unit)?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompBuilderAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.compbuilder_fragment_item, parent, false)
        return ViewHolder(itemView)
    }

    fun setOnItemClick(listener:((Champion)->Unit)){
        onItemClick=listener
    }


    override fun getItemCount(): Int {
        return data.size
    }

    fun submitList(list:List<Champion>) {

        data=list.sortedBy { x->
            x.cost
        }
        notifyDataSetChanged()
    }

    fun getList():List<Champion>{
        return data
    }


    override fun onBindViewHolder(holder: CompBuilderAdapter.ViewHolder, position: Int) {
        holder.bind(data[position])
    }


    fun getItem(position:Int): Champion {
        return data[position]
    }




    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(champion: Champion) {
            containerView.imgChampionCompBuilder.setImageResource(selectImg(champion.name))
            when (champion.cost) {
                1 -> {
                    containerView.ChampionBuilderCardView.setCardBackgroundColor(Color.WHITE)
                }
                2 -> {
                    containerView.ChampionBuilderCardView.setCardBackgroundColor(Color.GREEN)
                }
                3 -> {
                    containerView.ChampionBuilderCardView.setCardBackgroundColor(Color.BLUE)
                }
                4 -> {
                    containerView.ChampionBuilderCardView.setCardBackgroundColor(Color.MAGENTA)
                }
                5 -> {
                    containerView.ChampionBuilderCardView.setCardBackgroundColor(Color.YELLOW)
                }
            }
            containerView.setOnClickListener {
                onItemClick?.invoke(champion)
            }


        }
        fun selectImg(name: String): Int {
            val resId: Int = containerView.getResources()
                .getIdentifier(name.toLowerCase().replace(" ","").replace("'",""), "drawable", containerView.context.getPackageName())
            return resId

        }

    }

}