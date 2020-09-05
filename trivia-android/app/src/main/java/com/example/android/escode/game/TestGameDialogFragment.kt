package com.example.android.escode.game

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.android.escode.R
import com.example.android.escode.databinding.FragmentGameDialogBinding


class TestGameDialogFragment : DialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameDialogBinding>(
                inflater, R.layout.test_fragment_game_dialog, container, false)

    // Variables
        // Texts...
        val popTitle: String = getString(R.string.popTitle_level)
        val popText: String = getString(R.string.popText_level, 2,3)
        // Buttons...
        val btn_home: ImageButton = binding.popBtn1
        val btn_score: Button = binding.popBtn2
        val btn_next: ImageButton = binding.popBtn3



        btn_score.setOnClickListener(View.OnClickListener {
            val scoreScreen = ScoreboardFragment()

            scoreScreen
        })

        return binding.root
    }



    // gibt einen boolischen wert zurück welcher sagt ob der current Wert gleich dem letzten Wert der liste question ist
    // dh ein lvl ist zu ende
    // TODO einen count für den current Level index
    fun isLastQ(lvl: MutableList<GameFragment.Question>, current: Int) : Boolean{

        var isLast = false
        val lastQuestion = lvl.lastIndex

        when(current) {
            lastQuestion -> isLast = true
            else -> isLast = false
        }
        return isLast
    }

    // gibt einen boolischen wert zurück welcher sagt ob der current Wert gleich dem letzten Wert der liste level ist
    // dh ein spiel ist zu ende
    // TODO einen count für den current Level index
    fun isLastLvl(lvl: MutableList<GameFragment.Level>, current: Int) : Boolean{

        var isLast = false
        val lastQuestion = lvl.lastIndex

        when(current) {
            lastQuestion -> isLast = true
            else -> isLast = false
        }
        return isLast
    }
}