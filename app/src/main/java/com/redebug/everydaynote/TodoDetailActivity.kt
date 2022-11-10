package com.redebug.everydaynote

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.redebug.everydaynote.data.NotesDatabase
import kotlinx.coroutines.launch

class TodoDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_detail)

        val textTittleTodo:TextView = findViewById(R.id.text_todo)
        val textDescriptionTodo:TextView =  findViewById(R.id.text_deskripsi)
        val buttonBack:MaterialToolbar = findViewById(R.id.toolbarDetail)
        val database = NotesDatabase.getInstance(this)

        val buttonCopyDescription:ImageButton = findViewById(R.id.btn_copy_detail_description)

        buttonCopyDescription.setOnClickListener {
            val clipBoard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("TextView", textDescriptionTodo.text )
            clipBoard.setPrimaryClip(clip)
            Toast.makeText(this@TodoDetailActivity, "Berhasil copy ke clipboard", Toast.LENGTH_SHORT).show()
        }

        val getId = intent.getIntExtra("id", 0)

        lifecycleScope.launch {
            val todo= database.todoDao().getTodoDetail(todoId = getId)
            textTittleTodo.text = "${todo.tittle}"
            textDescriptionTodo.text = "${todo.description}"

            buttonBack.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.share -> {
                        val sendIntent = Intent()
                        sendIntent.action = Intent.ACTION_SEND
                        sendIntent.putExtra(
                            Intent.EXTRA_TEXT,
                            "${textTittleTodo.text}\n${textDescriptionTodo.text}"
                        )
                        sendIntent.type = "text/plain"
                        startActivity(sendIntent)
                        true
                    }
                    R.id.edit -> {
                        lifecycleScope.launch {
                            val intent = Intent(this@TodoDetailActivity, UpdateTodoActivity::class.java)
                            intent.putExtra("id", todo.id)
                            intent.putExtra("todo", todo.tittle)
                            intent.putExtra("decription", todo.description)
                            intent.putExtra("cheked", todo.isChecked)
                            startActivity(intent)
                        }
                        true
                    }
                    else ->{false}
                }
            }
        }
        buttonBack.setNavigationOnClickListener { finish() }
    }
}