package com.example.tfgjara.fragments.main

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.tfgjara.MainActivityViewModel
import com.example.tfgjara.R
import com.example.tfgjara.data.Champion
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.compbuilder_fragment_item.view.*
import kotlinx.android.synthetic.main.game_fragment_item.view.*
import kotlinx.android.synthetic.main.match_history_fragment_item.view.*
import no.stelar7.api.r4j.pojo.tft.TFTMatch
import no.stelar7.api.r4j.pojo.tft.TFTParticipant
import no.stelar7.api.r4j.pojo.tft.TFTUnit

class GameFragmentAdapter : RecyclerView.Adapter<GameFragmentAdapter.ViewHolder>() {

    private var data: List<TFTParticipant> = emptyList()
    private var names:Map<String,String> = mapOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameFragmentAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.game_fragment_item, parent, false)
        return ViewHolder(itemView)
    }

    fun loadNames(nameList:Map<String,String>){
        names=nameList
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun submitList(list:List<TFTParticipant>) {

        data=list.sortedBy { x->
           x.placement
        }
        notifyDataSetChanged()
    }

    fun getList():List<TFTParticipant>{
        return data
    }


    override fun onBindViewHolder(holder: GameFragmentAdapter.ViewHolder, position: Int) {
        holder.bind(data[position])

    }

    fun getItem(position:Int): TFTParticipant {
        return data[position]
    }



    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(participant: TFTParticipant) {

            if(names.isNotEmpty()){
                containerView.lblPlayerName.text=names[participant.puuid]
            }

            containerView.lblPlayerLvl.text="Lvl "+participant.level
            containerView.imgPlayerPet.setImageResource(selectImgPet(participant.companion.species))
            loadPlayerUnits(participant.units,participant.units.size)

        }

        private fun loadPlayerUnits(list: List<TFTUnit>, size: Int) {
            if (size >= 10) {
                containerView.imgPlayerChampion0.setImageResource(selectImg(list[0].characterId))
                containerView.imgPlayerChampion1.setImageResource(selectImg(list[1].characterId))
                containerView.imgPlayerChampion2.setImageResource(selectImg(list[2].characterId))
                containerView.imgPlayerChampion3.setImageResource(selectImg(list[3].characterId))
                containerView.imgPlayerChampion4.setImageResource(selectImg(list[4].characterId))
                containerView.imgPlayerChampion5.setImageResource(selectImg(list[5].characterId))
                containerView.imgPlayerChampion6.setImageResource(selectImg(list[6].characterId))
                containerView.imgPlayerChampion7.setImageResource(selectImg(list[7].characterId))
                containerView.imgPlayerChampion8.setImageResource(selectImg(list[8].characterId))
                containerView.imgPlayerChampion9.setImageResource(selectImg(list[9].characterId))
            } else if (size == 9) {
                containerView.imgPlayerChampion0.setImageResource(selectImg(list[0].characterId))
                containerView.imgPlayerChampion1.setImageResource(selectImg(list[1].characterId))
                containerView.imgPlayerChampion2.setImageResource(selectImg(list[2].characterId))
                containerView.imgPlayerChampion3.setImageResource(selectImg(list[3].characterId))
                containerView.imgPlayerChampion4.setImageResource(selectImg(list[4].characterId))
                containerView.imgPlayerChampion5.setImageResource(selectImg(list[5].characterId))
                containerView.imgPlayerChampion6.setImageResource(selectImg(list[6].characterId))
                containerView.imgPlayerChampion7.setImageResource(selectImg(list[7].characterId))
                containerView.imgPlayerChampion8.setImageResource(selectImg(list[8].characterId))
                containerView.imgPlayerChampion9.visibility = View.INVISIBLE
            } else if (size == 8) {
                containerView.imgPlayerChampion0.setImageResource(selectImg(list[0].characterId))
                containerView.imgPlayerChampion1.setImageResource(selectImg(list[1].characterId))
                containerView.imgPlayerChampion2.setImageResource(selectImg(list[2].characterId))
                containerView.imgPlayerChampion3.setImageResource(selectImg(list[3].characterId))
                containerView.imgPlayerChampion4.setImageResource(selectImg(list[4].characterId))
                containerView.imgPlayerChampion5.setImageResource(selectImg(list[5].characterId))
                containerView.imgPlayerChampion6.setImageResource(selectImg(list[6].characterId))
                containerView.imgPlayerChampion7.setImageResource(selectImg(list[7].characterId))
                containerView.imgPlayerChampion8.visibility = View.INVISIBLE
                containerView.imgPlayerChampion9.visibility = View.INVISIBLE
            } else if (size == 7) {
                containerView.imgPlayerChampion0.setImageResource(selectImg(list[0].characterId))
                containerView.imgPlayerChampion1.setImageResource(selectImg(list[1].characterId))
                containerView.imgPlayerChampion2.setImageResource(selectImg(list[2].characterId))
                containerView.imgPlayerChampion3.setImageResource(selectImg(list[3].characterId))
                containerView.imgPlayerChampion4.setImageResource(selectImg(list[4].characterId))
                containerView.imgPlayerChampion5.setImageResource(selectImg(list[5].characterId))
                containerView.imgPlayerChampion6.setImageResource(selectImg(list[6].characterId))
                containerView.imgPlayerChampion7.visibility = View.INVISIBLE
                containerView.imgPlayerChampion8.visibility = View.INVISIBLE
                containerView.imgPlayerChampion9.visibility = View.INVISIBLE
            } else if (size == 6) {
                containerView.imgPlayerChampion0.setImageResource(selectImg(list[0].characterId))
                containerView.imgPlayerChampion1.setImageResource(selectImg(list[1].characterId))
                containerView.imgPlayerChampion2.setImageResource(selectImg(list[2].characterId))
                containerView.imgPlayerChampion3.setImageResource(selectImg(list[3].characterId))
                containerView.imgPlayerChampion4.setImageResource(selectImg(list[4].characterId))
                containerView.imgPlayerChampion5.setImageResource(selectImg(list[5].characterId))
                containerView.imgPlayerChampion6.visibility = View.INVISIBLE
                containerView.imgPlayerChampion7.visibility = View.INVISIBLE
                containerView.imgPlayerChampion8.visibility = View.INVISIBLE
                containerView.imgPlayerChampion9.visibility = View.INVISIBLE
            } else if (size == 5) {
                containerView.imgPlayerChampion0.setImageResource(selectImg(list[0].characterId))
                containerView.imgPlayerChampion1.setImageResource(selectImg(list[1].characterId))
                containerView.imgPlayerChampion2.setImageResource(selectImg(list[2].characterId))
                containerView.imgPlayerChampion3.setImageResource(selectImg(list[3].characterId))
                containerView.imgPlayerChampion4.setImageResource(selectImg(list[4].characterId))
                containerView.imgPlayerChampion5.visibility = View.INVISIBLE
                containerView.imgPlayerChampion6.visibility = View.INVISIBLE
                containerView.imgPlayerChampion7.visibility = View.INVISIBLE
                containerView.imgPlayerChampion8.visibility = View.INVISIBLE
                containerView.imgPlayerChampion9.visibility = View.INVISIBLE
            } else if (size == 4) {
                containerView.imgPlayerChampion0.setImageResource(selectImg(list[0].characterId))
                containerView.imgPlayerChampion1.setImageResource(selectImg(list[1].characterId))
                containerView.imgPlayerChampion2.setImageResource(selectImg(list[2].characterId))
                containerView.imgPlayerChampion3.setImageResource(selectImg(list[3].characterId))
                containerView.imgPlayerChampion4.visibility = View.INVISIBLE
                containerView.imgPlayerChampion5.visibility = View.INVISIBLE
                containerView.imgPlayerChampion6.visibility = View.INVISIBLE
                containerView.imgPlayerChampion7.visibility = View.INVISIBLE
                containerView.imgPlayerChampion8.visibility = View.INVISIBLE
                containerView.imgPlayerChampion9.visibility = View.INVISIBLE
            } else if (size == 3) {
                containerView.imgPlayerChampion0.setImageResource(selectImg(list[0].characterId))
                containerView.imgPlayerChampion1.setImageResource(selectImg(list[1].characterId))
                containerView.imgPlayerChampion2.setImageResource(selectImg(list[2].characterId))
                containerView.imgPlayerChampion3.visibility = View.INVISIBLE
                containerView.imgPlayerChampion4.visibility = View.INVISIBLE
                containerView.imgPlayerChampion5.visibility = View.INVISIBLE
                containerView.imgPlayerChampion6.visibility = View.INVISIBLE
                containerView.imgPlayerChampion7.visibility = View.INVISIBLE
                containerView.imgPlayerChampion8.visibility = View.INVISIBLE
                containerView.imgPlayerChampion9.visibility = View.INVISIBLE
            }
            else {
                containerView.imgPlayerChampion0.visibility = View.INVISIBLE
                containerView.imgPlayerChampion1.visibility = View.INVISIBLE
                containerView.imgPlayerChampion2.visibility = View.INVISIBLE
                containerView.imgPlayerChampion3.visibility = View.INVISIBLE
                containerView.imgPlayerChampion4.visibility = View.INVISIBLE
                containerView.imgPlayerChampion5.visibility = View.INVISIBLE
                containerView.imgPlayerChampion6.visibility = View.INVISIBLE
                containerView.imgPlayerChampion7.visibility = View.INVISIBLE
                containerView.imgPlayerChampion8.visibility = View.INVISIBLE
                containerView.imgPlayerChampion9.visibility = View.INVISIBLE
            }

        }

        fun selectImg(name: String): Int {
            val resId: Int = containerView.getResources()
                .getIdentifier(name.toLowerCase().replace(" ","").replace("'","").replace("tft3_","").replace("set3_",""), "drawable", containerView.context.getPackageName())
            return resId
        }


        fun selectImgPet(name: String): Int {
            val resId: Int = containerView.getResources().getIdentifier(name.toLowerCase().replace("pet",""), "drawable", containerView.context.getPackageName())
            if(resId==0){
                return R.drawable.tftavatar
            }
            return resId
        }
    }


}