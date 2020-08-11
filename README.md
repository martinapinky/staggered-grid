# staggered-grid
Android RecyclerView with Staggered Grid Layout Manager Example

# Getting Started
Create a new Android Studio project with the empty activity template.
Add the following dependency to your `build.gradle` file:
```
dependencies {
    implementation 'com.google.android.material:material:1.3.0-alpha02'
    implementation 'com.makeramen:roundedimageview:2.3.0'
}
```
## XML
Add the following to your `activity_main.xml` file:
```
<androidx.recyclerview.widget.RecyclerView
        android:id="@+id/postsRecyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:clipToPadding="false"
        android:paddingStart="0dp"
        android:paddingEnd="15dp"
        android:paddingBottom="15dp" />
```
### RecyclerView Item
Create an XML file named `post_item_container.xml` with the following:
```
<?xml version="1.0" encoding="utf-8"?>
<com.makeramen.roundedimageview.RoundedImageView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/imagePost"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginStart="15dp"
    android:layout_marginTop="15dp"
    android:adjustViewBounds="true"
    app:riv_corner_radius="12dp">
</com.makeramen.roundedimageview.RoundedImageView>
```
## Kotlin
Create a data class called `PostItem.kt` with the following:
```
data class PostItem(var image: Int)
```
Create a RecyclerView Adapter called `Posts Adapter` with the following:
```

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView

class PostsAdapter internal constructor(
    private val context: Context,
    private val postItems: List<PostItem>
) :
    RecyclerView.Adapter<PostsAdapter.ListViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postImageView: RoundedImageView = itemView.findViewById(R.id.imagePost)

        fun setPostImage(postItem: PostItem) {
            postImageView.setImageResource(postItem.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView = inflater.inflate(R.layout.post_item_container, parent, false)
        return ListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.setPostImage(postItems[position])
    }

    override fun getItemCount(): Int {
        return postItems.size
    }


}
```
Add the following to your `MainActivity.kt` file:
```
val postsRecyclerView: RecyclerView = findViewById(R.id.postsRecyclerView)
        postsRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

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

        postsRecyclerView.adapter = PostsAdapter(this, postItems)
```
### Screenshots
![Screen Shot 1](https://github.com/martinapinky/staggered-grid/blob/master/app/src/main/res/drawable/screenshot_1.png?raw=true)
![Screen Shot 2](https://github.com/martinapinky/staggered-grid/blob/master/app/src/main/res/drawable/screenshot_2.png?raw=true)
