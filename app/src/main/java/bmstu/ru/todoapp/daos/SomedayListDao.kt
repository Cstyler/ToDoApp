package bmstu.ru.todoapp.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import bmstu.ru.todoapp.dbentities.SomedayNoteDb
import bmstu.ru.todoapp.entities.CustomDate
import java.util.*


@Dao
interface SomedayListDao {
    @Query("SELECT * FROM SomedayNoteDb")
    fun getAllFromDb(): List<SomedayNoteDb>

    @Query("SELECT * FROM SomedayNoteDb WHERE id = :id")
    fun getById(id: Int): SomedayNoteDb

    @Query("SELECT * FROM SomedayNoteDb WHERE contextId = :id")
    fun getByContext(id: Int): List<SomedayNoteDb>

    @Insert
    fun insert(word: SomedayNoteDb): Long

    @Query("DELETE FROM SomedayNoteDb WHERE id = :id")
    fun deleteById(id: Int)

    @Query("UPDATE SomedayNoteDb SET name=:name, content=:content, creationDate=:creationDate, updateDate=:updateDate, contextId=:contextId WHERE id = :id")
    fun update(
        id: Int,
        name: String,
        content: String?,
        creationDate: Date,
        updateDate: Date,
        contextId: Int?
    )
}
