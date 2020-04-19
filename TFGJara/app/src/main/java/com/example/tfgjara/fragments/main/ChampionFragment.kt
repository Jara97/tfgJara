package com.example.tfgjara.fragments.main

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfgjara.MainActivityViewModel
import com.example.tfgjara.R
import com.example.tfgjara.data.Champion
import com.example.tfgjara.data.Skill
import kotlinx.android.synthetic.main.champion_fragment.*
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment_item.view.*

class ChampionFragment : Fragment(R.layout.champion_fragment) {

    private val viewModel: MainActivityViewModel by activityViewModels()


    companion object {
        fun newInstance() = ChampionFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {

        (requireActivity() as AppCompatActivity).supportActionBar?.run {
            setTitle(R.string.champion_title)
        }


        val s: Skill =viewModel.getChampionSkill(viewModel.getActualChampion().championId)
        val c:Champion=viewModel.getActualChampion()

        lblCNameD.text=c.name
        imgChampionD.setImageResource(selectImg(c.name))
        lblCSkillName.text=s.name
        lblCSkillDescription.text=s.description
        lblCTrait1.text=c.traits[0]
        lblCTrait2.text=c.traits[1]

        setSkillEfect(s)
        setTraitDescription(c.traits)
        setTraitEfect(c.traits)

        imgCItem1.setImageResource(selectImgItem((c.items[0].toString())))
        imgCItem2.setImageResource(selectImgItem((c.items[1].toString())))
        imgCItem3.setImageResource(selectImgItem((c.items[2].toString())))

    }

    private fun setTraitDescription(traits: List<String>) {
        lblCTrait1Desc.text=viewModel.getChampionTrait(traits[0]).description
        lblCTrait2Desc.text=viewModel.getChampionTrait(traits[1]).description
        if(traits.size==3){
            lblCTrait3.text=viewModel.getChampionTrait(traits[2]).name
            lblCTrait3Desc.text=viewModel.getChampionTrait(traits[2]).description
        }
        else{
            lblCTrait3.visibility=View.GONE
            lblCTrait3Desc.visibility=View.GONE
        }
    }

    private fun setSkillEfect(c: Skill){
        if(c.damage.equals("0")){
            lblCSkillDamage.visibility= View.GONE
        }
        else{
            lblCSkillDamage.text="Damage:  "+c.damage
        }
        if(c.shield.equals("0")){
            lblCSkillShield.visibility=View.GONE
        }
        else{
            lblCSkillShield.text="Shield:  "+c.damage
        }
        if(c.heal.equals("0")){
            lblCSkillHeal.visibility=View.GONE
        }
        else if(c.heal.trim()[0].isDigit()){
            lblCSkillHeal.text="Heal:  "+c.heal
        }
        else{
            lblCSkillHeal.text=c.heal
        }

    }

    private fun setTraitEfect(traits: List<String>) {
        var efect:String=""
        var efect2:String=""
        var efect3:String=""
        viewModel.getChampionTrait(traits[0]).sets.forEach {
            efect=efect+it.toString()+"\n\n"
        }
        viewModel.getChampionTrait(traits[1]).sets.forEach {
            efect2=efect2+it.toString()+"\n\n"
        }
        lblCTrait1Efect.text=efect.substring(0,efect.length-2)
        lblCTraitEfect2.text=efect2.substring(0,efect2.length-2)
        if(viewModel.getActualChampion().traits.size==3){
            viewModel.getChampionTrait(traits[2]).sets.forEach {
                efect3=efect3+it.toString()+"\n\n"
            }
            lblCTraitEfect3.text=efect3.substring(0,efect3.length-2)
        }
        else{
            lblCTraitEfect3.visibility=View.GONE
        }

    }


    fun selectImg(name: String): Int {
        val resId: Int = this.getResources()
            .getIdentifier(name.toLowerCase().replace(" ","").replace("'",""), "drawable", context!!.getPackageName())
        return resId

    }
    fun selectImgItem(name: String): Int {
        val resId: Int = this.getResources()
            .getIdentifier("a"+name.toLowerCase().replace(" ","").replace("'",""), "drawable", context!!.getPackageName())
        return resId

    }



}