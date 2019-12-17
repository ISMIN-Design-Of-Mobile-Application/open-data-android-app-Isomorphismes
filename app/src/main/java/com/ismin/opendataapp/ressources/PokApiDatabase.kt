package com.ismin.opendataapp.ressources

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ismin.opendataapp.interfaces.PokemonDAO
import com.ismin.opendataapp.pokapiclass.Pokemon

@Database(entities = [Pokemon::class], version = 1)
abstract class PokApiDatabase : RoomDatabase() {

    abstract fun getPokemonDao(): PokemonDAO

    companion object {
        private var INSTANCE: PokApiDatabase? = null

        fun getAppDatabase(context: Context): PokApiDatabase {
            if (INSTANCE == null) {
                synchronized(PokApiDatabase::class) {
                    INSTANCE = Room
                        .databaseBuilder(
                            context.applicationContext,
                            PokApiDatabase::class.java,
                            "pokemonsDB"
                        )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as PokApiDatabase
        }
    }
}