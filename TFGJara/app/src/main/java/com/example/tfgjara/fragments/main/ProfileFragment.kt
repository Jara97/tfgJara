package com.example.tfgjara.fragments.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.tfgjara.MainActivityViewModel
import com.example.tfgjara.R
import kotlinx.android.synthetic.main.profile_fragment.*
import no.stelar7.api.r4j.pojo.lol.league.LeagueEntry
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner
import no.stelar7.api.r4j.pojo.tft.TFTParticipant
import kotlin.math.absoluteValue


class ProfileFragment : Fragment(R.layout.profile_fragment) {

    private val viewModel: MainActivityViewModel by activityViewModels()

    private val navController: NavController by lazy {
        findNavController()
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        setObservers()
        setListeners()
        if(savedInstanceState!=null){
            lblLoading.visibility= View.INVISIBLE
        }
    }

    private fun setListeners() {
        btnHistory.setOnClickListener {
            navController.navigate(R.id.MatchHistoryNavigation)
        }
    }

    private fun loadSumonner() {
        viewModel.getApiSummoner()
    }

    private fun setupViews() {
        (requireActivity() as AppCompatActivity).supportActionBar?.run {
            setTitle(R.string.profile)
        }
        setVisibility(false)
        setVisibility2(false)
        lblNoTFTGames.visibility=View.INVISIBLE

        if(viewModel.getSumonner().isNotBlank()){
            loadSumonner()
        }

    }

    private fun setObservers() {
        viewModel.actualSumonner.observe(this){
            if(it!=null){
                setViews(it)
                viewModel.getLeague()
                viewModel.getGames()
                setVisibility2(true)
            }
        }

        viewModel.actualSumonnerData.observe(this){
            if(it.isNotEmpty()){
                setViewsData(it as MutableList<LeagueEntry>)
                setVisibility(true)
            }
            else{
                lblNoTFTGames.visibility=View.VISIBLE
            }
        }

        viewModel.games.observe(this){
            if(!it.isEmpty()){
                viewModel.loadGames()
            }
        }
        viewModel.summonerNotFound.observe(this){
            if(it.isNotBlank()){
                lblLoading.text=it
            }
        }


    }


    private fun setViewsData(it: MutableList<LeagueEntry>) {
        lblProfileLeague.setText(it[0].tierDivisionType.tier+" "+it[0].tierDivisionType.division)
        lblProfileTotalGames.text=("Games "+(it[0]?.losses+it[0].wins).toString())
        lblProfileLeaguePoints.text=(it[0].leaguePoints.toString()+" "+"LP")
        lblProfileWin.text=("Wins "+it[0]?.wins.toString())
        lblProfileWinratio.text=((it[0].wins.div(it[0].losses.toDouble())*100).toInt().toString()+"%")
        progressProfile.max=it[0].losses+it[0].wins
        progressProfile.progress=it[0].wins
        imgProfileLeague.setImageResource(selectImgItem(it[0].tierDivisionType.tier.toString()))

    }

    private fun setViews(it:Summoner) {
        lblProfileName.setText(it.name)
        lblProfileLvl.text="LV "+it.summonerLevel.toString()
        imgProfileIcon.setImageResource(selectImgIcon(it.profileIconId.toString()))

    }

    private fun setVisibility(vis:Boolean){
        if(!vis){
            imgProfileLeague.visibility= View.INVISIBLE
            lblProfileWinratio.visibility= View.INVISIBLE
            lblProfileWin.visibility= View.INVISIBLE
            lblProfileLeaguePoints.visibility= View.INVISIBLE
            lblProfileLeague.visibility= View.INVISIBLE
            lblProfileTotalGames.visibility= View.INVISIBLE
            progressProfile.visibility= View.INVISIBLE
            btnHistory.visibility= View.INVISIBLE
        }
        else{
            imgProfileLeague.visibility= View.VISIBLE
            lblProfileWinratio.visibility= View.VISIBLE
            lblProfileWin.visibility= View.VISIBLE
            lblProfileLeaguePoints.visibility= View.VISIBLE
            lblProfileLeague.visibility= View.VISIBLE
            lblProfileTotalGames.visibility= View.VISIBLE
            progressProfile.visibility= View.VISIBLE
            btnHistory.visibility= View.VISIBLE
            lblLoading.visibility= View.INVISIBLE
            lblNoTFTGames.visibility=View.INVISIBLE
        }
    }

    private fun setVisibility2(vis:Boolean){
        if(!vis){
            imgProfileIcon.visibility= View.INVISIBLE
            lblProfileLvl.visibility= View.INVISIBLE
            lblProfileName.visibility= View.INVISIBLE
        }
        else{
            imgProfileIcon.visibility= View.VISIBLE
            lblProfileLvl.visibility= View.VISIBLE
            lblProfileName.visibility= View.VISIBLE
            lblLoading.visibility= View.INVISIBLE

        }
    }

   private fun selectImgItem(name: String): Int {
        val resId: Int = this.getResources()
            .getIdentifier(name.toLowerCase(), "drawable", context!!.getPackageName())
        return resId

    }

   private fun selectImgIcon(name: String): Int {
        val resId: Int = this.getResources()
            .getIdentifier("b"+name, "drawable", context!!.getPackageName())
        return resId

    }
}