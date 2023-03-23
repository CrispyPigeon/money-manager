package com.ds.money_manager.feature.main.activity

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import com.ds.money_manager.R
import com.ds.money_manager.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    val viewModel by viewModels<MainActivityViewModel>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()

        viewModel.isAuthorized()
    }

    private fun initListeners() {
        viewModel.isAuthorized.observe(this) { isAuth ->
            val navHost =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)!!
            val navController = navHost.findNavController()
            val inflater = navController.navInflater
            val graph = inflater.inflate(R.navigation.nav_graph)

            if (isAuth) {
                graph.setStartDestination(R.id.mainFragment)
            } else {
                graph.setStartDestination(R.id.signInFragment)
            }
            navController.setGraph(graph, intent.extras)
        }
    }
}