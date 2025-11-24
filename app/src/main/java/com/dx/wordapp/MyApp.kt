package com.dx.wordapp

import android.app.Application
import androidx.room.Room
import com.dx.wordapp.data.db.MyDatabase
import com.dx.wordapp.data.repo.WordsRepo

class MyApp: Application() {
    lateinit var repo : WordsRepo

    override fun onCreate() {
        super.onCreate()

        val db = Room.databaseBuilder(
            this,
            MyDatabase::class.java,
            MyDatabase.NAME
        )
            .build()
        repo = WordsRepo(db.getWordsDao())
    }
}