package bmstu.ru.todoapp.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import bmstu.ru.todoapp.dbentities.ProjectDb


@Dao
interface ProjectDao {
    @Query("SELECT * FROM ProjectDb")
    fun getAllFromDb(): List<ProjectDb>

    @Query("SELECT * FROM ProjectDb WHERE id = :id")
    fun getById(id: Int): ProjectDb

    @Insert
    fun insert(word: ProjectDb)

    @Query("DELETE FROM ProjectDb WHERE id = :id")
    fun deleteById(id: Int)

    @Query("UPDATE ProjectDb SET name=:name, content=:content WHERE id = :id")
    fun update(id: Int, name: String, content: String?)
}
