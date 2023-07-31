package com.alchemistsgarden.androi.fruits

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.GridView
import android.widget.ListAdapter
import androidx.annotation.DrawableRes
import com.alchemistsgarden.androi.fruits.adapters.FruitImageAdapter
import com.alchemistsgarden.androi.fruits.models.FruitCell

private data class MatchGameModel(
    val view: View,
    val position: PointF,
    val index: Int,
)

interface MatchListener {
    fun onMatch(count: Int, score: Double, @DrawableRes image: Int): Unit
}

class MatchFruitsGridView : GridView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val linePaint = Paint()

    private val movedItems = mutableListOf<MatchGameModel>()
    private val matches = mutableListOf<MatchGameModel>()

    private var gameBoard: Array<Array<FruitCell>>? = null

    private var matchListener: MatchListener? = null

    init {
        linePaint.apply {
            color = Color.WHITE
            linePaint.strokeWidth = 50f
        }
    }

    override fun setAdapter(adapter: ListAdapter) {
        super.setAdapter(adapter)

        gameBoard = Array(5) { row ->
            Array(5) { column ->
                (adapter as FruitImageAdapter).fruits[row * 5 + column]
            }
        }
    }

    fun setOnMatchListener(onMatchListener: MatchListener) {
        matchListener = onMatchListener
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        val x: Float = event.x
        val y: Float = event.y

        val position: Int = pointToPosition(x.toInt(), y.toInt())
        val child: View? = getChildAt(position - firstVisiblePosition)

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                movedItems.clear()
            }

            MotionEvent.ACTION_UP -> {
                if (areItemsGrouped()) {
                    val col = movedItems.first().index % 5
                    val row = if (movedItems.first().index < 5) 0 else movedItems.first().index / 5

                    matches.addAll(movedItems)
                    matchListener?.onMatch(
                        count = movedItems.size,
                        score = movedItems.size * 10.0,
                        image = gameBoard!![row][col].imageRes
                    )
                } else {
                    movedItems.forEach {
                        it.view.setBackgroundColor(Color.parseColor("#80000000"))
                    }
                }
            }

            MotionEvent.ACTION_MOVE -> {

                child?.let {
                    val centerX = child.left + child.width / 2
                    val centerY = child.top + child.height / 2

                    val centerPoint = PointF(centerX.toFloat(), centerY.toFloat())
                    val matchGameModel =
                        MatchGameModel(view = it, position = centerPoint, index = indexOfChild(it))

                    if (!matches.contains(matchGameModel) && !movedItems.contains(matchGameModel)) {
                        movedItems.add(matchGameModel)
                        it.setBackgroundColor(Color.parseColor("#80FF0606"))
                    }
                }

            }

            else -> return false
        }

        return true
    }

    private fun areItemsGrouped(): Boolean {
        if (movedItems.size < 3) {
            return false
        }

        var temp = movedItems.first()

        for (item in movedItems) {
            val tempCol = temp.index % 5
            val tempRow = if (temp.index < 5) 0 else temp.index / 5

            val col = item.index % 5
            val row = if (item.index < 5) 0 else item.index / 5

            val board = gameBoard!!

            if (board[row][col].imageRes == board[tempRow][tempCol].imageRes) {
                if (temp.position.x == item.position.x || temp.position.y == item.position.y) {
                    temp = item
                } else {
                    return false
                }
            } else {
                return false
            }
        }

        return true
    }

    companion object {
        private const val TAG = "CustomFruitsGridView"
    }
}