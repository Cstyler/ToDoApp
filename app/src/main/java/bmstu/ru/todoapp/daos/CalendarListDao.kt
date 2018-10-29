package bmstu.ru.todoapp.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import bmstu.ru.todoapp.dbentities.CalendarNoteDb
import bmstu.ru.todoapp.entities.CustomDate
import java.util.*


@Dao
interface CalendarListDao {
    @Query("SELECT * FROM CalendarNoteDb")
    fun getAllFromDb(): List<CalendarNoteDb>

    @Query("SELECT * FROM CalendarNoteDb WHERE contextId = :id")
    fun getByContext(id: Int): List<CalendarNoteDb>

    @Query("SELECT * FROM CalendarNoteDb WHERE projectId = :id")
    fun getByProject(id: Int): List<CalendarNoteDb>

    @Query("SELECT * FROM CalendarNoteDb WHERE projectId is null")
    fun getWithNoProject(): List<CalendarNoteDb>

    @Query("SELECT * FROM CalendarNoteDb WHERE id = :id")
    fun getById(id: Int): CalendarNoteDb

    @Insert
    fun insert(word: CalendarNoteDb): Long

    @Query("DELETE FROM CalendarNoteDb WHERE id = :id")
    fun deleteById(id: Int)

    @Query("UPDATE CalendarNoteDb SET name=:name, content=:content, creationDate=:creationDate, updateDate=:updateDate, doTime=:doTime, remindTime=:remindTime, projectId=:projectId, contextId=:contextId WHERE id = :id")
    fun update(
        id: Int,
        name: String,
        content: String?,
        creationDate: Date,
        updateDate: Date,
        doTime: CustomDate?,
        remindTime: CustomDate?,
        projectId: Int?,
        contextId: Int?
    )
}
