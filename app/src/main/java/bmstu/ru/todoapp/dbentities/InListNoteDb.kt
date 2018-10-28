package bmstu.ru.todoapp.dbentities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
data class InListNoteDb(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var name: String,
    @ColumnInfo
    var content: String? = null,
    @ColumnInfo()
    var creationDate: Date,
    @ColumnInfo
    var updateDate: Date
) {
    @Ignore
    constructor(name: String, content: String?, creationDate: Date, updateDate: Date)
            : this(0, name, content, creationDate, updateDate)
}
