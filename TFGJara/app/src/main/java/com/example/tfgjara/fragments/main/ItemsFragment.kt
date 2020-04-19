package com.example.tfgjara.fragments.main

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.example.tfgjara.MainActivityViewModel
import com.example.tfgjara.R
import com.example.tfgjara.data.Item
import kotlinx.android.synthetic.main.items_fragment.*

class ItemsFragment : Fragment(R.layout.items_fragment) {

    private val viewModel: MainActivityViewModel by activityViewModels()
    private var list:List<Item> = mutableListOf()

    companion object {
        fun newInstance() = ItemsFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        setListeners()
        setObservers()
        viewModel.resetItem()
        viewModel.selectItem2(1)
        list =viewModel.getItems().filter {
            it.id.toString().contains(1.toString())
        }
    }

    private fun setupViews() {
        (requireActivity() as AppCompatActivity).supportActionBar?.run {
            setTitle(R.string.items)
        }
    }

    private fun setObservers() {
        viewModel.actualItem.observe(this){
            desmarcItems()
            when (it.id) {
                1 -> {
                    imgItemAD.setBackgroundColor(resources.getColor(R.color.sortDescending))
                }
                2 -> {
                    imgItemAS.setBackgroundColor(resources.getColor(R.color.sortDescending))
                }
                3 -> {
                    imgItemAP.setBackgroundColor(resources.getColor(R.color.sortDescending))
                }
                4 -> {
                    imgItemMana.setBackgroundColor(resources.getColor(R.color.sortDescending))
                }
                5 -> {
                    ImgItemArmor.setBackgroundColor(resources.getColor(R.color.sortDescending))
                }
                6 -> {
                    imgItemMR.setBackgroundColor(resources.getColor(R.color.sortDescending))
                }
                7 -> {
                    imgItemHP.setBackgroundColor(resources.getColor(R.color.sortDescending))
                }
                8 -> {
                    imgItemS.setBackgroundColor(resources.getColor(R.color.sortDescending))
                }
                9 -> {
                    imgItemCrit.setBackgroundColor(resources.getColor(R.color.sortDescending))
                }
            }
        }

        viewModel.actualItem2.observe(this){
            desmarcItems2()
            when (it) {
                1 -> {
                    imgItem1.setBackgroundColor(resources.getColor(R.color.sortDescending))
                }
                2 -> {
                    imgItem2.setBackgroundColor(resources.getColor(R.color.sortDescending))
                }
                3 -> {
                    imgItem3.setBackgroundColor(resources.getColor(R.color.sortDescending))
                }
                4 -> {
                    imgItem4.setBackgroundColor(resources.getColor(R.color.sortDescending))
                }
                5 -> {
                    imgItem5.setBackgroundColor(resources.getColor(R.color.sortDescending))
                }
                6 -> {
                    imgItem6.setBackgroundColor(resources.getColor(R.color.sortDescending))
                }
                7 -> {
                    imgItem7.setBackgroundColor(resources.getColor(R.color.sortDescending))
                }
                8 -> {
                    imgItem8.setBackgroundColor(resources.getColor(R.color.sortDescending))
                }
                9 -> {
                    imgItem9.setBackgroundColor(resources.getColor(R.color.sortDescending))
                }
            }
        }

    }

    private fun desmarcItems() {
        imgItemAD.setBackgroundColor(resources.getColor(R.color.background))
        imgItemAS.setBackgroundColor(resources.getColor(R.color.background))
        imgItemAP.setBackgroundColor(resources.getColor(R.color.background))
        imgItemMana.setBackgroundColor(resources.getColor(R.color.background))
        ImgItemArmor.setBackgroundColor(resources.getColor(R.color.background))
        imgItemMR.setBackgroundColor(resources.getColor(R.color.background))
        imgItemHP.setBackgroundColor(resources.getColor(R.color.background))
        imgItemS.setBackgroundColor(resources.getColor(R.color.background))
        imgItemCrit.setBackgroundColor(resources.getColor(R.color.background))
    }

