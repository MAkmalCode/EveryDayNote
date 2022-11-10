package com.redebug.everydaynote.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo_list")
    fun getTodos(): Flow<List<Todo>>

    @Query("SELECT * FROM todo_list WHERE id = :todoId")
    suspend fun  getTodoDetail(todoId:Int): Todo

    @Insert
    suspend fun insertTodo(todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)
}