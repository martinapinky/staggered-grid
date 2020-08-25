package com.zanyastudios.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val postsRecyclerView: RecyclerView = findViewById(R.id.postsRecyclerView)
//        postsRecyclerView.layoutManager =
//            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        postsRecyclerView.layoutManager = LinearLayoutManager(this)

        val postItems: MutableList<PostItem> = mutableListOf()
        postItems.add(PostItem(R.drawable.leeminho))
        postItems.add(PostItem(R.drawable.leejongsuk))
        postItems.add(PostItem(R.drawable.chaeunwoo))
        postItems.add(PostItem(R.drawable.seokangjoon))
        postItems.add(PostItem(R.drawable.kimsoohyun))
        postItems.add(PostItem(R.drawable.parkseojoon))
        postItems.add(PostItem(R.drawable.seoinguk))
        postItems.add(PostItem(R.drawable.jichangwook))
        postItems.add(PostItem(R.drawable.yooseungho))
        postItems.add(PostItem(R.drawable.leeseunggi))

        val adapter = PostsAdapter(this, postItems)
        postsRecyclerView.adapter = adapter

        val tracker = SelectionTracker.Builder<Long>(
            "mySelection",
            postsRecyclerView,
            StableIdKeyProvider(postsRecyclerView),
            MyItemDetailsLookup(postsRecyclerView),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        adapter.tracker = tracker
    }
}