    private fun desmarcItems2(){
        imgItem1.setBackgroundColor(resources.getColor(R.color.background))
        imgItem2.setBackgroundColor(resources.getColor(R.color.background))
        imgItem3.setBackgroundColor(resources.getColor(R.color.background))
        imgItem4.setBackgroundColor(resources.getColor(R.color.background))
        imgItem5.setBackgroundColor(resources.getColor(R.color.background))
        imgItem6.setBackgroundColor(resources.getColor(R.color.background))
        imgItem7.setBackgroundColor(resources.getColor(R.color.background))
        imgItem8.setBackgroundColor(resources.getColor(R.color.background))
        imgItem9.setBackgroundColor(resources.getColor(R.color.background))

    }

    private fun setListeners() {
        imgItemAD.setOnClickListener {
            viewModel.selectItem(viewModel.getItem(1))
            changeRecipes(1)
            makeItemsInvisible(true)
        }
        imgItemAS.setOnClickListener {
            viewModel.selectItem(viewModel.getItem(2))
            changeRecipes(2)
            makeItemsInvisible(true)
        }
        imgItemAP.setOnClickListener {
            viewModel.selectItem(viewModel.getItem(3))
            changeRecipes(3)
            makeItemsInvisible(true)
        }
        imgItemMana.setOnClickListener {
            viewModel.selectItem(viewModel.getItem(4))
            changeRecipes(4)
            makeItemsInvisible(true)
        }
        ImgItemArmor.setOnClickListener {
            viewModel.selectItem(viewModel.getItem(5))
            changeRecipes(5)
            makeItemsInvisible(true)
        }
        imgItemMR.setOnClickListener {
            viewModel.selectItem(viewModel.getItem(6))
            changeRecipes(6)
            makeItemsInvisible(true)
        }
        imgItemHP.setOnClickListener {
            viewModel.selectItem(viewModel.getItem(7))
            changeRecipes(7)
            makeItemsInvisible(true)
        }
        imgItemS.setOnClickListener {
            viewModel.selectItem(viewModel.getItem(8))
            changeRecipes(8)
            makeItemsInvisible(true)
        }
        imgItemCrit.setOnClickListener {
            viewModel.selectItem(viewModel.getItem(9))
            changeRecipes(9)
            makeItemsInvisible(true)
        }
        imgItem1.setOnClickListener {
            if(!list.isEmpty()){
                viewModel.selectItem2(1)
                changeItemData(list[1])
                makeItemsInvisible(false)
            }
        }
        imgItem2.setOnClickListener {
            if(!list.isEmpty()){
                viewModel.selectItem2(2)
                changeItemData(list[2])
                makeItemsInvisible(false)
            }
        }
        imgItem3.setOnClickListener {
            if(!list.isEmpty()){
                viewModel.selectItem2(3)
                changeItemData(list[3])
                makeItemsInvisible(false)
            }
        }
        imgItem4.setOnClickListener {
            if(!list.isEmpty()){
                viewModel.selectItem2(4)
                changeItemData(list[4])
                makeItemsInvisible(false)
            }
        }
        imgItem5.setOnClickListener {
            if(!list.isEmpty()){
                viewModel.selectItem2(5)
                changeItemData(list[5])
                makeItemsInvisible(false)
            }
        }
        imgItem6.setOnClickListener {
            if(!list.isEmpty()){
                viewModel.selectItem2(6)
                changeItemData(list[6])
                makeItemsInvisible(false)
            }
        }
        imgItem7.setOnClickListener {
            if(!list.isEmpty()){
                viewModel.selectItem2(7)
                changeItemData(list[7])
                makeItemsInvisible(false)
            }
        }
        imgItem8.setOnClickListener {
            if(!list.isEmpty()){
                viewModel.selectItem2(8)
                changeItemData(list[8])
                makeItemsInvisible(false)
            }
        }
        imgItem9.setOnClickListener {
            if(!list.isEmpty()){
                viewModel.selectItem2(9)
                changeItemData(list[9])
                makeItemsInvisible(false)
            }
        }

    }

