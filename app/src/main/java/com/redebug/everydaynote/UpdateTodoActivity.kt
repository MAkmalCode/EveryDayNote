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

class UpdateTodoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_todo)

        val etTodo: TextInputEditText = findViewById(R.id.et_tittle_update)
        val etDescription: TextInputEditText = findViewById(R.id.et_content_update)
        val buttonUpdate: Button = findViewById(R.id.btn_save)
        val database = NotesDatabase.getInstance(this)

        val getCheked = intent.getBooleanExtra("cheked",false)
        val getTodo = intent.getStringExtra("todo")
        val getDecription = intent.getStringExtra("decription")
        val getId = intent.getIntExtra("id",0)

        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        etTodo.setText(getTodo)
        etDescription.setText(getDecription)

        buttonUpdate.setOnClickListener {
            val textTodo = etTodo.text.toString()
            val textDescription = etDescription.text.toString()

            val todo = Todo(id = getId, tittle = textTodo, description = textDescription, isChecked = getCheked)
            lifecycleScope.launch {
                database.todoDao().updateTodo(todo)
                val intent = Intent(this@UpdateTodoActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}