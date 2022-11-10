package com.redebug.everydaynote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.redebug.everydaynote.data.Notes
import com.redebug.everydaynote.data.NotesDatabase
import kotlinx.coroutines.launch

class UpdateNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_note)

        val edittextDate: TextInputEditText = findViewById(R.id.et_date_update)
        val edittextTittle: TextInputEditText = findViewById(R.id.et_tittle_update)
        val edittextContent: TextInputEditText = findViewById(R.id.et_content_update)
        val buttonSave: Button = findViewById(R.id.btn_save)
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        val database = NotesDatabase.getInstance(this)
        val buttonShare: ImageButton = findViewById(R.id.btn_share)

        val getid = intent.getIntExtra("id",0)

        edittextTittle.setText(intent.getStringExtra("tittle"))
        edittextDate.setText(intent.getStringExtra("date"))
        edittextContent.setText(intent.getStringExtra("content"))

        buttonShare.setOnClickListener { view ->
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "${edittextTittle.text}\n\n${edittextContent.text}"
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }

        buttonSave.setOnClickListener {
            val textTittle = edittextTittle.text.toString()
            val textDate = edittextDate.text.toString()
            val textContent = edittextContent.text.toString()

            val note = Notes(id = getid, tittle = textTittle, date = textDate, content = textContent)

            lifecycleScope.launch {
                database.notesDao().updateNote(note)
                val intent = Intent(this@UpdateNoteActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}