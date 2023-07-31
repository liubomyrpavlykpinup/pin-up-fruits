package com.alchemistsgarden.androi.fruits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alchemistsgarden.androi.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityStartBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            startGameButton.setOnClickListener {
                startActivity(Intent(this@StartActivity, MatchActivity::class.java))
            }

            exitGameButton.setOnClickListener {
                finish()
            }
        }
    }
}