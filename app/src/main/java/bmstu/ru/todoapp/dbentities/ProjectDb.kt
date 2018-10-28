package bmstu.ru.todoapp.dbentities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity
data class ProjectDb(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var name: String,
    @ColumnInfo
    var content: String? = null
) {
    @Ignore
    constructor(name: String, content: String?)
            : this(0, name, content)
}
