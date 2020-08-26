package com.zanyastudios.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.view.ActionMode
import androidx.core.content.ContextCompat
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MainActivity : AppCompatActivity(), ActionMode.Callback {

    private var selectedPostItems: MutableList<PostItem> = mutableListOf()
    private var selectedPostItems2: MutableList<PostItem> = mutableListOf()
    private var actionMode: ActionMode? = null
    private lateinit var adapter: PostsAdapter
    private lateinit var adapter2: PostsAdapter
    private var tracker: SelectionTracker<PostItem>? = null
    private var tracker2: SelectionTracker<PostItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val postsRecyclerView: RecyclerView = findViewById(R.id.postsRecyclerView)
        postsRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        val postsRecyclerView2: RecyclerView = findViewById(R.id.postsRecyclerView2)
        postsRecyclerView2.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        val postItems: MutableList<PostItem> = mutableListOf()
        postItems.add(PostItem("Lee Minho", R.drawable.leeminho))
        postItems.add(PostItem("Lee Jong Suk", R.drawable.leejongsuk))
        postItems.add(PostItem("Cha Eun Woo", R.drawable.chaeunwoo))
        postItems.add(PostItem("Seo Kang Joon", R.drawable.seokangjoon))
        postItems.add(PostItem("Kim Soo Hyun", R.drawable.kimsoohyun))
        postItems.add(PostItem("Park Seo Joon", R.drawable.parkseojoon))
        postItems.add(PostItem("Seo In Guk", R.drawable.seoinguk))
        postItems.add(PostItem("Ji Chang Wook", R.drawable.jichangwook))
        postItems.add(PostItem("Yoo Seung Ho", R.drawable.yooseungho))
        postItems.add(PostItem("Lee Seung Gi", R.drawable.leeseunggi))

        adapter = PostsAdapter(this, postItems)
        postsRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        tracker = SelectionTracker.Builder<PostItem>(
            "mySelection",
            postsRecyclerView,
            MyItemKeyProvider(adapter),
            MyItemDetailsLookup(postsRecyclerView),
            StorageStrategy.createParcelableStorage(PostItem::class.java)
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        adapter.tracker = tracker

        tracker?.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    tracker?.let {
                        selectedPostItems = it.selection.toMutableList()
                        selectedPostItems.remove(PostItem("", 1))
                        if (selectedPostItems.isEmpty() && selectedPostItems2.isEmpty()) {
                            actionMode?.finish()
                        } else {
                            if (actionMode == null) actionMode =
                                startSupportActionMode(this@MainActivity)
                            actionMode?.title =
                                "${selectedPostItems.size + selectedPostItems2.size}"
                        }
                    }
                }
            })

        // Recyclerview 2
        adapter2 = PostsAdapter(this, postItems)
        postsRecyclerView2.adapter = adapter2
        adapter2.notifyDataSetChanged()

        tracker2 = SelectionTracker.Builder<PostItem>(
            "mySelection2",
            postsRecyclerView2,
            MyItemKeyProvider(adapter2),
            MyItemDetailsLookup(postsRecyclerView2),
            StorageStrategy.createParcelableStorage(PostItem::class.java)
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        adapter2.tracker = tracker2

        tracker2?.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    tracker2?.let {
                        selectedPostItems2 = it.selection.toMutableList()
                        selectedPostItems2.remove(PostItem("", 1))
                        if (selectedPostItems2.isEmpty() && selectedPostItems.isEmpty()) {
                            actionMode?.finish()
                        } else {
                            if (actionMode == null) actionMode =
                                startSupportActionMode(this@MainActivity)
                            actionMode?.title =
                                "${selectedPostItems2.size + selectedPostItems.size}"
                        }
                    }
                }
            })
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_view_delete -> {
                val string = ""
                for (selectedItem in selectedPostItems) {
                    string + "\n${selectedItem.name}"
                }
                Toast.makeText(
                    this,
                    selectedPostItems.toString() + "\n${selectedPostItems2.toString()}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        return true
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        // Hack for selecting from both Recyclerviews
        if (selectedPostItems.size > 0) {
            adapter2.tracker?.select(PostItem("", 1))
        }
        if (selectedPostItems2.size > 0) {
            adapter.tracker?.select(PostItem("", 1))
        }


        mode?.let {
            val inflater: MenuInflater = it.menuInflater
            inflater.inflate(R.menu.action_mode_menu, menu)
            return true
        }
        return false
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        if (selectedPostItems.size > 0 && selectedPostItems2.size > 0) {
            adapter.tracker?.clearSelection()
            adapter.notifyDataSetChanged()
            adapter2.tracker?.clearSelection()
            adapter2.notifyDataSetChanged()
        }
        actionMode = null
    }
}