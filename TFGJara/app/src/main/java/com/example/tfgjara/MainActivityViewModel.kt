package com.example.tfgjara

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tfgjara.data.Champion
import com.example.tfgjara.data.Item
import com.example.tfgjara.data.Skill
import com.example.tfgjara.data.Trait
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.match_history_fragment_item.view.*
import no.stelar7.api.r4j.basic.APICredentials
import no.stelar7.api.r4j.basic.constants.api.Platform
import no.stelar7.api.r4j.basic.constants.api.ServicePlatform
import no.stelar7.api.r4j.impl.R4J
import no.stelar7.api.r4j.impl.tft.TFTSummonerAPI
import no.stelar7.api.r4j.pojo.lol.league.LeagueEntry
import no.stelar7.api.r4j.pojo.lol.spectator.SpectatorGameInfo
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner
import no.stelar7.api.r4j.pojo.shared.GAMHSMatch
import no.stelar7.api.r4j.pojo.tft.TFTMatch
import no.stelar7.api.r4j.pojo.tft.TFTParticipant
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.lang.NullPointerException
import java.lang.reflect.Type


class MainActivityViewModel : ViewModel() {

    //private lateinit var  repository: LocalRepository

    private val apikey:String="RGAPI-bf58fe4c-1f78-4d76-a756-e318ff8a5b71"
    private val r4J = R4J(APICredentials(apikey))

    private val gson: Gson =Gson()
    private lateinit var  actualChampion: Champion

    var actualCompTraits:MutableMap<String,Int> = mutableMapOf()

    private var actualGame:List<TFTParticipant> = listOf()

    var actualGameDuration:Int = 0

    var actualGameDate:String = ""

    var actualGameType:String = ""

    private var actualCompChampions:MutableList<Champion?> = mutableListOf(null,null,null,null,null,null,null,null,null,null)

    private val _actualItem: MutableLiveData<Item> =  MutableLiveData()
    val actualItem: LiveData<Item>
        get() = _actualItem

    private val _actualItem2: MutableLiveData<Int> =  MutableLiveData(1)
    val actualItem2: LiveData<Int>
        get() = _actualItem2

    private val _actualSumonner: MutableLiveData<Summoner?> =  MutableLiveData(null)
    val actualSumonner: LiveData<Summoner?>
        get() = _actualSumonner

    private val _games: MutableLiveData<MutableList<String>> =  MutableLiveData()
    val games: LiveData<MutableList<String>>
        get() = _games

    private val _names: MutableLiveData<MutableMap<String,String>> =  MutableLiveData()
    val names: LiveData<MutableMap<String,String>>
        get() = _names

    private var gamesData:MutableList<TFTMatch> = mutableListOf()


    private val _actualSumonnerData: MutableLiveData<MutableList<LeagueEntry?>> =  MutableLiveData(mutableListOf())
    val actualSumonnerData: LiveData<MutableList<LeagueEntry?>>
        get() = _actualSumonnerData

    private val _summonerNotFound: MutableLiveData<String> =  MutableLiveData("")
    val summonerNotFound: LiveData<String>
        get() = _summonerNotFound


    private var champions:MutableList<Champion> = mutableListOf()
    private var traits:MutableList<Trait> = mutableListOf()
    private var skills:MutableList<Skill> = mutableListOf()
    private var items:MutableList<Item> = mutableListOf()

    private var sumonner:String=""

    private val _actualSort: MutableLiveData<Int> =  MutableLiveData(1)
    val actualSort: LiveData<Int>
        get() = _actualSort


    fun loadChampionJson(data:InputStream){
        val collectionType: Type = object : TypeToken<List<Champion>>() {}.type
        val reader:Reader=InputStreamReader(data)
        champions=gson.fromJson(reader,collectionType)
    }

    fun loadSkillsJson(data:InputStream){
        val collectionType: Type = object : TypeToken<List<Skill>>() {}.type
        val reader:Reader=InputStreamReader(data)
        skills=gson.fromJson(reader,collectionType)
    }

    fun loadTraitJson(data:InputStream){
        val collectionType: Type = object : TypeToken<List<Trait>>() {}.type
        val reader:Reader=InputStreamReader(data)
        traits=gson.fromJson(reader,collectionType)
    }

    fun loadItemJson(data:InputStream){
        val collectionType: Type = object : TypeToken<List<Item>>() {}.type
        val reader:Reader=InputStreamReader(data)
        items=gson.fromJson(reader,collectionType)
        _actualItem.value=items[0]
        _actualItem2.value=1
    }

    fun getTraits():List<Trait>{
        return traits
    }

    fun getChampions():List<Champion>{
        return champions
    }

    fun setSort(filter:Int){
        _actualSort.value=filter
    }

    fun getActualChampion():Champion{
        return actualChampion
    }


