package com.zanyastudios.test

import androidx.recyclerview.selection.ItemKeyProvider

class MyItemKeyProvider(private val adapter: PostsAdapter) :
    ItemKeyProvider<PostItem>(SCOPE_CACHED) {
    override fun getKey(position: Int): PostItem? {
        return adapter.getItem(position)
    }

    override fun getPosition(key: PostItem): Int {
        return adapter.getPosition(key.name!!)
    }
}