package com.example.tfgjara

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_fragment.*
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    private val navController: NavController by lazy {
        findNavController(R.id.navHostFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setBottonNavigationView()
        viewModel.loadChampionJson(resources.openRawResource(R.raw.champions))
        viewModel.loadSkillsJson(resources.openRawResource(R.raw.skills))
        viewModel.loadTraitJson((resources.openRawResource(R.raw.traits)))
        setupAppBar()

    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
    }


    private fun setBottonNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.mnu1 -> navController.navigate(R.id.mainNavigation)
                R.id.mnu2 -> navController.navigate(R.id.itemsNavigation)
                else -> true
            }
            true
        }
    }
}
