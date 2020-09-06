/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.escode

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.android.escode.databinding.ActivityMainBinding
import com.example.android.escode.game.GameDialogFragment
import com.example.android.escode.game.GameFragment
import com.example.android.escode.game.ScoreboardFragment

class MainActivity : AppCompatActivity(), Communicator {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        drawerLayout = binding.drawerLayout

        val navController = this.findNavController(R.id.myNavHostFragment)

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        // prevent nav gesture if not on start destination
        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, bundle: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }

        NavigationUI.setupWithNavController(binding.navView, navController)

//        val gameFrag = GameFragment()
//        supportFragmentManager.beginTransaction().replace(R.id.drawerLayout, gameFrag).commit()

//        val gameDialogFrag = GameDialogFragment()
//        supportFragmentManager.beginTransaction().replace(R.id.drawerLayout, gameDialogFrag).commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
    val bundle = Bundle()
    override fun passLevelIndex(levelIndex: Int) {

        bundle.putInt("indexLevelPass", levelIndex)

//        val transaction = this.supportFragmentManager.beginTransaction()
        val scoreFrag = ScoreboardFragment()
        scoreFrag.arguments = bundle

//        transaction.replace(R.id.drawerLayout, scoreFrag)
//        transaction.addToBackStack(null)
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//        transaction.commit()
    }

    override fun passHeartLevelCount(heartLevel: Int) {
        val bundle = Bundle()
        bundle.putInt("levelHeartPass", heartLevel)

        val transaction = this.supportFragmentManager.beginTransaction()
        val scoreFrag = ScoreboardFragment()
        scoreFrag.arguments = bundle

//        transaction.replace(R.id.drawerLayout, scoreFrag)
//        transaction.addToBackStack(null)
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//        transaction.commit()
    }

    override fun passHeartTotalCount(heartTotal: Int) {
        val bundle = Bundle()
        bundle.putInt("totalHeartPass", heartTotal)

        val transaction = this.supportFragmentManager.beginTransaction()
        val scoreFrag = ScoreboardFragment()
        scoreFrag.arguments = bundle

//        transaction.replace(R.id.drawerLayout, scoreFrag)
//        transaction.addToBackStack(null)
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//        transaction.commit()
    }
}
