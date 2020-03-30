package com.example.tfgjara.fragments.main

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tfgjara.R
import com.example.tfgjara.data.Champion
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.main_fragment.view.*
import kotlinx.android.synthetic.main.main_fragment_item.view.*



class MainFragmentAdapter : RecyclerView.Adapter<MainFragmentAdapter.ViewHolder>() {

    private var data: List<Champion> = emptyList()
    private var onItemClick:((Champion)->Unit)?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.main_fragment_item, parent, false)
        return ViewHolder(itemView)
    }

    fun setOnItemClick(listener:((Champion)->Unit)){
        onItemClick=listener
    }


    override fun getItemCount(): Int {
        return data.size
    }

    fun submitList(list:List<Champion>) {
        data=list
        notifyDataSetChanged()
    }

    fun getList():List<Champion>{
        return data
    }


    override fun onBindViewHolder(holder: MainFragmentAdapter.ViewHolder, position: Int) {
        holder.bind(data[position])
    }


    fun getItem(position:Int): Champion {
        return data[position]
    }


    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {


        init {
        }


        fun bind(champion: Champion) {
            containerView.lblCName.setText(champion.name)
            if(champion.traits.size==2){
                containerView.lblCClass.setText(champion.traits[1])
            }
            else {
                containerView.lblCClass.setText(champion.traits[1]+" "+champion.traits?.get(2))
            }
            containerView.lblCCost.text=champion.cost.toString()
            containerView.lblCOrigin.setText(champion.traits[0])
            containerView.imgChampion.setImageResource(selectImg(champion.name))
            when (champion.cost) {
                1 -> {
                    containerView.cardShadow.setCardBackgroundColor(Color.WHITE)
                }
                2 -> {
                    containerView.cardShadow.setCardBackgroundColor(Color.GREEN)
                }
                3 -> {
                    containerView.cardShadow.setCardBackgroundColor(Color.BLUE)
                }
                4 -> {
                    containerView.cardShadow.setCardBackgroundColor(Color.MAGENTA)
                }
                5 -> {
                    containerView.cardShadow.setCardBackgroundColor(Color.YELLOW)
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