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

package com.example.android.escode.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.escode.R
import com.example.android.escode.databinding.FragmentGameBinding
import kotlinx.android.synthetic.main.fragment_game.*

//import kotlinx.android.synthetic.main.fragment_game.*

class GameFragment : Fragment() {
    data class Question(
            val text: String,
            val answers: List<String>)

    data class Level(
            val nr: Int,
            val questions: List<Question>
    )

    // The first answer is the correct one.  We randomize the answers before showing the text.
    // All questions must have four answers.  We'd want these to contain references to string
    // resources so we could internationalize. (or better yet, not define the questions in code...)
    private val questionsL1: MutableList<Question> = mutableListOf(
            Question(text = "Frage 1",
                    answers = listOf("richtig", "falsch1", "falsch2", "falsch3")),
            Question(text = "Frage 2",
                    answers = listOf("richtig", "falsch1", "falsch2", "falsch3")),
            Question(text = "Frage 3",
                    answers = listOf("richtig", "falsch1", "falsch2", "falsch3"))
    )

    private val questionsL2: MutableList<Question> = mutableListOf(
            Question(text = "Frage 4",
                    answers = listOf("richtig", "falsch1", "falsch2", "falsch3")),
            Question(text = "Frage 5",
                    answers = listOf("richtig", "falsch1", "falsch2", "falsch3")),
            Question(text = "Frage 6",
                    answers = listOf("richtig", "falsch1", "falsch2", "falsch3"))
    )

    private val questionsL3: MutableList<Question> = mutableListOf(
            Question(text = "Frage 7",
                    answers = listOf("richtig", "falsch1", "falsch2", "falsch3")),
            Question(text = "Frage 8",
                    answers = listOf("richtig", "falsch1", "falsch2", "falsch3")),
            Question(text = "Frage 9",
                    answers = listOf("richtig", "falsch1", "falsch2", "falsch3"))
    )

    private val levels : MutableList<Level> = mutableListOf(
            Level(nr = 1, questions = questionsL1
            ),
            Level(nr = 2, questions = questionsL2
            ),
            Level(nr = 3, questions = questionsL3
            )
    )

    lateinit var currentLevel: Level
    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private var levelIndex = 0
    private val numQuestions = 3
    private val numLevels = 3
    /*Math.min((questions.size + 1) / 2, 3)*/

    private var heartCountPuzzle: Int = 3
    private var heartCountLevel = 0
    private var heartCountTotal = 0
    lateinit var heartCountPuzzleString: String
    lateinit var heartCountLevelString: String
    lateinit var heartCountTotalString: String



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
                inflater, R.layout.fragment_game, container, false)

        // Shuffles the questions and sets the question index to the first question.
        startQuestions()

        // Bind this fragment class to the layout
        binding.game = this

        val radioGroup = binding.questionRadioGroup


        // Set the onClickListener for the submitButton
        binding.submitButton.setOnClickListener { view: View ->



            var checkedId = -1
            checkedId = radioGroup.checkedRadioButtonId
            var isChecked = diyIsChecked()
            // diyIsChecked()

            lateinit var selectedBtn: RadioButton
            // Do nothing if nothing is checked (id == -1)
            if (-1 != checkedId && isChecked) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.btn0 -> {answerIndex = 0;
                        selectedBtn = btn0}
                    R.id.btn1 -> {answerIndex = 1;
                        selectedBtn = btn1}
                    R.id.btn2 -> {answerIndex = 2;
                        selectedBtn = btn2}
                    R.id.btn3 -> {answerIndex = 3;
                        selectedBtn = btn3}
                }

                // The first answer in the original question is always the correct one, so if our
                // answer matches, we have the correct answer.
                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    Toast.makeText(this.context, R.string.fb_noChoice, Toast.LENGTH_SHORT).show()
                    radioGroup.clearCheck()
                    advanceToNextQuestion()
                    enableAllBtns()
                    binding.invalidateAll()

                } else {

                    // Wrong answer! A wrong answer sends us to beginning of puzzle.
                    if (heartCountPuzzle > 0) {
                        resetQuestion()
                        disableSelectedBtn(selectedBtn)
                        radioGroup.clearCheck()
                        binding.invalidateAll()
                        Toast.makeText(this.context, R.string.fb_wrong, Toast.LENGTH_SHORT).show()
                    } else {
                        advanceToNextQuestion()
                        enableAllBtns()
                        binding.invalidateAll()
                    }
                }
            } else {
                Toast.makeText(this.context, R.string.fb_noChoice, Toast.LENGTH_SHORT).show()
            }
        }


        // in case we need it
