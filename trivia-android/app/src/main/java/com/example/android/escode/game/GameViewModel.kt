package com.example.android.escode.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.android.escode.database.EscodeDB
import com.example.android.escode.database.EscodeDao

class GameViewModel(
        val database: EscodeDao,
        application: Application) : AndroidViewModel(application) {
}