package com.loc.newsapp.domain.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Source(
    var id: String?,
    var name: String?
): Parcelable