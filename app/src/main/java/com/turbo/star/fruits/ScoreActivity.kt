package com.turbo.star.fruits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.turbo.star.R
import com.turbo.star.databinding.ActivityScoreBinding
import com.turbo.star.fruits.adapters.FruitsScoreAdapter
import com.turbo.star.fruits.data.match.FruitScoreDao
import com.turbo.star.fruits.data.match.FruitsDatabase
import com.turbo.star.fruits.data.match.FruitsScoreRepository
import com.turbo.star.fruits.data.match.LocalFruitsScoreRepository
import com.turbo.star.fruits.models.FruitScore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ScoreActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityScoreBinding.inflate(layoutInflater)
    }

    private lateinit var database: FruitsDatabase
    private lateinit var dao: FruitScoreDao
    private lateinit var fruitsRepository: FruitsScoreRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = FruitsDatabase.instance(applicationContext)
        dao = database.fruitScoreDao()
        fruitsRepository = LocalFruitsScoreRepository(dao)

        val adapter = FruitsScoreAdapter()
        adapter.fruitsScore = emptyList()

        binding.resultRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.resultRecyclerView.adapter = adapter

        lifecycleScope.launch {
            fruitsRepository.getAll()
                .map { list -> list.map { FruitScore(it.count, it.imageRes, it.score) } }
                .collect {
                    adapter.fruitsScore = it
                    adapter.notifyDataSetChanged()
                }
        }

        binding.tryAgainButton.setOnClickListener {
            lifecycleScope.launch {
                fruitsRepository.clear()
            }

            finish()
        }
    }

    override fun onDestroy() {

        lifecycleScope.launch {
            fruitsRepository.clear()
        }

        super.onDestroy()

    }

}