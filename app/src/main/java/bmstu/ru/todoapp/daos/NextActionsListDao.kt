package bmstu.ru.todoapp.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import bmstu.ru.todoapp.dbentities.InListNoteDb
import java.util.*


@Dao
interface NextActionsListDao {
    @Query("SELECT * FROM InListNoteDb")
    fun getAllFromDb(): List<InListNoteDb>

    @Query("SELECT * FROM InListNoteDb WHERE id = :id")
    fun getById(id: Int): InListNoteDb

    @Insert
    fun insert(word: InListNoteDb)

    @Query("DELETE FROM InListNoteDb WHERE id = :id")
    fun deleteById(id: Int)

    @Query("UPDATE InListNoteDb SET name=:name, content=:content, creationDate=:creationDate, updateDate=:updateDate WHERE id = :id")
    fun update(id: Int, name: String, content: String?, creationDate: Date, updateDate: Date)
}
