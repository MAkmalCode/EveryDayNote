package com.redebug.everydaynote.fragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.redebug.everydaynote.InseertTodoActivity
import com.redebug.everydaynote.R
import com.redebug.everydaynote.TodoDetailActivity
import com.redebug.everydaynote.adapter.TodoAdapter
import com.redebug.everydaynote.data.NotesDatabase
import kotlinx.coroutines.launch

class TodoFragment : Fragment(R.layout.fragment_todo){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview)
        val buttonInsertTodo: ExtendedFloatingActionButton = view.findViewById(R.id.btn_add_todo)
        val database = NotesDatabase.getInstance(requireContext())

        buttonInsertTodo.setOnClickListener{
            val intent = Intent(this@TodoFragment.context, InseertTodoActivity::class.java)
            startActivity(intent)
        }

        lifecycleScope.launch {
            val todoList = database.todoDao().getTodos()
            todoList.collect{
                val adapter = TodoAdapter(it)
                recyclerView.adapter = adapter
                adapter.itemCheckListener = {todo ->
                    lifecycleScope.launch {
                        database.todoDao().updateTodo(todo)
                    }
                }
                adapter.itemClickListener = {
                    val intent = Intent(this@TodoFragment.context, TodoDetailActivity::class.java)
                    intent.putExtra("id", it.id)
                    startActivity(intent)
                }
                adapter.deleteClickListener = {
                    lifecycleScope.launch {
                        MaterialAlertDialogBuilder(this@TodoFragment.requireContext())
                            .setTitle("Menghapus")
                            .setMessage("Anda yakin ingin menghapus")
                            .setNegativeButton("tidak") { dialog, which ->
                                dialog.dismiss()
                            }
                            .setPositiveButton("ya") { dialog, which ->
                                lifecycleScope.launch {
                                    database.todoDao().deleteTodo(it)
                                    Toast.makeText(this@TodoFragment.context, "Berhasil ter delete", Toast.LENGTH_SHORT).show()
                                }
                            }
                            .show()
                    }
                }
            }
        }
    }
}