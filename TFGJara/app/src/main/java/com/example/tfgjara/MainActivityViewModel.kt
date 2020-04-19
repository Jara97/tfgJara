package com.example.tfgjara

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tfgjara.data.Champion
import com.example.tfgjara.data.Item
import com.example.tfgjara.data.Skill
import com.example.tfgjara.data.Trait
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

    private val apikey:String="RGAPI-8e0639cd-7757-4f01-816d-62f3db696f05"
    private val r4J = R4J(APICredentials(apikey))

    private val gson: Gson =Gson()
    private lateinit var  actualChampion: Champion


    private val _actualItem: MutableLiveData<Item> =  MutableLiveData()
    val actualItem: LiveData<Item>
        get() = _actualItem

    private val _actualItem2: MutableLiveData<Int> =  MutableLiveData()
    val actualItem2: LiveData<Int>
        get() = _actualItem2

    private val _actualSumonner: MutableLiveData<Summoner?> =  MutableLiveData(null)
    val actualSumonner: LiveData<Summoner?>
        get() = _actualSumonner

    private val _games: MutableLiveData<MutableList<String>> =  MutableLiveData()
    val games: LiveData<MutableList<String>>
        get() = _games

    private val _gamesData: MutableLiveData<MutableList<TFTMatch>> =  MutableLiveData(mutableListOf())
    val gamesData: LiveData<MutableList<TFTMatch>>
        get() = _gamesData

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
            if(!_summonerNotFound.value.equals("Summoner not found")){
                _games.postValue( r4J.getTFTAPI().getMatchAPI().getMatchList(ServicePlatform.EUROPE, Summoner.byName(Platform.EUW1, sumonner).getPUUID(), 5))
            }

        }.start()

    }

    fun loadGames() {
        Thread{
            if(!_summonerNotFound.value.equals("Summoner not found")){
                val list:MutableList<TFTMatch> = mutableListOf()
                _games.value?.forEach {
                    list.add((r4J.getTFTAPI().getMatchAPI().getMatch(ServicePlatform.EUROPE, it)))
                }
                _gamesData.postValue(list)
            }

        }.start()
    }

    fun resetSummoner() {
        _actualSumonner.value= null
        _actualSumonnerData.value=mutableListOf()
        _games.value=mutableListOf()
        _gamesData.value=mutableListOf()
        _summonerNotFound.value=""
        sumonner=""
    }


}


