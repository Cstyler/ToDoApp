package bmstu.ru.todoapp.dbentities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity
data class InListNote(
    @PrimaryKey
    var uid: Int = 0,

    @ColumnInfo
    var name: String,

    @ColumnInfo
    var content: String? = null,

    @ColumnInfo
    var context: Context? = null,

    @ColumnInfo
    var _1: String? = null,

    @ColumnInfo
    var _2: String? = null
)