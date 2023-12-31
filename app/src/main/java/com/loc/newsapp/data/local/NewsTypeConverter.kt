package com.loc.newsapp.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.loc.newsapp.domain.model.Source


// do this if you want to add the type converter myself when creating the room db
// rather than allowing room do it by default, useful when type converters has dependencies
@ProvidedTypeConverter
class NewsTypeConverter {

    @TypeConverter
    fun sourceToString(source: Source): String{
        return "${source.id},${source.name}"
    }

    @TypeConverter
    fun stringToSource(source: String): Source{
        return source.split(",").let { sourceArray ->
            Source(
                id = sourceArray[0],
                name = sourceArray[1]
            )
        }
    }
}