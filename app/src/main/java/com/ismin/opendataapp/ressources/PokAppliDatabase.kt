package com.ismin.opendataapp.ressources

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ismin.opendataapp.interfaces.PokemonDAO
import com.ismin.opendataapp.pokapiclass.Pokemon

@Database(entities = [Pokemon::class], version = 3)
abstract class PokAppliDatabase : RoomDatabase() {

    abstract fun getPokemonDao(): PokemonDAO

    companion object {
        private var INSTANCE: PokAppliDatabase? = null

        fun getAppDatabase(context: Context): PokAppliDatabase {
            if (INSTANCE == null) {
                synchronized(PokAppliDatabase::class) {
                    INSTANCE = Room
                        .databaseBuilder(
                            context.applicationContext,
                            PokAppliDatabase::class.java,
                            "pokemonsDB"
                        )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as PokAppliDatabase
        }
    }
}