    private fun makeItemsInvisible(visibility:Boolean) {
        if(visibility){
            lblItemEfectD.visibility= View.INVISIBLE
            lblItemEfect.visibility=View.INVISIBLE
            lblItemStats.visibility= View.INVISIBLE
            lblItemRecipeStats.visibility= View.INVISIBLE
            imgItemRecipe1.visibility= View.INVISIBLE
            imgItemRecipe2.visibility= View.INVISIBLE
            imgItemRecipeResult.visibility= View.INVISIBLE
            imgItemPlus.visibility= View.INVISIBLE
            viewModel.selectItem2(-1)
        }
        else{
            lblItemEfectD.visibility= View.VISIBLE
            lblItemEfect.visibility=View.VISIBLE
            lblItemStats.visibility= View.VISIBLE
            lblItemRecipeStats.visibility= View.VISIBLE
            imgItemRecipe1.visibility= View.VISIBLE
            imgItemRecipe2.visibility= View.VISIBLE
            imgItemRecipeResult.visibility= View.VISIBLE
            imgItemPlus.visibility= View.VISIBLE
        }
    }


    private fun changeItemData(item: Item) {
        lblItemRecipeStats.text=calculateStats(item)
        lblItemEfectD.text=item.pasive
        imgItemRecipeResult.setImageResource(selectImgItem(item.id.toString()))
        imgItemRecipe1.setImageResource(selectImgItem(item.id.toString()[0].toString()))
        imgItemRecipe2.setImageResource(selectImgItem(item.id.toString()[1].toString()))
    }

    private fun calculateStats(item: Item): CharSequence? {
        if(item.id.toString().contains("8")){

            when {
                item.id.toString().substring(1)=="9" -> {
                    return "10% Crit and +10% Dodge chance"
                }
                item.id.toString().substring(0,1)=="8" -> {
                    return ""
                }
                else -> {
                    return viewModel.getItem((item.id.toString().substring(0,1)).toInt()).stats
                }
            }
        }
        else if(item.id.toString().contains("9")){
            val item1:Item=viewModel.getItem((item.id.toString().substring(0,1)).toInt())
            val item2:Item=viewModel.getItem((item.id.toString().substring(1)).toInt())
            if(item1.id<=3){
                item2.stats="20% Crit chance"
                return item1.stats+" +\n "+item2.stats
            }
            else if(item1.id==9){
                item1.stats=""
                item2.stats="20% Crit and +20% Dodge chance"
                return item2.stats
            }
            else if(item1.id==4||item1.id==8){
                item2.stats="10% Crit and +10% Dodge chance"
                return item1.stats+" +\n "+item2.stats
            }
            else if(item1.id in 5..7){
                item2.stats="20% Dodge chance"
                return item1.stats+" +\n "+item2.stats
            }
        }
        return viewModel.getItem((item.id.toString().substring(0,1)).toInt()).stats+" +\n "+viewModel.getItem((item.id.toString().substring(1)).toInt()).stats
    }

    private fun changeRecipes(id: Int) {
        list =viewModel.getItems().filter {
            it.id.toString().contains(id.toString())
        }
        imgItem1.setImageResource(selectImgItem(list[1].id.toString()))
        imgItem2.setImageResource(selectImgItem(list[2].id.toString()))
        imgItem3.setImageResource(selectImgItem(list[3].id.toString()))
        imgItem4.setImageResource(selectImgItem(list[4].id.toString()))
        imgItem5.setImageResource(selectImgItem(list[5].id.toString()))
        imgItem6.setImageResource(selectImgItem(list[6].id.toString()))
        imgItem7.setImageResource(selectImgItem(list[7].id.toString()))
        imgItem8.setImageResource(selectImgItem(list[8].id.toString()))
        imgItem9.setImageResource(selectImgItem(list[9].id.toString()))
    }

    private fun selectImgItem(name: String): Int {
        val resId: Int = this.getResources()
            .getIdentifier("a"+name, "drawable", context!!.getPackageName())
        return resId

    }


}
