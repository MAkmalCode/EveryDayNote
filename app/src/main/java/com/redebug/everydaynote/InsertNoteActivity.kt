package com.redebug.everydaynote

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.redebug.everydaynote.data.Notes
import com.redebug.everydaynote.data.NotesDatabase
import kotlinx.coroutines.launch

class InsertNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_note)

        val edittextDate: TextInputEditText = findViewById(R.id.et_date_input)
        val edittextTittle: TextInputEditText = findViewById(R.id.et_tittle_input)
        val edittextContent: TextInputEditText = findViewById(R.id.et_content_input)
        val buttonSave: Button = findViewById(R.id.btn_save)
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar_input)
        val database = NotesDatabase.getInstance(this)

        buttonSave.setOnClickListener {
            val textTittle = edittextTittle.text.toString()
            val textDate = edittextDate.text.toString()
            val textContent = edittextContent.text.toString()

            val note = Notes(id = 0, tittle = textTittle, date = textDate, content = textContent)

            if (textTittle.isEmpty() && textDate.isEmpty() && textContent.isEmpty()){
                val intent = Intent(this@InsertNoteActivity, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@InsertNoteActivity, "Masih ada yang kosong", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                lifecycleScope.launch{
                    database.notesDao().inputNote(note)
                    val intent = Intent(this@InsertNoteActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}
