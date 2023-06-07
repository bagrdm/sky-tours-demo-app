package com.dm.sky_tours_demo_app.ui

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dm.sky_tours_demo_app.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBottomNav()
    }

    private fun setupBottomNav() {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        MaterialAlertDialogBuilder(this)
            .setIcon(R.drawable.icon_app)
            .setTitle(R.string.exit)
            .setMessage(R.string.question_exit)
            .setPositiveButton(
                R.string.possitive,
                DialogInterface.OnClickListener { dialog, which -> finish() })
            .setNegativeButton(R.string.negative, null)
            .show()
    } //Выход
}
