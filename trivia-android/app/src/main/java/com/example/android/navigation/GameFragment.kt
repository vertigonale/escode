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

package com.example.android.navigation

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameBinding
//import kotlinx.android.synthetic.main.fragment_game.*

class GameFragment : Fragment() {
    data class Question(
            val text: String,
            val answers: List<String>)

    private var heartCount: Int = 3
    var heartCountString = heartCount.toString()

    // The first answer is the correct one.  We randomize the answers before showing the text.
    // All questions must have four answers.  We'd want these to contain references to string
    // resources so we could internationalize. (or better yet, not define the questions in code...)
    private val questions: MutableList<Question> = mutableListOf(
            Question(text = "Frage 1",
                    answers = listOf("richtig", "falsch1", "falsch2", "falsch3")),
            Question(text = "Frage 2",
                    answers = listOf("richtig", "falsch1", "falsch2", "falsch3")),
            Question(text = "Frage 3",
                    answers = listOf("richtig", "falsch1", "falsch2", "falsch3")),
            Question(text = "Frage 4",
                    answers = listOf("richtig", "falsch1", "falsch2", "falsch3")),
            Question(text = "Frage 5",
                    answers = listOf("richtig", "falsch1", "falsch2", "falsch3")),
            Question(text = "Frage 6",
                    answers = listOf("richtig", "falsch1", "falsch2", "falsch3")),
            Question(text = "Frage 7",
                    answers = listOf("richtig", "falsch1", "falsch2", "falsch3")),
            Question(text = "Frage 8",
                    answers = listOf("richtig", "falsch1", "falsch2", "falsch3")),
            Question(text = "Frage 9",
                    answers = listOf("richtig", "falsch1", "falsch2", "falsch3")),
            Question(text = "Frage 10",
                    answers = listOf("richtig", "falsch1", "falsch2", "falsch3"))
    )

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 3)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
                inflater, R.layout.fragment_game, container, false)

        // Shuffles the questions and sets the question index to the first question.
        startQuestions()

        // Bind this fragment class to the layout
        binding.game = this

        // Set the onClickListener for the submitButton
        binding.submitButton.setOnClickListener { view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            // Do nothing if nothing is checked (id == -1)
            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }
                // The first answer in the original question is always the correct one, so if our
                // answer matches, we have the correct answer.
                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    questionIndex++
                    // Advance to the next question
                    heartCount = 3
                    heartCountString = "(plus) " + heartCount.toString()
                    if (questionIndex < numQuestions) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        binding.invalidateAll()
                    } else {
                        // We've won!  Navigate to the gameWonFragment.
                        view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameDialog(/*numQuestions,questionIndex*/))
                    }
                } else {
                    // Wrong answer! A wrong answer sends us to beginning of puzzle.
//                    if (heartCount == 0) {
//
//                    }
                    if (heartCount > 0) {
                        setQuestion()
                        binding.invalidateAll()
                        heartCount--
                        heartCountString = "(minus) " + heartCount.toString()
                    } else {
                        questionIndex++
//                    view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToSelf())
                        heartCount = 3
                        heartCountString = "(reset) " + heartCount.toString()
                        if (questionIndex < numQuestions) {
                            currentQuestion = questions[questionIndex]
                            setQuestion()
                            binding.invalidateAll()
                        } else {
                            // We've won!  Navigate to the gameWonFragment.
                            view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameDialog(/*numQuestions,questionIndex*/))
                        }
                    }
                }
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
        setQuestion()
    }

    // Sets the question and randomizes the answers.  This only changes the data, not the UI.
    // Calling invalidateAll on the FragmentGameBinding updates the data.
    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        // randomize the answers into a copy of the array
        answers = currentQuestion.answers.toMutableList()
        // and shuffle them
        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_trivia_question, questionIndex + 1, numQuestions)
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
