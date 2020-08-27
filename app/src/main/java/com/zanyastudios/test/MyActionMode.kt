package com.zanyastudios.test

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.RecyclerView

class MyActionMode(
    val activity: MainActivity,
    recyclerView1: RecyclerView,
    recyclerView2: RecyclerView,
    private val adapter1: PostsAdapter,
    private val adapter2: PostsAdapter
) : ActionMode.Callback {
    private var selectedPostItems: MutableList<PostItem> = mutableListOf()
    private var selectedPostItems2: MutableList<PostItem> = mutableListOf()
    private var actionMode: ActionMode? = null
    private var tracker: SelectionTracker<PostItem>? = null
    private var tracker2: SelectionTracker<PostItem>? = null

    init {
        tracker = SelectionTracker.Builder<PostItem>(
            "mySelection",
            recyclerView1,
            MyItemKeyProvider(adapter1),
            MyItemDetailsLookup(recyclerView1),
            StorageStrategy.createParcelableStorage(PostItem::class.java)
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        adapter1.tracker = tracker

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
                                activity.startSupportActionMode(this@MyActionMode)
                            actionMode?.title =
                                "${selectedPostItems.size + selectedPostItems2.size}"
                        }
                    }
                }
            })

        tracker2 = SelectionTracker.Builder<PostItem>(
            "mySelection2",
            recyclerView2,
            MyItemKeyProvider(adapter2),
            MyItemDetailsLookup(recyclerView2),
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
                                activity.startSupportActionMode(this@MyActionMode)
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
                Toast.makeText(
                    activity,
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
            adapter1.tracker?.select(PostItem("", 1))
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
        if (selectedPostItems.size > 0 || selectedPostItems2.size > 0) {
            adapter1.tracker?.clearSelection()
            adapter1.notifyDataSetChanged()
            adapter2.tracker?.clearSelection()
            adapter2.notifyDataSetChanged()
        }
        actionMode = null
    }
}