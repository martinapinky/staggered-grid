package com.zanyastudios.test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView

class PostsAdapter internal constructor(
    private val context: Context,
    private val postItems: List<PostItem>
) :
    RecyclerView.Adapter<PostsAdapter.ListViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    var tracker: SelectionTracker<Long>? = null


    init {
        setHasStableIds(true)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val postImageView: RoundedImageView = itemView.findViewById(R.id.imagePost)
        private val container: LinearLayout = itemView.findViewById(R.id.linear_layout_container)

        fun setPostImage(postItem: PostItem, isActivated: Boolean = false) {
            postImageView.setImageResource(postItem.image)
            itemView.isActivated = isActivated
            if (isActivated) {
//                container.background = context.getDrawable(R.drawable.selected_item_background)
                container.background =
                    ContextCompat.getDrawable(context, R.drawable.selected_item_background)
            } else {
                container.background = null
            }

        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView = inflater.inflate(R.layout.post_item_container, parent, false)
        return ListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        tracker?.let {
            holder.setPostImage(postItems[position], it.isSelected(position.toLong()))
        }

    }

    override fun getItemCount(): Int {
        return postItems.size
    }

    override fun getItemId(position: Int): Long = position.toLong()
}