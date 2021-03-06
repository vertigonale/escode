package com.example.android.escode.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "level_scores_table")
data class LevelScore (
        @PrimaryKey(autoGenerate = true)
        var lvlScoreId: Long = 0L,

        @ColumnInfo(name = "level")
        var lvlIndex: Int,

        @ColumnInfo(name = "score")
        var lvlScore: Int
)