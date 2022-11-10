package com.redebug.everydaynote.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.redebug.everydaynote.InsertNoteActivity
import com.redebug.everydaynote.R
import com.redebug.everydaynote.UpdateNoteActivity
import com.redebug.everydaynote.adapter.NotesAdapter
import com.redebug.everydaynote.data.Notes
import com.redebug.everydaynote.data.NotesDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NotesFragment : Fragment(R.layout.fragment_notes) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview)
        val buttonInput: ExtendedFloatingActionButton = view.findViewById(R.id.btn_input)
        val database = NotesDatabase.getInstance(requireContext())

        lifecycleScope.launch{
            val noteList: Flow<List<Notes>> = database.notesDao().getListNote()
            noteList.collect{
                val adapter = NotesAdapter(it)
                recyclerView.adapter = adapter

                buttonInput.setOnClickListener{
                    startActivity(Intent(this@NotesFragment.context, InsertNoteActivity::class.java))
                }

                adapter.itemClickListener = { note ->
                    lifecycleScope.launch {
                        database.notesDao().updateNote(note)
                        val intent = Intent(this@NotesFragment.context, UpdateNoteActivity::class.java)
                        intent.putExtra("id", note.id)
                        intent.putExtra("tittle", note.tittle)
                        intent.putExtra("date", note.date)
                        intent.putExtra("content", note.content)
                        startActivity(intent)
                    }
                }

                adapter.deleteClickListener = {
                    MaterialAlertDialogBuilder(this@NotesFragment.requireContext())
                        .setTitle("Menghapus")
                        .setMessage("Anda yakin ingin menghapus\nnote ini?")
                        .setNegativeButton("tidak") { dialog, which ->
                            dialog.dismiss()
                        }
                        .setPositiveButton("ya") { dialog, which ->
                            lifecycleScope.launch{
                                database.notesDao().deleteNote(it)
                                Toast.makeText(this@NotesFragment.context, "Berhasil menghapus", Toast.LENGTH_SHORT).show()
                            }
                        }.show()
                }
            }
        }

    }
}