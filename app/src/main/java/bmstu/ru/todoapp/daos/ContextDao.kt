package bmstu.ru.todoapp.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import bmstu.ru.todoapp.dbentities.ContextDb
import bmstu.ru.todoapp.dbentities.InListNoteDb


@Dao
interface ContextDao {
    @Query("SELECT * FROM ContextDb")
    fun getAllFromDb(): List<ContextDb>

    @Query("SELECT * FROM ContextDb WHERE id = :id")
    fun getById(id: Int): ContextDb

    @Insert
    fun insert(word: ContextDb)

    @Query("DELETE FROM ContextDb WHERE id = :id")
    fun deleteById(id: Int)

    @Query("UPDATE ContextDb SET name=:name, content=:content WHERE id = :id")
    fun update(id: Int, name: String, content: String?)
}
