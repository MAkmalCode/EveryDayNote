package com.redebug.everydaynote.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    fun getListNote(): Flow<List<Notes>>

    @Insert
    suspend fun inputNote(student: Notes)

    @Delete
    suspend fun deleteNote(student: Notes)

    @Update
    suspend fun updateNote(student: Notes)

}