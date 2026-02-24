package com.example.loginandsignuppage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginandsignuppage.databinding.NotesItemBinding

class NoteAdapter(
    private val notes: List<NoteItem>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    interface OnItemClickListener {
        fun onDeleteClick(noteId: String)
        fun onUpdateClick(noteId: String,title: String,description: String)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NotesItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)

        holder.binding.Updatebtn.setOnClickListener {
            listener.onUpdateClick(
                note.noteId ?: "",
                note.title ?: "",
                note.description ?: ""
            )
        }

        holder.binding.Deletebtn.setOnClickListener {
            listener.onDeleteClick(note.noteId ?: "")
        }
    }

    override fun getItemCount(): Int = notes.size

    class NoteViewHolder(val binding: NotesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: NoteItem) {
            binding.titletextView.text = note.title
            binding.discriptiontextView.text = note.description
            binding.discriptiontextView.movementMethod = android.text.method.ScrollingMovementMethod()
        }

    }
}
