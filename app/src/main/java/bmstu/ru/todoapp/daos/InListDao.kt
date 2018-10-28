package bmstu.ru.todoapp.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import bmstu.ru.todoapp.dbentities.InListNoteDb


@Dao
interface InListDao {
    @Query("SELECT * FROM InListNoteDb")
    fun getAllFromDb(): List<InListNoteDb>

    @Insert
    fun insert(word: InListNoteDb)

    @Delete
    fun delete(user: InListNoteDb)
}
