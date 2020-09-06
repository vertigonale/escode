package com.example.android.escode

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EscodeViewModel : ViewModel() {
    companion object {
        private const val AUTHOR_ARGUMENT = "author"

        fun createArguments(levelIndex: Int): Bundle {
            val bundle = Bundle()
            bundle.putSerializable(AUTHOR_ARGUMENT, levelIndex)

            return bundle
        }
    }

    val lvlIndex: Int = 0
    fun loadArguments(arguments: Bundle?) {
        if (arguments == null) {
            return
        }

        val levelIndex: Int? = arguments.get(AUTHOR_ARGUMENT) as Int
//        this.lvlIndex.(levelIndex)
    }

}