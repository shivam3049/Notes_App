package com.example.loginandsignuppage

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import com.example.loginandsignuppage.databinding.ActivityWelcomePageBinding

class Welcome_page : AppCompatActivity() {

    private val binding: ActivityWelcomePageBinding by lazy {
        ActivityWelcomePageBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, SignUp::class.java))
            finish()
        }, 2000)

        val text = "Welcome"
        val span = SpannableString(text)
        span.setSpan(ForegroundColorSpan(Color.RED), 0, 5, 0)
        span.setSpan(ForegroundColorSpan(Color.DKGRAY), 5, 7, 0)
        binding.welcomeText.text = span
    }
}
