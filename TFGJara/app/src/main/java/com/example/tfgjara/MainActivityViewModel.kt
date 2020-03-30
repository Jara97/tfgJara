package com.example.tfgjara

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tfgjara.data.Champion
import com.example.tfgjara.data.Skill
import com.example.tfgjara.data.Trait
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.lang.reflect.Type


class MainActivityViewModel : ViewModel() {

    //private lateinit var  repository: LocalRepository

    private val gson: Gson =Gson()
    private lateinit var  actualChampion: Champion

    private var champions:MutableList<Champion> = mutableListOf()
    private var traits:MutableList<Trait> = mutableListOf()
    private var skills:MutableList<Skill> = mutableListOf()

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

    fun selectChampion(it: Champion) {
        actualChampion=it
    }


}