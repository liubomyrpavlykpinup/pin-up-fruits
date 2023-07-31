package com.alchemistsgarden.androi.fruits.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alchemistsgarden.androi.databinding.ScoreListItemBinding
import com.alchemistsgarden.androi.fruits.models.FruitScore

class FruitsScoreAdapter : RecyclerView.Adapter<FruitsScoreAdapter.ViewHolder>() {

    var fruitsScore = emptyList<FruitScore>()

    inner class ViewHolder(private val binding: ScoreListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(score: FruitScore) {
            with(binding) {
                fruitsCount.text = score.count.toString()
                fruitImage.setImageResource(score.image)
                fruitScore.text = score.roundedScore
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ScoreListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = fruitsScore.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(fruitsScore[position])
    }



}