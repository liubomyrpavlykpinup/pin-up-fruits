package com.alchemistsgarden.androi.fruits.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.alchemistsgarden.androi.R
import com.alchemistsgarden.androi.databinding.FruitItemBinding
import com.alchemistsgarden.androi.fruits.models.FruitCell

class FruitImageAdapter : BaseAdapter() {

    var fruits: List<FruitCell> = emptyList()

    override fun getCount(): Int {
        return fruits.size
    }

    override fun getItem(position: Int): Any {
        return fruits[position]
    }

    override fun getItemId(position: Int): Long {
        return fruits[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val gridView: View = convertView ?: LayoutInflater.from(parent.context)
            .inflate(R.layout.fruit_item, parent, false)

        return FruitItemBinding.bind(gridView).apply {
            fruitImage.setImageResource(fruits[position].imageRes)
        }.root
    }



}