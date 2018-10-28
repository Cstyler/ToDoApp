package bmstu.ru.todoapp

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import bmstu.ru.todoapp.daos.ContextDao
import bmstu.ru.todoapp.daos.InListDao
import bmstu.ru.todoapp.dbentities.ContextDb
import bmstu.ru.todoapp.dbentities.Converters
import bmstu.ru.todoapp.dbentities.InListNoteDb


@Database(entities = [InListNoteDb::class, ContextDb::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun inListDao(): InListDao
    abstract fun contextDao(): ContextDao
}