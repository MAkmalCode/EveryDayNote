package com.redebug.everydaynote.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.redebug.everydaynote.R
import com.redebug.everydaynote.data.Todo

class TodoAdapter(val todolist: List<Todo>): RecyclerView.Adapter<TodoAdapter.TodoListViewHolder>() {

    var itemClickListener: ((Todo) -> Unit)? = null
    var itemCheckListener: ((Todo) -> Unit)? = null
    var deleteClickListener: ((Todo) -> Unit)? = null

    class TodoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val chekBoxTittle: MaterialCheckBox = itemView.findViewById(R.id.checkbox_todolist)
        val textDetail:TextView = itemView.findViewById(R.id.text_detail)
        val buttonDeleteTodo: ImageButton = itemView.findViewById(R.id.btn_delete_todo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo_list, parent, false)
        return TodoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        val todo: Todo = todolist[position]
        holder.textDetail.text = todo.tittle
        holder.chekBoxTittle.isChecked = todo.isChecked
        holder.textDetail.setOnClickListener {
            itemClickListener?.invoke(todo)
        }
        holder.chekBoxTittle.setOnCheckedChangeListener { compoundButton, isChecked->
            itemCheckListener?.invoke(todo.copy(isChecked = isChecked))
            if (todo.isChecked) {
                holder.textDetail.paintFlags = holder.textDetail.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                holder.textDetail.paintFlags = holder.textDetail.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
        if (todo.isChecked) {
            holder.textDetail.paintFlags = holder.textDetail.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.textDetail.paintFlags = holder.textDetail.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
        holder.buttonDeleteTodo.setOnClickListener {
            deleteClickListener?.invoke(todo)
        }
    }

    override fun getItemCount(): Int {
        return todolist.size
    }
}