package com.example.android.escode.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.android.escode.R
import com.example.android.escode.databinding.FragmentGameDialogBinding

class GameDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // super.onCreateView(inflater, container, savedInstanceState)


        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameDialogBinding>(
                inflater, R.layout.fragment_game_dialog, container, false)

        val popTitle: String = getString(R.string.popTitle_level)
        val popText: String = getString(R.string.popText_level, 2,3)

        val test = binding.popText
        test.setText("changed, it worked!")


        return binding.root
    }
}