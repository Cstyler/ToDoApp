package bmstu.ru.todoapp.dbentities

import android.arch.persistence.room.*
import java.util.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ContextDb::class,
            parentColumns = ["id"],
            childColumns = ["contextId"],
            onDelete = ForeignKey.SET_NULL
        )]
)
data class SomedayNoteDb(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var name: String,
    @ColumnInfo
    var content: String? = null,
    @ColumnInfo()
    var creationDate: Date,
    @ColumnInfo
    var updateDate: Date,
    @ColumnInfo
    var contextId: Int?
) {
    @Ignore
    constructor(
        name: String,
        content: String?,
        creationDate: Date,
        updateDate: Date,
        contextId: Int?
    )
            : this(
        0,
        name,
        content,
        creationDate,
        updateDate,
        contextId
    )
}
