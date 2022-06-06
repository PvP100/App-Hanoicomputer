package com.example.utt.hnccomputer.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecoration(
    private val space: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = space
        outRect.right = space
        outRect.left = 0
        outRect.bottom = 0

//        if (parent.getChildLayoutPosition(view) % 2 == 0) {
//            outRect.left = space
//        } else {
//            outRect.left = 0
//        }
    }

}