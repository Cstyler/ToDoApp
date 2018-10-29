package bmstu.ru.todoapp.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import bmstu.ru.todoapp.dbentities.WaitingForNoteDb
import bmstu.ru.todoapp.entities.CustomDate
import java.util.*


@Dao
interface WaitingForListDao {
    @Query("SELECT * FROM WaitingForNoteDb")
    fun getAllFromDb(): List<WaitingForNoteDb>

    @Query("SELECT * FROM WaitingForNoteDb WHERE contextId = :id")
    fun getByContext(id: Int): List<WaitingForNoteDb>

    @Query("SELECT * FROM WaitingForNoteDb WHERE projectId = :id")
    fun getByProject(id: Int): List<WaitingForNoteDb>

    @Query("SELECT * FROM WaitingForNoteDb WHERE projectId is null")
    fun getWithNoProject(): List<WaitingForNoteDb>

    @Query("SELECT * FROM WaitingForNoteDb WHERE id = :id")
    fun getById(id: Int): WaitingForNoteDb

    @Insert
    fun insert(word: WaitingForNoteDb): Long

    @Query("DELETE FROM WaitingForNoteDb WHERE id = :id")
    fun deleteById(id: Int)

    @Query("UPDATE WaitingForNoteDb SET name=:name, content=:content, creationDate=:creationDate, updateDate=:updateDate, waitingTime=:waitingTime, remindTime=:remindTime, projectId=:projectId, contextId=:contextId WHERE id = :id")
    fun update(
        id: Int,
        name: String,
        content: String?,
        creationDate: Date,
        updateDate: Date,
        waitingTime: CustomDate?,
        remindTime: CustomDate?,
        projectId: Int?,
        contextId: Int?
    )
}
