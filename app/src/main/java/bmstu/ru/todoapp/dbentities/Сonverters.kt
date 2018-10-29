package bmstu.ru.todoapp.dbentities

import android.arch.persistence.room.TypeConverter
import bmstu.ru.todoapp.entities.CustomDate
import java.util.*


class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromTimestampToCustomDate(value: Long?): CustomDate? {
        return value?.let {
            val d = Date(it)
            CustomDate(d.year, d.month, d.day, d.hours, d.minutes)
        }
    }

    @TypeConverter
    fun customDateToTimestamp(date: CustomDate?): Long? {
        return Calendar.getInstance().time.time
    }
}