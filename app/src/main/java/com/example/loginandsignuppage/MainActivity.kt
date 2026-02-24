package com.example.loginandsignuppage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loginandsignuppage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createbtn.setOnClickListener {
            startActivity(Intent(this, AddNote::class.java))
        }

        binding.openallbtn.setOnClickListener {
            startActivity(Intent(this, AllNotes::class.java))
        }
    }
}
