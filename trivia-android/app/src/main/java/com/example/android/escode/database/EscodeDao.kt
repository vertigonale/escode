package com.example.android.escode.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EscodeDao {
    @Insert
    fun insertLevelScore(score: LevelScore)

    @Update
    fun updateLevelScore(score: LevelScore)

    @Insert
    fun insertTotalScore(score: TotalScore)

    @Update
    fun updateTotalScore(score: TotalScore)

    @Query("SELECT * FROM level_scores_table WHERE id = :key")
    fun get(key: Long): LevelScore

    @Query(value = "SELECT * FROM level_scores_table")
    fun getLevelScores(): List<LevelScore>
}