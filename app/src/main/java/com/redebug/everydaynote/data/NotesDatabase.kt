package com.redebug.everydaynote.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [Notes::class, Todo::class])
abstract class NotesDatabase: RoomDatabase()  {
    abstract fun notesDao():NotesDao
    abstract fun todoDao(): TodoDao

    companion object{
        @Volatile
        private var INSTANCE :NotesDatabase? = null

        fun getInstance(context: Context):NotesDatabase{
            return INSTANCE ?: synchronized(this){
                val room = Room.databaseBuilder(context,
                    NotesDatabase::class.java, "student_db")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = room
                room
            }
        }
    }
}