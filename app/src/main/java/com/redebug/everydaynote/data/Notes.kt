package com.redebug.everydaynote.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
class Notes (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val tittle: String,
    val date: String,
    val content: String
)