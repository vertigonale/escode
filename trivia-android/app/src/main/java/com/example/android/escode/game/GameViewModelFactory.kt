package com.example.android.escode.game

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.escode.database.EscodeDao

class GameViewModelFactory (
    private val dataSource: EscodeDao,
    private val application: Application) : ViewModelProvider.Factory {

        // takes any type of class as argument, returns ViewModel
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            // check if viewModel class is available, if yes, return instance of it
            if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
                return GameViewModel(dataSource, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}