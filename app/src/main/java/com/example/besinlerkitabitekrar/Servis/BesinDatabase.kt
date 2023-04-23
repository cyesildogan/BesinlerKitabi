package com.example.besinlerkitabitekrar.Servis

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.besinlerkitabitekrar.model.Besin


@Database(entities = arrayOf(Besin::class), version = 1 , exportSchema = true)
abstract class BesinDatabase : RoomDatabase() {

    abstract fun besinDAO() : BesinDAO
    //singleton
    companion object {
        @Volatile  private var instance : BesinDatabase?=null
        private val lock = Any()

        operator fun invoke(context: Context) = instance?: synchronized(lock){
            instance?: databaseOlustur(context).also {
                instance=it
            }
        }

        private fun databaseOlustur(context : Context) = Room.databaseBuilder(
            context.applicationContext,BesinDatabase::class.java,"besindatabase").build()
        }


    }

