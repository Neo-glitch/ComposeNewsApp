package com.loc.newsapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

// normal we can have a mapper class to convert from article to article entity class in data layer
// todo annonate Entity class in data layer

@Parcelize
@Entity
data class Article(
    var source: Source?,
    var author: String?,
    var title: String?,
    var description: String?,
    @PrimaryKey
    var url: String,
    var urlToImage: String?,
    var publishedAt: String?,
    var content: String?
): Parcelable