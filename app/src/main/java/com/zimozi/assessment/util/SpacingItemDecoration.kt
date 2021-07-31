package com.zimozi.assessment.util

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class SpacingItemDecoration(
    @DimenRes
    private val spaceHeight: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val offset = view.resources.getDimensionPixelSize(spaceHeight)
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = offset
            }
            bottom = offset
        }
    }
}