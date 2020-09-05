package com.example.android.escode.destinations

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.ActivityNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.example.android.escode.game.GameDialogFragment

@Navigator.Name("gameDialog")
class GameDialogNavigator( private val manager: FragmentManager
) : Navigator<GameDialogNavigator.Destination>() {

    override fun navigate(destination: Destination, args: Bundle?, navOptions: NavOptions?, navigatorExtras: Extras?): NavDestination? {
        TODO("Not yet implemented")
        val dialogFragment = GameDialogFragment()
        dialogFragment.arguments = args
        dialogFragment.show(manager, "game dialog")
    }

    override fun createDestination(): Destination {
        return Destination(this)
    }

    override fun popBackStack(): Boolean {
        return false
    }

    class Destination(gameDialogNavigator: GameDialogNavigator) :
            NavDestination(gameDialogNavigator)


}