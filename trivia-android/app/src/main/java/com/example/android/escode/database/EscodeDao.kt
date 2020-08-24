package com.example.android.escode.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EscodeDao {
    @Insert
    fun insert(score: LevelScore)

    @Update
    fun update(score: LevelScore)

    @Query("SELECT * FROM level_scores_table")
    fun getLevelScores()
}