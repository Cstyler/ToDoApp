package bmstu.ru.todoapp.dbentities

import android.arch.persistence.room.*
import bmstu.ru.todoapp.entities.CustomDate
import java.util.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ProjectDb::class,
            parentColumns = ["id"],
            childColumns = ["projectId"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = ContextDb::class,
            parentColumns = ["id"],
            childColumns = ["contextId"],
            onDelete = ForeignKey.SET_NULL
        )]
)
data class NextActionsNoteDb(
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
    var priority: Int,
    @ColumnInfo
    var deadline: CustomDate?,
    @ColumnInfo
    var remindTime: CustomDate?,
    @ColumnInfo
    var contextId: Int?,
    @ColumnInfo
    var projectId: Int?
) {
    @Ignore
    constructor(
        name: String,
        content: String?,
        creationDate: Date,
        updateDate: Date,
        priority: Int,
        deadline: CustomDate?,
        remindTime: CustomDate?,
        contextId: Int?,
        projectId: Int?
    )
            : this(
        0,
        name,
        content,
        creationDate,
        updateDate,
        priority,
        deadline,
        remindTime,
        contextId,
        projectId
    )
}
