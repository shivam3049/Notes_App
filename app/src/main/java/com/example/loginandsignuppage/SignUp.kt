package com.example.loginandsignuppage

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginandsignuppage.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.registerbutton.setOnClickListener {

            val email = binding.email.text.toString().trim()
            val username = binding.Username.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val repeat = binding.repeatpassword.text.toString().trim()

            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || repeat.isEmpty()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != repeat) {
                Toast.makeText(this, "Password must match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()

                        // GO TO LOGIN PAGE
                        startActivity(Intent(this, login_page::class.java))
                        finish()

                    } else {
                        Toast.makeText(this, "Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // Already have account → go to login
        binding.signbutton.setOnClickListener {
            startActivity(Intent(this, login_page::class.java))
        }
    }
}
