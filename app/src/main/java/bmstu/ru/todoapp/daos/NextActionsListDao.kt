package bmstu.ru.todoapp.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import bmstu.ru.todoapp.dbentities.NextActionsNoteDb
import bmstu.ru.todoapp.entities.CustomDate
import java.util.*


@Dao
interface NextActionsListDao {
    @Query("SELECT * FROM NextActionsNoteDb")
    fun getAllFromDb(): List<NextActionsNoteDb>

    @Query("SELECT * FROM NextActionsNoteDb WHERE id = :id")
    fun getById(id: Int): NextActionsNoteDb

    @Query("SELECT * FROM NextActionsNoteDb WHERE contextId = :id")
    fun getByContext(id: Int): List<NextActionsNoteDb>

    @Query("SELECT * FROM NextActionsNoteDb WHERE projectId = :id")
    fun getByProject(id: Int): List<NextActionsNoteDb>

    @Query("SELECT * FROM NextActionsNoteDb WHERE projectId is null")
    fun getWithNoProject(): List<NextActionsNoteDb>

    @Query("SELECT * FROM NextActionsNoteDb WHERE deadline is not null")
    fun getWithDeadline(): List<NextActionsNoteDb>

    @Query("SELECT * FROM NextActionsNoteDb WHERE deadline is null")
    fun getWithNoDeadline(): List<NextActionsNoteDb>

    @Insert
    fun insert(word: NextActionsNoteDb): Long

    @Query("DELETE FROM NextActionsNoteDb WHERE id = :id")
    fun deleteById(id: Int)

    @Query("UPDATE NextActionsNoteDb SET name=:name, content=:content, creationDate=:creationDate, updateDate=:updateDate, priority=:priority, deadline=:deadline, remindTime=:remindTime, projectId=:projectId, contextId=:contextId WHERE id = :id")
    fun update(
        id: Int,
        name: String,
        content: String?,
        creationDate: Date,
        updateDate: Date,
        priority: Int,
        deadline: CustomDate?,
        remindTime: CustomDate?,
        projectId: Int?,
        contextId: Int?
    )
}
