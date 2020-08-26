package com.example.android.escode.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.android.escode.database.EscodeDB
import com.example.android.escode.database.EscodeDao
import com.example.android.escode.database.LevelScore
import kotlinx.coroutines.*

class GameViewModel(
        private val levelKey: Long =0L,
        val database: EscodeDao,
        application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private suspend fun insertLvlScore(score: LevelScore) {
        withContext(Dispatchers.IO) {
            database.insertLevelScore(score)
        }
    }

    fun setLevelHeartCount(levelHeart: Int) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val levelScore = database.get(levelKey) ?: return@withContext
                levelScore.lvlScore = levelHeart
                database.updateLevelScore(levelScore)
            }
        }
    }
}