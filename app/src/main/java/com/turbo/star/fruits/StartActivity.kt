package com.turbo.star.fruits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.turbo.star.R
import com.turbo.star.databinding.ActivityStartBinding

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