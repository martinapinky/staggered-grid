# staggered-grid
Android RecyclerView with Staggered Grid Layout Manager Example

# Getting Started
Add the following dependency to your `build.gradle` file:
```
dependencies {
    implementation 'com.google.android.material:material:1.3.0-alpha02'
    implementation 'com.makeramen:roundedimageview:2.3.0'
}
```
# XML
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
