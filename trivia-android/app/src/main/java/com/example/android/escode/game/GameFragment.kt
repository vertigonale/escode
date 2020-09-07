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

import android.R.attr.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.android.escode.R
import com.example.android.escode.databinding.FragmentGameBinding
import kotlinx.android.synthetic.main.fragment_game.*
import com.example.android.escode.EscodeViewModel
import com.example.android.escode.popCommunicator


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
            Question(text = "What's the origin of the word \"Kotlin\"?",
                    answers = listOf("name of a little island in Russia",
                            "surname of the Developer of Kotlin",
                            "THE favorite chocolate brand of android programmers",
                            "anagram of \"link to\" (link to android)")),
            Question(text = "Which declaration throws an exception in Kotlin?",
                    answers = listOf("var var1 : String = 5",
                            "val var2 = 3",
                            "lateinit var3 : String",
                            "var var4 = true")),
            Question(text = "What is the keyword to declare a function in Kotlin?",
                    answers = listOf("fun",
                            "do",
                            "def",
                            "all of them"))
    )

    private val questionsL2: MutableList<Question> = mutableListOf(
            Question(text = "Which declaration is written correctly in Kotlin?",
                    answers = listOf("var var1 : String = \"HAHAHA\"",
                            "lateinit var2 : Int",
                            "val var3 = 3; var3 = 5",
                            "var var4 : Long")),
            Question(text = "What is the equivalent of \"System.out.println()\" in Kotlin?",
                    answers = listOf("println()",
                            "out()",
                            "show()",
                            "printout")),
            Question(text = "What is the equivalent of the switch-statement in Kotlin?",
                    answers = listOf("when",
                            "wheel",
                            "whenever",
                            "pick"))
    )

    private val questionsL3: MutableList<Question> = mutableListOf(
            Question(text = "In Java, to hold data in a class and NOTHING ELSE, we need to define constructors, variables to store data, getter & setter methods, etc. Whereas in Kotlin, it is a bit different \nWhat is necessary to get the same result in Kotlin?",
                    answers = listOf("declare a class with keyword \"data\"",
                            "create getter/setter methods",
                            "define universal constructor for different kind of data",
                            "give data a good long hug")),
            Question(text = "What is possible with \"if\" in Kotlin?",
                    answers = listOf("use it as an expression & assign its value to a variable",
                            "end an if-statement with \"fi\"",
                            "extend \"if\" with \"otherwise\"",
                            "if() is TRUE by default")),
            Question(text = "Which symbol performs a safe call in Kotlin? (calling a method or accessing a property if the receiver is non-null)",
                    answers = listOf("?.",
                            "->",
                            ":",
                            "$"))
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

//    // passing Variable variables
    var currentScore: Int = 7
//    lateinit var comm: popCommunicator

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


//        // TESTING PURPOSE
//        binding.testPopup.setOnClickListener { view: View ->
//            showPopup()
//        }

//        comm = activity as popCommunicator


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
                    answers.shuffle()

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


        return binding.root
    }




    // sets the first question
    private fun startQuestions() {
        questionIndex = 0
        setLevel()
        setQuestion()
        heartCountToString()
        answers.shuffle()
    }

    private fun setLevel() {
        currentLevel = levels[levelIndex]
    }


    private fun setQuestion() {
        currentQuestion = currentLevel.questions[questionIndex]
        //currentQuestion = questions[questionIndex]
        answers = currentQuestion.answers.toMutableList()

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_trivia_question, questionIndex + 1, numQuestions, levelIndex + 1, numLevels)
    }

    //// NOTE: jedes mal wenn man falsch antwortet steht die richtige antwort immer auf der ersten stelle :P


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
            answers.shuffle()
        }
        else {

            if (levelIndex < 2) {
                currentScore = heartCountLevel
//                comm.passDataCom(currentScore)
                levelIndex++
                heartCountLevel = 0
                showLevelPopup()

            } else {

                levelIndex = 0
                showEndPopup()
            }
        }
    }
// enables them again for the next level
    fun enableAllBtns (){
//        btn0.isChecked = false
        btn0.isEnabled = true
        btn1.isEnabled = true
        btn2.isEnabled = true
        btn3.isEnabled = true
    }

    // disables the selected radio button
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


    fun showLevelPopup (){
//        val dialogFragment = GameDialogFragment()
//        dialogFragment.setTargetFragment(this, 0)
//        val manager: FragmentManager? = fragmentManager
//        val ft: FragmentTransaction = manager!!.beginTransaction()
        view?.findNavController()?.navigate(GameFragmentDirections.actionGameFragmentToGameLevelDialogFragment())
    }

    fun showEndPopup() {
        view?.findNavController()?.navigate(GameFragmentDirections.actionGameFragmentToGameEndDialogFragment())
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
