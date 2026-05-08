package com.example.loginandsignuppage

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginandsignuppage.databinding.ActivityAllNotesBinding
import com.example.loginandsignuppage.databinding.DialogUpdateNotesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AllNotes : AppCompatActivity(), NoteAdapter.OnItemClickListener {

    private val binding: ActivityAllNotesBinding by lazy {
        ActivityAllNotesBinding.inflate(layoutInflater)
    }

    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.notesrecycleview.layoutManager = LinearLayoutManager(this)
        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser ?: return
        val noteRef = databaseReference.child("users")
            .child(user.uid)
            .child("notes")

        noteRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                
                val noteList = mutableListOf<NoteItem>()
                for (data in snapshot.children) {
                    val note = data.getValue(NoteItem::class.java)
                    if (note != null) {
                        noteList.add(note)
                    }
                }
                val adapter = NoteAdapter(noteList.reversed(), this@AllNotes)
                binding.notesrecycleview.adapter = adapter
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
    override fun onUpdateClick(noteId: String, currentTitle: String, currentDiscription: String) {

        val dialogBinding = DialogUpdateNotesBinding.inflate(
            LayoutInflater.from(this)
        )

        val dialog = AlertDialog.Builder(this)
            .setTitle("Update Note")
            .setView(dialogBinding.root)
            .setPositiveButton("Update") { d, _ ->

                val newTitle = dialogBinding.updatetitle.text.toString()
                val newDesc = dialogBinding.updatediscription.text.toString()

                updateNote(noteId, newTitle, newDesc)
                d.dismiss()
            }
            .setNegativeButton("Cancel") { d, _ -> d.dismiss() }
            .create()

        dialogBinding.updatetitle.setText(currentTitle)
        dialogBinding.updatediscription.setText(currentDiscription)

        dialog.show()
    }

    override fun onDeleteClick(noteId: String) {

        val uid = auth.currentUser?.uid ?: return

        databaseReference.child("users")
            .child(uid)
            .child("notes")
            .child(noteId)
            .removeValue()
    }

    private fun updateNote(noteId: String, newTitle: String, newDescription: String) {

        val uid = auth.currentUser?.uid ?: return
        val updateNote = NoteItem(newTitle, newDescription, noteId)
        databaseReference.child("users")
            .child(uid)
            .child("notes")
            .child(noteId)
            .setValue(updateNote)
            .addOnSuccessListener {
                Toast.makeText(this, "Note Updated Successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to Update", Toast.LENGTH_SHORT).show()
            }
    }
}
