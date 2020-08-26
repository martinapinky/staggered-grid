package com.zanyastudios.test

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

class MyItemDetailsLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<PostItem>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<PostItem>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as PostsAdapter.ListViewHolder)
                .getItemDetails()
        }
        return null
    }
}