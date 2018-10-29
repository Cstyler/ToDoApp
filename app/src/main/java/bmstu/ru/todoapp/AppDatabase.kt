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
        WaitingForNoteDb::class,
        SomedayNoteDb::class,
        CalendarNoteDb::class
    ],
    version = 8
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun inListDao(): InListDao
    abstract fun nextActionsListDao(): NextActionsListDao
    abstract fun waitingForListDao(): WaitingForListDao
    abstract fun somedayListDao(): SomedayListDao
    abstract fun calendarListDao(): CalendarListDao
    abstract fun contextDao(): ContextDao
    abstract fun projectDao(): ProjectDao

}