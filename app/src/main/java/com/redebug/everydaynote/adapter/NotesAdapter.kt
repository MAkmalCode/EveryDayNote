package com.redebug.everydaynote.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.redebug.everydaynote.R
import com.redebug.everydaynote.data.Notes


class NotesAdapter(val noteList: List<Notes>): RecyclerView.Adapter<NotesAdapter.NotesViewholder>() {

    var itemClickListener: ((Notes) -> Unit)? = null
    var deleteClickListener:((Notes) -> Unit)? = null

    class NotesViewholder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textJudul:TextView = itemView.findViewById(R.id.text_tittle)
        val textDate:TextView = itemView.findViewById(R.id.text_date)
        val textContent:TextView = itemView.findViewById(R.id.text_content)
        val buttonDelete: ImageButton = itemView.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notes, parent, false)
        return NotesViewholder(view)
    }

    override fun onBindViewHolder(holder: NotesViewholder, position: Int) {
        val note: Notes = noteList[position]
        holder.textJudul.text = note.tittle
        holder.textDate.text = note.date
        holder.textContent.text = note.content
        holder.itemView.setOnClickListener{
            itemClickListener?.invoke(note)
        }
        holder.buttonDelete.setOnClickListener {
            deleteClickListener?.invoke(note)
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }
}