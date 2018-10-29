package bmstu.ru.todoapp

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import bmstu.ru.todoapp.daos.*
import bmstu.ru.todoapp.dbentities.*


@Database(
    entities = [
        InListNoteDb::class,
        ContextDb::class,
        ProjectDb::class,
        NextActionsNoteDb::class,
        WaitingForNoteDb::class
    ],
    version = 7
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun inListDao(): InListDao
    abstract fun nextActionsDao(): NextActionsListDao
    abstract fun waitingForDao(): WaitingForListDao
    abstract fun contextDao(): ContextDao
    abstract fun projectDao(): ProjectDao

}