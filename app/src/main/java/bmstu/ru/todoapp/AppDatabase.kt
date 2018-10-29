package bmstu.ru.todoapp

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import bmstu.ru.todoapp.daos.ContextDao
import bmstu.ru.todoapp.daos.InListDao
import bmstu.ru.todoapp.daos.NextActionsListDao
import bmstu.ru.todoapp.daos.ProjectDao
import bmstu.ru.todoapp.dbentities.*


@Database(
    entities = [
        InListNoteDb::class,
        ContextDb::class,
        ProjectDb::class,
        NextActionsNoteDb::class
    ],
    version = 6
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun inListDao(): InListDao
    abstract fun nextActionsDao(): NextActionsListDao
    abstract fun contextDao(): ContextDao
    abstract fun projectDao(): ProjectDao
}