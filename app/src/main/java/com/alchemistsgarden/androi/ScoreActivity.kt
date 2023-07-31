package com.alchemistsgarden.androi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alchemistsgarden.androi.databinding.ActivityScoreBinding
import com.alchemistsgarden.androi.adapters.FruitsScoreAdapter
import com.alchemistsgarden.androi.data.match.FruitScoreDao
import com.alchemistsgarden.androi.data.match.FruitsDatabase
import com.alchemistsgarden.androi.data.match.FruitsScoreRepository
import com.alchemistsgarden.androi.data.match.LocalFruitsScoreRepository
import com.alchemistsgarden.androi.models.FruitScore
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