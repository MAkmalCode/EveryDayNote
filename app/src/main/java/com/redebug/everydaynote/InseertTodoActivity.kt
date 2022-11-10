package com.redebug.everydaynote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.redebug.everydaynote.data.NotesDatabase
import com.redebug.everydaynote.data.Todo
import kotlinx.coroutines.launch

class InseertTodoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inseert_todo)

        val tittleTodo: TextInputEditText = findViewById(R.id.et_tittle_input)
        val descriptionTodo: TextInputEditText = findViewById(R.id.et_description_input)
        val buttonSave: Button = findViewById(R.id.btn_save)
        val buttonBack:MaterialToolbar = findViewById(R.id.toolbar)
        val database = NotesDatabase.getInstance(this)

        buttonSave.setOnClickListener {
            val tittle = tittleTodo.text.toString()
            val description = descriptionTodo.text.toString()

            val todo = Todo(tittle = tittle, description = description)
            lifecycleScope.launch{
                database.todoDao().insertTodo(todo)
            }
            val intent = Intent(this@InseertTodoActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonBack.setNavigationOnClickListener { finish() }
    }
}