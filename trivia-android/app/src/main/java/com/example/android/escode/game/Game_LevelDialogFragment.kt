package com.example.android.escode.game


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import com.example.android.escode.R
import com.example.android.escode.databinding.FragmentGameLevelDialogBinding
import kotlinx.android.synthetic.main.fragment_game_level_dialog.*


//class GameDialogFragment : DialogFragment() {
class Game_LevelDialogFragment : Fragment() {

    var levelScorePB: Int = 5
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameLevelDialogBinding>(
                inflater, R.layout.fragment_game_level_dialog, container, false)

////////////////////////
//        levelScorePB = arguments!!.getInt("levelScoreP")
    // Variables
        // Texts...
        binding.popTitle.setText(getString(R.string.popTitle_level))
        binding.popText.setText(getString(R.string.popText_level, levelScorePB,9))
        // Buttons...
        val btn_home: ImageButton = binding.popBtn1
        val btn_score: Button = binding.popBtn2
        val btn_next: ImageButton = binding.popBtn3



        btn_score.setOnClickListener(View.OnClickListener {
            view?.findNavController()?.navigate(Game_LevelDialogFragmentDirections.actionGameDialogFragmentToScoreboardFragment())
        })

        btn_next.setOnClickListener(View.OnClickListener {
            closefragment()
//            view?.findNavController()?.navigate(GameDialogFragmentDirections.actionGameDialogFragmentToGameFragment())
        })

        btn_home.setOnClickListener(View.OnClickListener {
            view?.findNavController()?.navigate(Game_LevelDialogFragmentDirections.actionGameDialogFragmentToTitleFragment())
        })




        return binding.root
    }


    private fun closefragment() {
        val manager: FragmentManager? = fragmentManager
        manager!!.popBackStack();
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