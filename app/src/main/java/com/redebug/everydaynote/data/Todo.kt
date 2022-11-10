package com.redebug.everydaynote.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_list")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tittle: String,
    val description:String,
    @ColumnInfo(name = "is_cheked")
    val isChecked: Boolean = false
)