/*        binding.btnMyDialog.setOnClickListener { view: View ->
            view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameDialog())
        }*/

        return binding.root
    }




    // sets the first question
    private fun startQuestions() {
        /*questions.shuffle()*/
        questionIndex = 0
        setLevel()
        setQuestion()
        heartCountToString()
    }

    private fun setLevel() {
        currentLevel = levels[levelIndex]
    }

    // Sets the question and randomizes the answers.  This only changes the data, not the UI.
    // Calling invalidateAll on the FragmentGameBinding updates the data.
    private fun setQuestion() {
        currentQuestion = currentLevel.questions[questionIndex]
        //currentQuestion = questions[questionIndex]
        // randomize the answers into a copy of the array
        answers = currentQuestion.answers.toMutableList()

        /*// and shuffle them
        answers.shuffle()*/

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_trivia_question, questionIndex + 1, numQuestions, levelIndex + 1, numLevels)
    }



    private fun resetQuestion() {
        setQuestion()
        heartCountPuzzle--
        heartCountToString()
    }

    private fun advanceToNextQuestion() {
        questionIndex++
        heartCountTotal+= heartCountPuzzle
        heartCountLevel += heartCountPuzzle
        heartCountPuzzle = 3
        heartCountToString()

        // Advance to the next question
        if (questionIndex < numQuestions) {
            currentQuestion = currentLevel.questions[questionIndex]
            setQuestion()
//            binding.invalidateAll()
        }
        else {
            // We've won!  Navigate to the gameWonFragment.
            // view?.findNavController()?.navigate(GameFragmentDirections.actionGameFragmentToGameDialog(/*numQuestions,questionIndex*/))
            if (levelIndex < 2) {
                levelIndex++
                heartCountLevel = 0
                //view?.findNavController()?.navigate(GameFragmentDirections.actionGameFragmentToGameDialog(/*numQuestions,questionIndex*/))
                showPopup()
//                view?.findNavController()?.navigate(GameFragmentDirections.actionGameFragmentToGameDialog(/*numQuestions,questionIndex*/))
            } else {

                levelIndex = 0
                view?.findNavController()?.navigate(GameFragmentDirections.actionGameFragmentToTitleFragment())
            }
        }
    }

    fun enableAllBtns (){
//        btn0.isChecked = false
        btn0.isEnabled = true
        btn1.isEnabled = true
        btn2.isEnabled = true
        btn3.isEnabled = true
    }

    // disables the selected radio button & enables them again for the next level
    fun disableSelectedBtn (selectedBtn: RadioButton){

        if (selectedBtn != btn0){
            selectedBtn.isEnabled = false
        }
        selectedBtn.isChecked = false

    }

    private fun heartCountToString() {
        heartCountPuzzleString = heartCountPuzzle.toString()
        heartCountLevelString = heartCountLevel.toString()
        heartCountTotalString = heartCountTotal.toString()
    }

    fun diyIsChecked (): Boolean {

        // State of the button, checked or not
        var chbtn0: Boolean = btn0.isChecked()
        var chbtn1: Boolean = btn1.isChecked()
        var chbtn2: Boolean = btn2.isChecked()
        var chbtn3: Boolean = btn3.isChecked()

        when(chbtn0 || chbtn1 || chbtn2 || chbtn3){
            true -> return true
            false -> return false
        }

    }


    fun showPopup (){

            val dialogFragment = GameDialogFragment()

            dialogFragment.show(fragmentManager, "game dialog")

    }


/*    fun onClick(view:View) {
        when (view.id) {

            R.id.btnMyDialog -> {
                val dialogFragment = GameDialogFragment()
                val bundle = Bundle()
                bundle.putBoolean("notAlertDialog", true)
                dialogFragment.arguments = bundle

            }
        }
    }*/
}
