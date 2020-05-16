package com.example.tfgjara

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.DialogFragment
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tfgjara.fragments.main.ItemsFragment
import com.example.tfgjara.fragments.main.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.navheader.view.*
import java.io.InputStream

class MainActivity : AppCompatActivity(){

    private val viewModel: MainActivityViewModel by viewModels()
    private val navController: NavController by lazy {
        findNavController(R.id.navHostFragment)
    }

    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setBottonNavigationView()
        setupNavigationView()
        setupAppBar()

        if(savedInstanceState==null){
            viewModel.loadChampionJson(resources.openRawResource(R.raw.champions))
            viewModel.loadSkillsJson(resources.openRawResource(R.raw.skills))
            viewModel.loadTraitJson((resources.openRawResource(R.raw.traits)))
            viewModel.loadItemJson((resources.openRawResource(R.raw.items)))
        }

    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.main_open_navigation_drawer, R.string.main_close_navigation_drawer).apply {
                drawerLayout.addDrawerListener(this)
                syncState()
                getDrawerArrowDrawable().setColor(Color.WHITE)
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else if(R.id.matchHistoryNavigation==navController.currentDestination?.id){
            navController.navigate(R.id.profileNavigation)
        }
        else if(R.id.gameNavigation==navController.currentDestination?.id){
            navController.navigate(R.id.matchHistoryNavigation)
        }
        else if(R.id.mainNavigation!=navController.currentDestination?.id){
            setupAppBar()
            navController.navigate(R.id.mainNavigation)
        }
        else if(R.id.mainNavigation==navController.currentDestination?.id){
            AlertDialog.Builder(this)
                .setMessage("Do you want to close the application?")
                .setCancelable(true)
                .setPositiveButton("Close") { _, _ -> finish() }
                .setNegativeButton("Cancel") { _, _ -> }
                .create().show()
        }
        else{
            super.onBackPressed()
        }
    }


    private fun setupNavigationView() {
        navigationView.setNavigationItemSelectedListener { navigateToOption(it)}
    }

    private fun navigateToOption(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mnuOption1 -> {
                if(navigationView.txtUserName.text.toString().isNotBlank()){
                    viewModel.resetSummoner()
                    viewModel.setSumonner(navigationView.txtUserName.text.toString())
                    navController.navigate(R.id.profileNavigation)
                    setLoadingDialog()
                }
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setLoadingDialog() {

        val dialog:AlertDialog=AlertDialog.Builder(this)
            .setView(R.layout.loading_dialog)
            .setCancelable(false)
            .create()

        dialog.show()
        Thread{
            Thread.sleep(4000)
            dialog.dismiss()
        }.start()
    }


    private fun setBottonNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.mnu1 -> navController.navigate(R.id.mainNavigation)
                R.id.mnu2 -> { navController.navigate(R.id.itemsNavigation) ; setupAppBar() }
                R.id.mnu3 -> { navController.navigate(R.id.compBuilderNavigation); setupAppBar() }
                else -> true
            }
            true
        }
    }
}
