package com.turbo.star.fruits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.turbo.star.R
import com.turbo.star.databinding.ActivityMatchBinding
import com.turbo.star.fruits.adapters.FruitImageAdapter
import com.turbo.star.fruits.adapters.FruitsScoreAdapter
import com.turbo.star.fruits.models.FruitCell
import com.turbo.star.fruits.models.FruitScore

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        setupSlideBox()
        setupFruitsAdapter()

    }

    private fun setupSlideBox() {
        viewBinding.slideBox.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        scoreAdapter = FruitsScoreAdapter()
        scoreAdapter.fruitsScore = listOf(
            FruitScore(4, R.drawable.heart, 40.0002),
            FruitScore(2, R.drawable.green_apple, 40.0002),
            FruitScore(6, R.drawable.grapes, 20.0002),
            FruitScore(6, R.drawable.grapes, 20.0002),
        )
        viewBinding.slideBox.adapter = scoreAdapter
    }

    private fun setupFruitsAdapter() {
        fruitsAdapter = FruitImageAdapter()
        fruitsAdapter.fruits = generateBoard()

        viewBinding.fruitsGridView.adapter = fruitsAdapter
    }

    private fun generateBoard(): List<FruitCell> {
        val fruits = mutableListOf<FruitCell>()
        for (i: Int in 1..GRID_ROWS * GRID_COLS) {
            fruits.add(FruitCell(id = i, imageRes = this.fruits.random()))
        }
        return fruits
    }

    companion object {
        const val GRID_COLS = 5
        const val GRID_ROWS = 5
    }
}
