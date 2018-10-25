package bmstu.ru.todoapp.dbentities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "User")
data class User(
    @PrimaryKey
    var uid: Int = 0,

    @ColumnInfo(name = "first_name")
    var firstName: String? = null,
    @ColumnInfo(name = "last_name")
    var lastName: String? = null
)