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
            val cal = Calendar.getInstance()
            cal.time = d
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val date = cal.get(Calendar.DATE)
            val hour = cal.get(Calendar.HOUR)
            val minute = cal.get(Calendar.MINUTE)
            CustomDate(year, month, date, hour, minute)
        }
    }

    @TypeConverter
    fun customDateToTimestamp(date: CustomDate?): Long? {
        val cal = Calendar.getInstance()
        return date?.let {
            cal.set(
                it.year,
                it.month,
                it.day,
                it.hour,
                it.minute
            )
            cal.time.time
        }
    }
}