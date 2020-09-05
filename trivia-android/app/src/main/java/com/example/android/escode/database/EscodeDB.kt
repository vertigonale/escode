package com.example.android.escode.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LevelScore::class], version = 1, exportSchema = false)
abstract class EscodeDB : RoomDatabase() {

    abstract val EscodeDao: EscodeDao

    companion object {
        @Volatile
        private var INSTANCE: EscodeDB? = null

        fun getInstance(context: Context): EscodeDB {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            EscodeDB::class.java,
                            "escode_database"
                    )
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}