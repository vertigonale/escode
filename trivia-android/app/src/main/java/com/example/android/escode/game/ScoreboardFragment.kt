package com.example.android.escode.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android.escode.R
import com.example.android.escode.databinding.FragmentScoreboardBinding

class ScoreboardFragment : Fragment() {

    var whichLevel: Int? = 0
    var whichLevelHeartCount: Int? = 0
    var whichTotalHeartCount: Int? = 0

    lateinit var whichLevelString: String
    lateinit var whichLevelHeartCountString: String
    lateinit var whichTotalHeartCountString: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentScoreboardBinding>(
                inflater, R.layout.fragment_scoreboard, container, false)

        whichLevel = arguments?.getInt("indexLevelPass")
        whichLevelHeartCount = arguments?.getInt("levelHeartPass")
        whichTotalHeartCount = arguments?.getInt("totalHeartPass")

        whichLevelString = "stringy string"/*whichLevel.toString()*/
        whichLevelHeartCountString = whichLevelHeartCount.toString()
        whichTotalHeartCountString = whichTotalHeartCount.toString()

        return binding.root
    }
}