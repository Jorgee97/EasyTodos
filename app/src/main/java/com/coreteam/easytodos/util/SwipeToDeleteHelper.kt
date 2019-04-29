package com.coreteam.easytodos.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.coreteam.easytodos.R

open class SwipeToDeleteHelper(private val context: Context)
        : ItemTouchHelper.Callback() {

    var background: Drawable = ColorDrawable(Color.RED)
    var xMark: Drawable = ContextCompat.getDrawable(context, R.drawable.ic_baseline_delete_sweep_24px)!!
    var xMarkMargin: Int = 0
    var initiated : Boolean = false

    init {
        xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC)
        xMarkMargin = context.resources.getDimension(R.dimen.fab_margin).toInt()
        initiated = true
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT)
    }

    override fun getMoveThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return super.getMoveThreshold(viewHolder)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        if (viewHolder.adapterPosition == -1) return

        background.setBounds(
            itemView.right + dX.toInt() ,
            itemView.top,
            itemView.right,
            itemView.bottom
        )
        background.draw(c)

        // draw x mark
        val itemHeight = itemView.bottom - itemView.top
        val intrinsicWidth = xMark.intrinsicWidth
        val intrinsicHeight = xMark.intrinsicWidth

        val xMarkLeft = itemView.right - xMarkMargin - intrinsicWidth
        val xMarkRight = itemView.right - xMarkMargin
        val xMarkTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val xMarkBottom = xMarkTop + intrinsicHeight
        xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom)

        xMark.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // Implemented on the UI
    }
}