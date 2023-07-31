package com.alchemistsgarden.androi.fruits.models

import androidx.annotation.DrawableRes

data class FruitScore(
    val count: Int, @DrawableRes val image: Int, val score: Double
) {

    val roundedScore: String
        get() = String.format("%.1f", score)
}