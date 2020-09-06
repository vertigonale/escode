package com.example.android.escode.game

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import com.example.android.escode.R
import com.example.android.escode.databinding.FragmentGameEndDialogBinding


class Game_EndDialogFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameEndDialogBinding>(
                inflater, R.layout.fragment_game_end_dialog, container, false)

        // Variables
        // Texts...
        val popTitle: String = getString(R.string.popTitle_level)
        val popText: String = getString(R.string.popText_level, 2,3)
        // Buttons...
        val btn_home: ImageButton = binding.popBtn1
        val btn_score: Button = binding.popBtn2
        val btn_again: ImageButton = binding.popBtn3



        btn_score.setOnClickListener(View.OnClickListener {
            view?.findNavController()?.navigate(Game_EndDialogFragmentDirections.actionGameEndDialogFragmentToScoreboardFragment())
        })

        btn_again.setOnClickListener(View.OnClickListener {
            view?.findNavController()?.navigate(Game_EndDialogFragmentDirections.actionGameEndDialogFragmentToGameFragment())
        })

        btn_home.setOnClickListener(View.OnClickListener {
            view?.findNavController()?.navigate(Game_EndDialogFragmentDirections.actionGameEndDialogFragmentToTitleFragment())
        })

        return binding.root
    }


    private fun closefragment() {
        val manager: FragmentManager? = fragmentManager
        manager!!.popBackStack();
    }
}