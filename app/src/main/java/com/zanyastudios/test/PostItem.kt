package com.zanyastudios.test

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostItem(val name: String?, val image: Int) : Parcelable

