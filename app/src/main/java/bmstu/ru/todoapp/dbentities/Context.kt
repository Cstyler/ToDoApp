package bmstu.ru.todoapp.dbentities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity
data class Context(
    @PrimaryKey
    var uid: Int = 0,

    @ColumnInfo
    var name: String,

    @ColumnInfo
    var description: String? = null
)