    fun getChampionSkill(id:String):Skill{
        return skills.find {
            it.championId==id
        }?:skills[1]
    }

    fun getChampionTrait(trait:String):Trait{
        return traits.find {
            it.name.equals(trait)
        }?:traits[1]
    }

    fun getItem(id:Int):Item{
        return items.find {
            it.id==id
        }?:items[0]
    }

    fun getGamesData():List<TFTMatch>{
        return gamesData
    }

    fun getItems():List<Item>{
        return items
    }

    fun selectChampion(it: Champion) {
        actualChampion=it
    }

    fun selectItem(it:Item){
        _actualItem.value=it
    }

    fun selectItem2(it:Int){
        _actualItem2.value=it
    }

    fun resetItem() {
        _actualItem.value=items[0]
    }

    fun setSumonner(it:String){
        sumonner=it
    }

    fun getSumonner():String{
        return sumonner
    }

    fun getApiSummoner(){
        _summonerNotFound.postValue("")

        Thread{ _actualSumonner.postValue(r4J.getTFTAPI().getSummonerAPI().getSummonerByName(Platform.EUW1,sumonner))
               if(_actualSumonner.value==null){
                    _summonerNotFound.postValue("Summoner not found")
               }
        }.start()

    }

    fun getLeague(){

        Thread{
            _actualSumonnerData.postValue( r4J.getTFTAPI().getLeagueAPI()
                .getLeagueEntries(Platform.EUW1, Summoner.byName(Platform.EUW1, sumonner).summonerId))

        }.start()
    }

    fun getGames() {
        Thread{
            _games.postValue( r4J.getTFTAPI().getMatchAPI().getMatchList(ServicePlatform.EUROPE, Summoner.byName(Platform.EUW1, sumonner).getPUUID(), 10))

        }.start()

    }

    fun loadGames() {
        Thread{
            val list:MutableList<TFTMatch> = mutableListOf()
            _games.value?.forEach {
                list.add((r4J.getTFTAPI().getMatchAPI().getMatch(ServicePlatform.EUROPE, it)))
            }
            gamesData=list

        }.start()
    }

    fun loadPlayerNames(tftParticipant: List<TFTParticipant>){
        Thread{
            val list:MutableMap<String,String> = mutableMapOf()
            tftParticipant.forEach {
                list[it.puuid]=r4J.tftapi.summonerAPI.getSummonerByPUUID(Platform.EUW1,it.puuid).name
            }
            _names.postValue(list)
        }.start()

    }

    fun resetSummoner() {
        _actualSumonner.value= null
        _actualSumonnerData.value=mutableListOf()
        _games.value=mutableListOf()
        gamesData=mutableListOf()
        _summonerNotFound.value=""
        sumonner=""
    }

    fun getActualCompChampions():MutableList<Champion?>{
        return actualCompChampions
    }

    fun addActualCompChampions(champion:Champion){
        if(!actualCompChampions.contains(champion)){
            when {
                actualCompChampions[0]==null -> { actualCompChampions[0]=champion }
                actualCompChampions[1]==null -> { actualCompChampions[1]=champion }
                actualCompChampions[2]==null -> { actualCompChampions[2]=champion }
                actualCompChampions[3]==null -> { actualCompChampions[3]=champion }
                actualCompChampions[4]==null -> { actualCompChampions[4]=champion }
                actualCompChampions[5]==null -> { actualCompChampions[5]=champion }
                actualCompChampions[6]==null -> { actualCompChampions[6]=champion }
                actualCompChampions[7]==null -> { actualCompChampions[7]=champion }
                actualCompChampions[8]==null -> { actualCompChampions[8]=champion }
                actualCompChampions[9]==null -> { actualCompChampions[9]=champion }
            }
        }
    }

    fun addActualCompTraits(trait:String){
        if(actualCompTraits.contains(trait)){
            if(actualCompTraits[trait]==null){
                actualCompTraits[trait]=1
            }
            else{
                actualCompTraits[trait]=actualCompTraits[trait]!!+1
            }
        }
        else{
            actualCompTraits[trait] = 1
        }
    }

    fun resetActualCompTraits(){
        actualCompTraits= mutableMapOf()
    }

    fun setGame(it: TFTMatch) {
        actualGame=it.participants
        actualGameDuration=it.duration
        actualGameDate=(it.matchCreationAsDate.toString().substring(8,10)+it.matchCreationAsDate.toString().substring(4,8)+it.matchCreationAsDate.toString().substring(0,4)).replace("-","/")
        if(it.queue.name.contains("RANKED")){
            actualGameType="Ranked Game"
        }
        else{
            actualGameType="Normal Game"
        }

    }

    fun getGame():List<TFTParticipant>{
        return actualGame
    }


}


