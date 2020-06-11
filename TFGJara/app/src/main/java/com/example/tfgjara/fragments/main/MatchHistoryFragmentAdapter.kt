package com.example.tfgjara.fragments.main

import android.graphics.ColorFilter
import android.graphics.LightingColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tfgjara.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.match_history_fragment_item.view.*
import no.stelar7.api.r4j.pojo.tft.TFTMatch
import no.stelar7.api.r4j.pojo.tft.TFTParticipant
import no.stelar7.api.r4j.pojo.tft.TFTTrait
import no.stelar7.api.r4j.pojo.tft.TFTUnit
import java.time.format.DateTimeFormatter
import java.util.*


class MatchHistoryFragmentAdapter : RecyclerView.Adapter<MatchHistoryFragmentAdapter.ViewHolder>() {

    private var data: List<TFTMatch> = emptyList()
    private var onItemClick:((TFTMatch)->Unit)?=null
    private var playerPuuid:String=""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MatchHistoryFragmentAdapter.ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.match_history_fragment_item, parent, false)
        return ViewHolder(itemView)
    }

    fun setOnItemClick(listener:((TFTMatch)->Unit)){
        onItemClick=listener
    }


    override fun getItemCount(): Int {
        return data.size
    }

    fun submitList(list:List<TFTMatch>) {
        data=list
        notifyDataSetChanged()
    }

    fun getList():List<TFTMatch>{
        return data
    }

    fun setPlayerPuuid(puuid:String){
        playerPuuid=puuid
    }


    override fun onBindViewHolder(holder:MatchHistoryFragmentAdapter.ViewHolder, position: Int) {
        holder.bind(data[position])
    }


    fun getItem(position:Int):TFTMatch {
        return data[position]
    }


    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {


        init {
        }


        fun bind(tftMatch: TFTMatch) {
            val player:TFTParticipant=tftMatch.participants.find { x->
                x.puuid==playerPuuid
            }?:tftMatch.participants.get(0)

            containerView.lblMatchDate.text=(tftMatch.matchCreationAsDate.toString().substring(8,10)+tftMatch.matchCreationAsDate.toString().substring(4,8)+tftMatch.matchCreationAsDate.toString().substring(0,4)).replace("-","/")


            containerView.lblMatchPosition.text=player.placement.toString()+"º Place"
            containerView.lblMatchGalaxy.text=tftMatch.gameVariation.replace("TFT3_GameVariation_","").replace("None","No Galaxy")

            containerView.lblMatchLevel.text="LV "+player.level.toString()
            if(tftMatch.queue.name.contains("RANKED")){
                containerView.lblMatchGameType.text=containerView.context.getString( R.string.ranked_game)
            }
            else{
                containerView.lblMatchGameType.text=containerView.context.getString(R.string.normal_game)
            }

            if(player.units.isNotEmpty()){
                loadPlayerUnits(player.units,player.units.size)
                loadPlayerUnitStars(player.units,player.units.size)
            }
            loadPlayerTraits(player.traits)

            if(player.placement<=3){
                containerView.linearLayoutColor.setBackgroundColor(containerView.resources.getColor(R.color.win))
            }
            else if(player.placement>=6){
                containerView.linearLayoutColor.setBackgroundColor(containerView.resources.getColor(R.color.lose))
            }
            else{
                containerView.linearLayoutColor.setBackgroundColor(containerView.resources.getColor(R.color.mid))
            }

            containerView.imgMatchMiniLegend.setImageResource(selectImgPet(player.companion.species))

            containerView.setOnClickListener {
                onItemClick?.invoke(tftMatch)
            }
        }

        private fun loadPlayerTraits(traitList: List<TFTTrait>) {
            val list=traitList.filter { x->
                x.tierCurrent>=1
            }
            val size:Int=list.size

            selectTraitcolor(list)

            if(size>=8){
                containerView.imgMatchTrait0.setImageResource(selectImg(list[0].name))
                containerView.imgMatchTrait1.setImageResource(selectImg(list[1].name))
                containerView.imgMatchTrait2.setImageResource(selectImg(list[2].name))
                containerView.imgMatchTrait3.setImageResource(selectImg(list[3].name))
                containerView.imgMatchTrait4.setImageResource(selectImg(list[4].name))
                containerView.imgMatchTrait5.setImageResource(selectImg(list[5].name))
                containerView.imgMatchTrait6.setImageResource(selectImg(list[6].name))
                containerView.imgMatchTrait7.setImageResource(selectImg(list[7].name))
            }
            else if(size==7){
                containerView.imgMatchTrait0.setImageResource(selectImg(list[0].name))
                containerView.imgMatchTrait1.setImageResource(selectImg(list[1].name))
                containerView.imgMatchTrait2.setImageResource(selectImg(list[2].name))
                containerView.imgMatchTrait3.setImageResource(selectImg(list[3].name))
                containerView.imgMatchTrait4.setImageResource(selectImg(list[4].name))
                containerView.imgMatchTrait5.setImageResource(selectImg(list[5].name))
                containerView.imgMatchTrait6.setImageResource(selectImg(list[6].name))
                containerView.imgMatchTrait7.visibility=View.INVISIBLE
            }
            else if(size==6){
                containerView.imgMatchTrait0.setImageResource(selectImg(list[0].name))
                containerView.imgMatchTrait1.setImageResource(selectImg(list[1].name))
                containerView.imgMatchTrait2.setImageResource(selectImg(list[2].name))
                containerView.imgMatchTrait3.setImageResource(selectImg(list[3].name))
                containerView.imgMatchTrait4.setImageResource(selectImg(list[4].name))
                containerView.imgMatchTrait5.setImageResource(selectImg(list[5].name))
                containerView.imgMatchTrait6.visibility=View.INVISIBLE
                containerView.imgMatchTrait7.visibility=View.INVISIBLE
            }
            else if(size==5){
                containerView.imgMatchTrait0.setImageResource(selectImg(list[0].name))
                containerView.imgMatchTrait1.setImageResource(selectImg(list[1].name))
                containerView.imgMatchTrait2.setImageResource(selectImg(list[2].name))
                containerView.imgMatchTrait3.setImageResource(selectImg(list[3].name))
                containerView.imgMatchTrait4.setImageResource(selectImg(list[4].name))
                containerView.imgMatchTrait5.visibility=View.INVISIBLE
                containerView.imgMatchTrait6.visibility=View.INVISIBLE
                containerView.imgMatchTrait7.visibility=View.INVISIBLE
            }
            else if(size==4){
                containerView.imgMatchTrait0.setImageResource(selectImg(list[0].name))
                containerView.imgMatchTrait1.setImageResource(selectImg(list[1].name))
                containerView.imgMatchTrait2.setImageResource(selectImg(list[2].name))
                containerView.imgMatchTrait3.setImageResource(selectImg(list[3].name))
                containerView.imgMatchTrait4.visibility=View.INVISIBLE
                containerView.imgMatchTrait5.visibility=View.INVISIBLE
                containerView.imgMatchTrait6.visibility=View.INVISIBLE
                containerView.imgMatchTrait7.visibility=View.INVISIBLE
            }
            else if(size==3){
                containerView.imgMatchTrait0.setImageResource(selectImg(list[0].name))
                containerView.imgMatchTrait1.setImageResource(selectImg(list[1].name))
                containerView.imgMatchTrait2.setImageResource(selectImg(list[2].name))
                containerView.imgMatchTrait3.visibility=View.INVISIBLE
                containerView.imgMatchTrait4.visibility=View.INVISIBLE
                containerView.imgMatchTrait5.visibility=View.INVISIBLE
                containerView.imgMatchTrait6.visibility=View.INVISIBLE
                containerView.imgMatchTrait7.visibility=View.INVISIBLE
            }
            else if(size==2){
                containerView.imgMatchTrait0.setImageResource(selectImg(list[0].name))
                containerView.imgMatchTrait1.setImageResource(selectImg(list[1].name))
                containerView.imgMatchTrait2.visibility=View.INVISIBLE
                containerView.imgMatchTrait3.visibility=View.INVISIBLE
                containerView.imgMatchTrait4.visibility=View.INVISIBLE
                containerView.imgMatchTrait5.visibility=View.INVISIBLE
                containerView.imgMatchTrait6.visibility=View.INVISIBLE
                containerView.imgMatchTrait7.visibility=View.INVISIBLE
            }
            else if(size==1){
                containerView.imgMatchTrait0.setImageResource(selectImg(list[0].name))
                containerView.imgMatchTrait1.visibility=View.INVISIBLE
                containerView.imgMatchTrait2.visibility=View.INVISIBLE
                containerView.imgMatchTrait3.visibility=View.INVISIBLE
                containerView.imgMatchTrait4.visibility=View.INVISIBLE
                containerView.imgMatchTrait5.visibility=View.INVISIBLE
                containerView.imgMatchTrait6.visibility=View.INVISIBLE
                containerView.imgMatchTrait7.visibility=View.INVISIBLE
            }
            else if(size==0){
                containerView.imgMatchTrait0.visibility=View.INVISIBLE
                containerView.imgMatchTrait1.visibility=View.INVISIBLE
                containerView.imgMatchTrait2.visibility=View.INVISIBLE
                containerView.imgMatchTrait3.visibility=View.INVISIBLE
                containerView.imgMatchTrait4.visibility=View.INVISIBLE
                containerView.imgMatchTrait5.visibility=View.INVISIBLE
                containerView.imgMatchTrait6.visibility=View.INVISIBLE
                containerView.imgMatchTrait7.visibility=View.INVISIBLE
            }
        }

        private fun selectTraitcolor(list: List<TFTTrait>) {
            val filter3: ColorFilter = LightingColorFilter(containerView.resources.getColor(R.color.tier1), containerView.resources.getColor(R.color.background))
            val filter2: ColorFilter = LightingColorFilter(containerView.resources.getColor(R.color.tier2), containerView.resources.getColor(R.color.background))
            val filter1: ColorFilter = LightingColorFilter(containerView.resources.getColor(R.color.tier3), containerView.resources.getColor(R.color.background))
            val filter4: ColorFilter = LightingColorFilter(containerView.resources.getColor(R.color.tier4), containerView.resources.getColor(R.color.background))

            try {

                when (list[0].tierCurrent) {
                    1 -> {
                        if(list[0].tierCurrent!=list[0].tierTotal)containerView.imgMatchTrait0.setColorFilter(filter1)
                        else containerView.imgMatchTrait0.setColorFilter(filter3)
                    }
                    2 -> {
                        if(list[0].tierCurrent!=list[0].tierTotal)containerView.imgMatchTrait0.setColorFilter(filter2)
                        else containerView.imgMatchTrait0.setColorFilter(filter3)
                    }
                    3 -> {
                        containerView.imgMatchTrait0.setColorFilter(filter3)
                    }
                    4 -> {
                        containerView.imgMatchTrait0.setColorFilter(filter4)
                    }
                }
                
                when (list[1].tierCurrent) {
                    1 -> {
                        if(list[1].tierCurrent!=list[1].tierTotal)containerView.imgMatchTrait1.setColorFilter(filter1)
                        else containerView.imgMatchTrait1.setColorFilter(filter3)
                    }
                    2 -> {
                        if(list[1].tierCurrent!=list[1].tierTotal)containerView.imgMatchTrait1.setColorFilter(filter2)
                        else containerView.imgMatchTrait1.setColorFilter(filter3)
                    }
                    3 -> {
                        containerView.imgMatchTrait1.setColorFilter(filter3)
                    }
                    4 -> {
                        containerView.imgMatchTrait1.setColorFilter(filter4)
                    }
                }
                when (list[2].tierCurrent) {
                    1 -> {
                        if(list[2].tierCurrent!=list[2].tierTotal)containerView.imgMatchTrait2.setColorFilter(filter1)
                        else containerView.imgMatchTrait2.setColorFilter(filter3)
                    }
                    2 -> {
                        if(list[2].tierCurrent!=list[2].tierTotal)containerView.imgMatchTrait2.setColorFilter(filter2)
                        else containerView.imgMatchTrait2.setColorFilter(filter3)
                    }
                    3 -> {
                        containerView.imgMatchTrait2.setColorFilter(filter3)
                    }
                    4 -> {
                        containerView.imgMatchTrait2.setColorFilter(filter4)
                    }
                }
                when (list[3].tierCurrent) {
                    1 -> {
                        if(list[3].tierCurrent!=list[3].tierTotal)containerView.imgMatchTrait3.setColorFilter(filter1)
                        else containerView.imgMatchTrait3.setColorFilter(filter3)
                    }
                    2 -> {
                        if(list[3].tierCurrent!=list[3].tierTotal)containerView.imgMatchTrait3.setColorFilter(filter2)
                        else containerView.imgMatchTrait3.setColorFilter(filter3)
                    }
                    3 -> {
                        containerView.imgMatchTrait3.setColorFilter(filter3)
                    }
                    4 -> {
                        containerView.imgMatchTrait3.setColorFilter(filter4)
                    }
                }
                when (list[4].tierCurrent) {
                    1 -> {
                        if(list[4].tierCurrent!=list[4].tierTotal)containerView.imgMatchTrait4.setColorFilter(filter1)
                        else containerView.imgMatchTrait4.setColorFilter(filter3)
                    }
                    2 -> {
                        if(list[4].tierCurrent!=list[4].tierTotal)containerView.imgMatchTrait4.setColorFilter(filter2)
                        else containerView.imgMatchTrait4.setColorFilter(filter3)
                    }
                    3 -> {
                        containerView.imgMatchTrait4.setColorFilter(filter3)
                    }
                    4 -> {
                        containerView.imgMatchTrait4.setColorFilter(filter4)
                    }
                }
                when (list[5].tierCurrent) {
                    1 -> {
                        if(list[5].tierCurrent!=list[5].tierTotal)containerView.imgMatchTrait5.setColorFilter(filter1)
                        else containerView.imgMatchTrait5.setColorFilter(filter3)
                    }
                    2 -> {
                        if(list[5].tierCurrent!=list[5].tierTotal)containerView.imgMatchTrait5.setColorFilter(filter2)
                        else containerView.imgMatchTrait5.setColorFilter(filter3)
                    }
                    3 -> {
                        containerView.imgMatchTrait5.setColorFilter(filter3)
                    }
                    4 -> {
                        containerView.imgMatchTrait5.setColorFilter(filter4)
                    }
                }
                when (list[6].tierCurrent) {
                    1 -> {
                        if(list[6].tierCurrent!=list[6].tierTotal)containerView.imgMatchTrait6.setColorFilter(filter1)
                        else containerView.imgMatchTrait6.setColorFilter(filter3)
                    }
                    2 -> {
                        if(list[6].tierCurrent!=list[6].tierTotal)containerView.imgMatchTrait6.setColorFilter(filter2)
                        else containerView.imgMatchTrait6.setColorFilter(filter3)
                    }
                    3 -> {
                        containerView.imgMatchTrait6.setColorFilter(filter3)
                    }
                    4 -> {
                        containerView.imgMatchTrait6.setColorFilter(filter4)
                    }
                }
                when (list[7].tierCurrent) {
                    1 -> {
                        if(list[7].tierCurrent!=list[7].tierTotal)containerView.imgMatchTrait7.setColorFilter(filter1)
                        else containerView.imgMatchTrait7.setColorFilter(filter3)
                    }
                    2 -> {
                        if(list[7].tierCurrent!=list[7 ].tierTotal)containerView.imgMatchTrait7.setColorFilter(filter2)
                        else containerView.imgMatchTrait7.setColorFilter(filter3)
                    }
                    3 -> {
                        containerView.imgMatchTrait7.setColorFilter(filter3)
                    }
                    4 -> {
                        containerView.imgMatchTrait7.setColorFilter(filter4)
                    }
                }
            }catch (e:IndexOutOfBoundsException){

            }

        }

        private fun loadPlayerUnitStars(list: List<TFTUnit>, size: Int) {
            if(size>=10){
                containerView.imgMatchChampion0Stars.setImageResource(selectImgStar(list[0].tier.toString()))
                containerView.imgMatchChampion1Stars.setImageResource(selectImgStar(list[1].tier.toString()))
                containerView.imgMatchChampion2Stars.setImageResource(selectImgStar(list[2].tier.toString()))
                containerView.imgMatchChampion3Stars.setImageResource(selectImgStar(list[3].tier.toString()))
                containerView.imgMatchChampion4Stars.setImageResource(selectImgStar(list[4].tier.toString()))
                containerView.imgMatchChampion5Stars.setImageResource(selectImgStar(list[5].tier.toString()))
                containerView.imgMatchChampion6Stars.setImageResource(selectImgStar(list[6].tier.toString()))
                containerView.imgMatchChampion7Stars.setImageResource(selectImgStar(list[7].tier.toString()))
                containerView.imgMatchChampion8Stars.setImageResource(selectImgStar(list[8].tier.toString()))
                containerView.imgMatchChampion9Stars.setImageResource(selectImgStar(list[9].tier.toString()))
            }
            else if(size==9){
                containerView.imgMatchChampion0Stars.setImageResource(selectImgStar(list[0].tier.toString()))
                containerView.imgMatchChampion1Stars.setImageResource(selectImgStar(list[1].tier.toString()))
                containerView.imgMatchChampion2Stars.setImageResource(selectImgStar(list[2].tier.toString()))
                containerView.imgMatchChampion3Stars.setImageResource(selectImgStar(list[3].tier.toString()))
                containerView.imgMatchChampion4Stars.setImageResource(selectImgStar(list[4].tier.toString()))
                containerView.imgMatchChampion5Stars.setImageResource(selectImgStar(list[5].tier.toString()))
                containerView.imgMatchChampion6Stars.setImageResource(selectImgStar(list[6].tier.toString()))
                containerView.imgMatchChampion7Stars.setImageResource(selectImgStar(list[7].tier.toString()))
                containerView.imgMatchChampion8Stars.setImageResource(selectImgStar(list[8].tier.toString()))
                containerView.imgMatchChampion9Stars.visibility=View.INVISIBLE
            }
            else if(size==8){
                containerView.imgMatchChampion0Stars.setImageResource(selectImgStar(list[0].tier.toString()))
                containerView.imgMatchChampion1Stars.setImageResource(selectImgStar(list[1].tier.toString()))
                containerView.imgMatchChampion2Stars.setImageResource(selectImgStar(list[2].tier.toString()))
                containerView.imgMatchChampion3Stars.setImageResource(selectImgStar(list[3].tier.toString()))
                containerView.imgMatchChampion4Stars.setImageResource(selectImgStar(list[4].tier.toString()))
                containerView.imgMatchChampion5Stars.setImageResource(selectImgStar(list[5].tier.toString()))
                containerView.imgMatchChampion6Stars.setImageResource(selectImgStar(list[6].tier.toString()))
                containerView.imgMatchChampion7Stars.setImageResource(selectImgStar(list[7].tier.toString()))
                containerView.imgMatchChampion8Stars.visibility=View.INVISIBLE
                containerView.imgMatchChampion9Stars.visibility=View.INVISIBLE
            }
            else if(size==7){
                containerView.imgMatchChampion0Stars.setImageResource(selectImgStar(list[0].tier.toString()))
                containerView.imgMatchChampion1Stars.setImageResource(selectImgStar(list[1].tier.toString()))
                containerView.imgMatchChampion2Stars.setImageResource(selectImgStar(list[2].tier.toString()))
                containerView.imgMatchChampion3Stars.setImageResource(selectImgStar(list[3].tier.toString()))
                containerView.imgMatchChampion4Stars.setImageResource(selectImgStar(list[4].tier.toString()))
                containerView.imgMatchChampion5Stars.setImageResource(selectImgStar(list[5].tier.toString()))
                containerView.imgMatchChampion6Stars.setImageResource(selectImgStar(list[6].tier.toString()))
                containerView.imgMatchChampion7Stars.visibility=View.INVISIBLE
                containerView.imgMatchChampion8Stars.visibility=View.INVISIBLE
                containerView.imgMatchChampion9Stars.visibility=View.INVISIBLE
            }
            else if(size==6){
                containerView.imgMatchChampion0Stars.setImageResource(selectImgStar(list[0].tier.toString()))
                containerView.imgMatchChampion1Stars.setImageResource(selectImgStar(list[1].tier.toString()))
                containerView.imgMatchChampion2Stars.setImageResource(selectImgStar(list[2].tier.toString()))
                containerView.imgMatchChampion3Stars.setImageResource(selectImgStar(list[3].tier.toString()))
                containerView.imgMatchChampion4Stars.setImageResource(selectImgStar(list[4].tier.toString()))
                containerView.imgMatchChampion5Stars.setImageResource(selectImgStar(list[5].tier.toString()))
                containerView.imgMatchChampion6Stars.visibility=View.INVISIBLE
                containerView.imgMatchChampion7Stars.visibility=View.INVISIBLE
                containerView.imgMatchChampion8Stars.visibility=View.INVISIBLE
                containerView.imgMatchChampion9Stars.visibility=View.INVISIBLE
            }
            else if(size==5){
                containerView.imgMatchChampion0Stars.setImageResource(selectImgStar(list[0].tier.toString()))
                containerView.imgMatchChampion1Stars.setImageResource(selectImgStar(list[1].tier.toString()))
                containerView.imgMatchChampion2Stars.setImageResource(selectImgStar(list[2].tier.toString()))
                containerView.imgMatchChampion3Stars.setImageResource(selectImgStar(list[3].tier.toString()))
                containerView.imgMatchChampion4Stars.setImageResource(selectImgStar(list[4].tier.toString()))
                containerView.imgMatchChampion5Stars.visibility=View.INVISIBLE
                containerView.imgMatchChampion6Stars.visibility=View.INVISIBLE
                containerView.imgMatchChampion7Stars.visibility=View.INVISIBLE
                containerView.imgMatchChampion8Stars.visibility=View.INVISIBLE
                containerView.imgMatchChampion9Stars.visibility=View.INVISIBLE
            }
            else if(size==4){
                containerView.imgMatchChampion0Stars.setImageResource(selectImgStar(list[0].tier.toString()))
                containerView.imgMatchChampion1Stars.setImageResource(selectImgStar(list[1].tier.toString()))
                containerView.imgMatchChampion2Stars.setImageResource(selectImgStar(list[2].tier.toString()))
                containerView.imgMatchChampion3Stars.setImageResource(selectImgStar(list[3].tier.toString()))
                containerView.imgMatchChampion4Stars.visibility=View.INVISIBLE
                containerView.imgMatchChampion5Stars.visibility=View.INVISIBLE
                containerView.imgMatchChampion6Stars.visibility=View.INVISIBLE
                containerView.imgMatchChampion7Stars.visibility=View.INVISIBLE
                containerView.imgMatchChampion8Stars.visibility=View.INVISIBLE
                containerView.imgMatchChampion9Stars.visibility=View.INVISIBLE
            }
            else if(size<=3){
                containerView.imgMatchChampion0Stars.setImageResource(selectImgStar(list[0].tier.toString()))
                containerView.imgMatchChampion1Stars.setImageResource(selectImgStar(list[1].tier.toString()))
                containerView.imgMatchChampion2Stars.setImageResource(selectImgStar(list[2].tier.toString()))
                containerView.imgMatchChampion3Stars.visibility=View.INVISIBLE
                containerView.imgMatchChampion4Stars.visibility=View.INVISIBLE
                containerView.imgMatchChampion5Stars.visibility=View.INVISIBLE
                containerView.imgMatchChampion6Stars.visibility=View.INVISIBLE
                containerView.imgMatchChampion7Stars.visibility=View.INVISIBLE
                containerView.imgMatchChampion8Stars.visibility=View.INVISIBLE
                containerView.imgMatchChampion9Stars.visibility=View.INVISIBLE
            }
        }

        private fun loadPlayerUnits(list: List<TFTUnit>, size: Int) {
            if(size>=10){
                containerView.imgMatchChampion0.setImageResource(selectImg(list[0].characterId))
                containerView.imgMatchChampion1.setImageResource(selectImg(list[1].characterId))
                containerView.imgMatchChampion2.setImageResource(selectImg(list[2].characterId))
                containerView.imgMatchChampion3.setImageResource(selectImg(list[3].characterId))
                containerView.imgMatchChampion4.setImageResource(selectImg(list[4].characterId))
                containerView.imgMatchChampion5.setImageResource(selectImg(list[5].characterId))
                containerView.imgMatchChampion6.setImageResource(selectImg(list[6].characterId))
                containerView.imgMatchChampion7.setImageResource(selectImg(list[7].characterId))
                containerView.imgMatchChampion8.setImageResource(selectImg(list[8].characterId))
                containerView.imgMatchChampion9.setImageResource(selectImg(list[9].characterId))
            }
            else if(size==9){
                containerView.imgMatchChampion0.setImageResource(selectImg(list[0].characterId))
                containerView.imgMatchChampion1.setImageResource(selectImg(list[1].characterId))
                containerView.imgMatchChampion2.setImageResource(selectImg(list[2].characterId))
                containerView.imgMatchChampion3.setImageResource(selectImg(list[3].characterId))
                containerView.imgMatchChampion4.setImageResource(selectImg(list[4].characterId))
                containerView.imgMatchChampion5.setImageResource(selectImg(list[5].characterId))
                containerView.imgMatchChampion6.setImageResource(selectImg(list[6].characterId))
                containerView.imgMatchChampion7.setImageResource(selectImg(list[7].characterId))
                containerView.imgMatchChampion8.setImageResource(selectImg(list[8].characterId))
                containerView.imgMatchChampion9.visibility=View.INVISIBLE
            }
            else if(size==8){
                containerView.imgMatchChampion0.setImageResource(selectImg(list[0].characterId))
                containerView.imgMatchChampion1.setImageResource(selectImg(list[1].characterId))
                containerView.imgMatchChampion2.setImageResource(selectImg(list[2].characterId))
                containerView.imgMatchChampion3.setImageResource(selectImg(list[3].characterId))
                containerView.imgMatchChampion4.setImageResource(selectImg(list[4].characterId))
                containerView.imgMatchChampion5.setImageResource(selectImg(list[5].characterId))
                containerView.imgMatchChampion6.setImageResource(selectImg(list[6].characterId))
                containerView.imgMatchChampion7.setImageResource(selectImg(list[7].characterId))
                containerView.imgMatchChampion8.visibility=View.INVISIBLE
                containerView.imgMatchChampion9.visibility=View.INVISIBLE
            }
            else if(size==7){
                containerView.imgMatchChampion0.setImageResource(selectImg(list[0].characterId))
                containerView.imgMatchChampion1.setImageResource(selectImg(list[1].characterId))
                containerView.imgMatchChampion2.setImageResource(selectImg(list[2].characterId))
                containerView.imgMatchChampion3.setImageResource(selectImg(list[3].characterId))
                containerView.imgMatchChampion4.setImageResource(selectImg(list[4].characterId))
                containerView.imgMatchChampion5.setImageResource(selectImg(list[5].characterId))
                containerView.imgMatchChampion6.setImageResource(selectImg(list[6].characterId))
                containerView.imgMatchChampion7.visibility=View.INVISIBLE
                containerView.imgMatchChampion8.visibility=View.INVISIBLE
                containerView.imgMatchChampion9.visibility=View.INVISIBLE
            }
            else if(size==6){
                containerView.imgMatchChampion0.setImageResource(selectImg(list[0].characterId))
                containerView.imgMatchChampion1.setImageResource(selectImg(list[1].characterId))
                containerView.imgMatchChampion2.setImageResource(selectImg(list[2].characterId))
                containerView.imgMatchChampion3.setImageResource(selectImg(list[3].characterId))
                containerView.imgMatchChampion4.setImageResource(selectImg(list[4].characterId))
                containerView.imgMatchChampion5.setImageResource(selectImg(list[5].characterId))
                containerView.imgMatchChampion6.visibility=View.INVISIBLE
                containerView.imgMatchChampion7.visibility=View.INVISIBLE
                containerView.imgMatchChampion8.visibility=View.INVISIBLE
                containerView.imgMatchChampion9.visibility=View.INVISIBLE
            }
            else if(size==5){
                containerView.imgMatchChampion0.setImageResource(selectImg(list[0].characterId))
                containerView.imgMatchChampion1.setImageResource(selectImg(list[1].characterId))
                containerView.imgMatchChampion2.setImageResource(selectImg(list[2].characterId))
                containerView.imgMatchChampion3.setImageResource(selectImg(list[3].characterId))
                containerView.imgMatchChampion4.setImageResource(selectImg(list[4].characterId))
                containerView.imgMatchChampion5.visibility=View.INVISIBLE
                containerView.imgMatchChampion6.visibility=View.INVISIBLE
                containerView.imgMatchChampion7.visibility=View.INVISIBLE
                containerView.imgMatchChampion8.visibility=View.INVISIBLE
                containerView.imgMatchChampion9.visibility=View.INVISIBLE
            }
            else if(size==4){
                containerView.imgMatchChampion0.setImageResource(selectImg(list[0].characterId))
                containerView.imgMatchChampion1.setImageResource(selectImg(list[1].characterId))
                containerView.imgMatchChampion2.setImageResource(selectImg(list[2].characterId))
                containerView.imgMatchChampion3.setImageResource(selectImg(list[3].characterId))
                containerView.imgMatchChampion4.visibility=View.INVISIBLE
                containerView.imgMatchChampion5.visibility=View.INVISIBLE
                containerView.imgMatchChampion6.visibility=View.INVISIBLE
                containerView.imgMatchChampion7.visibility=View.INVISIBLE
                containerView.imgMatchChampion8.visibility=View.INVISIBLE
                containerView.imgMatchChampion9.visibility=View.INVISIBLE
            }
            else if(size<=3){
                containerView.imgMatchChampion0.setImageResource(selectImg(list[0].characterId))
                containerView.imgMatchChampion1.setImageResource(selectImg(list[1].characterId))
                containerView.imgMatchChampion2.setImageResource(selectImg(list[2].characterId))
                containerView.imgMatchChampion3.visibility=View.INVISIBLE
                containerView.imgMatchChampion4.visibility=View.INVISIBLE
                containerView.imgMatchChampion5.visibility=View.INVISIBLE
                containerView.imgMatchChampion6.visibility=View.INVISIBLE
                containerView.imgMatchChampion7.visibility=View.INVISIBLE
                containerView.imgMatchChampion8.visibility=View.INVISIBLE
                containerView.imgMatchChampion9.visibility=View.INVISIBLE
            }


        }

        fun selectImg(name: String): Int {
            val resId: Int = containerView.getResources()
                .getIdentifier(name.toLowerCase().replace(" ","").replace("'","").replace("tft3_","").replace("set3_",""), "drawable", containerView.context.getPackageName())
            return resId
        }

        fun selectImgStar(name: String): Int {
            val resId: Int = containerView.getResources()
                .getIdentifier("stars"+name, "drawable", containerView.context.getPackageName())
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