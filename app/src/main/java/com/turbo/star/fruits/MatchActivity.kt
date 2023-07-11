package com.turbo.star.fruits

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.turbo.star.R
import com.turbo.star.databinding.ActivityMatchBinding
import com.turbo.star.fruits.adapters.FruitImageAdapter
import com.turbo.star.fruits.adapters.FruitsScoreAdapter
import com.turbo.star.fruits.data.match.FruitScoreDao
import com.turbo.star.fruits.data.match.FruitsDatabase
import com.turbo.star.fruits.data.match.FruitsScoreRepository
import com.turbo.star.fruits.data.match.LocalFruitsScoreRepository
import com.turbo.star.fruits.models.FruitCell
import com.turbo.star.fruits.models.FruitScore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MatchActivity : AppCompatActivity() {

    private val viewBinding by lazy {
        ActivityMatchBinding.inflate(layoutInflater)
    }

    private lateinit var scoreAdapter: FruitsScoreAdapter
    private lateinit var fruitsAdapter: FruitImageAdapter

    private val fruits = listOf(
        R.drawable.grapes,
        R.drawable.heart,
        R.drawable.green_apple,
        R.drawable.orange,
        R.drawable.plum,
        R.drawable.strawberry
    )

    private lateinit var database: FruitsDatabase
    private lateinit var dao: FruitScoreDao
    private lateinit var fruitsRepository: FruitsScoreRepository

    private val timer = object : CountDownTimer(60_000, 1_000) {
        @SuppressLint("SetTextI18n")
        override fun onTick(millisUntilFinished: Long) {
            viewBinding.timerTextView.text = "00:${millisUntilFinished / 1000}"
        }

        override fun onFinish() {
            startActivity(Intent(this@MatchActivity, ScoreActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)



        database = FruitsDatabase.instance(applicationContext)
        dao = database.fruitScoreDao()
        fruitsRepository = LocalFruitsScoreRepository(dao)



        setupSlideBox()
        setupFruitsAdapter()

        viewBinding.fruitsGridView.setOnMatchListener(onMatchListener = object : MatchListener {
            override fun onMatch(count: Int, score: Double, image: Int) {
                lifecycleScope.launch {
                    fruitsRepository.save(
                        score = com.turbo.star.fruits.data.match.FruitScore(0, count, score, image)
                    )
                }
            }
        })

        viewBinding.reloadFruits.setOnClickListener {
            (it as LottieAnimationView).playAnimation()
            setupFruitsAdapter()
        }

        lifecycleScope.launch {
            fruitsRepository.getAll()
                .map { list -> list.map { FruitScore(it.count, it.imageRes, it.score) } }
                .collect {
                    scoreAdapter.fruitsScore = it
                    scoreAdapter.notifyDataSetChanged()
                }
        }
    }

    private fun setupSlideBox() {
        viewBinding.slideBox.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        scoreAdapter = FruitsScoreAdapter()
        scoreAdapter.fruitsScore = emptyList()

        viewBinding.slideBox.adapter = scoreAdapter
    }

    private fun setupFruitsAdapter() {
        fruitsAdapter = FruitImageAdapter()
        fruitsAdapter.fruits = generateBoard()
        fruitsAdapter.notifyDataSetChanged()

        viewBinding.fruitsGridView.adapter = fruitsAdapter
    }

    private fun generateBoard(): List<FruitCell> {
        val fruits = mutableListOf<FruitCell>()
        for (i: Int in 1..GRID_ROWS * GRID_COLS) {
            fruits.add(FruitCell(id = i, imageRes = this.fruits.random()))
        }
        return fruits
    }
    override fun onStart() {
        super.onStart()

        lifecycleScope.launch {
            fruitsRepository.clear()
        }
        timer.start()
    }

    companion object {
        const val GRID_COLS = 5
        const val GRID_ROWS = 5
    }
}

