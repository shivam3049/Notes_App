package com.example.loginandsignuppage

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginandsignuppage.databinding.ActivityLoginPageBinding
import com.google.firebase.auth.FirebaseAuth

class login_page : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private val binding by lazy {
        ActivityLoginPageBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.loginbutton.setOnClickListener {
            val email = binding.username.text.toString().trim()
            val pass = binding.Password.text.toString().trim()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please fill details", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        binding.signUpbutton.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
    }
}
