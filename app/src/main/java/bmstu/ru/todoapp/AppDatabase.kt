package bmstu.ru.todoapp

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import bmstu.ru.todoapp.daos.UserDao
import bmstu.ru.todoapp.dbentities.User


@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}