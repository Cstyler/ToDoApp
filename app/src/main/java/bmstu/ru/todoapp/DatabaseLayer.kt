package bmstu.ru.todoapp

import android.util.Log
import bmstu.ru.todoapp.entities.InListNote
import bmstu.ru.todoapp.entities.NoteName
import java.util.*
import java.text.SimpleDateFormat


object DatabaseLayer {
    private const val TAG = "DatabaseLayer"

    fun getInNoteNames(): Array<NoteName> {
        val noteNames = Array(20) { i ->
            NoteName(i, "In$i")
        }
        return noteNames
    }

    fun getNextActionsNames(): Array<NoteName> {
        val noteNames = arrayOf(
            NoteName(1, "NextAction1"),
            NoteName(2, "NextAction2"),
            NoteName(3, "NextAction3")
        )
        return noteNames
    }

    fun getWaitingForNoteNames(): Array<NoteName> {
        val noteNames = arrayOf(
            NoteName(1, "WaitingFor1"),
            NoteName(2, "WaitingFor2"),
            NoteName(3, "WaitingFor3")
        )
        return noteNames
    }

    fun getSomedayNoteNames(): Array<NoteName> {
        val noteNames = arrayOf(
            NoteName(1, "Someday1"),
            NoteName(2, "Someday2"),
            NoteName(3, "Someday3")
        )
        return noteNames
    }

    fun getCalendarNoteNames(): Array<NoteName> {
        val noteNames = arrayOf(
            NoteName(1, "Calendar1"),
            NoteName(2, "Calendar2"),
            NoteName(3, "Calendar3")
        )
        return noteNames
    }

    fun getInListNoteById(id: Int): InListNote {
        val c = Calendar.getInstance()
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val formattedDate = df.format(c.time)
        Log.i(TAG, "Date: $formattedDate")
        Log.i(TAG, "Date as int: ${c.time}")
        return InListNote("name$id", "content$id", c.time)
    }
}