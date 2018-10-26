package bmstu.ru.todoapp

import android.util.Log
import bmstu.ru.todoapp.entities.InListNote
import bmstu.ru.todoapp.entities.NextActionsListNote
import bmstu.ru.todoapp.entities.NoteName
import java.util.*

object DatabaseLayer {
    private const val TAG = "DatabaseLayer"

    fun getInNoteNames(): Array<NoteName> {
        val noteNames = Array(20) {
            NoteName(it, "In$it")
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
        val cal = Calendar.getInstance()
        Log.i(TAG, "Time: ${cal.time}")
        val time = cal.time
        return InListNote("name$id", "content$id", time, time)
    }

    fun updateInListEdit(id: Int, note: InListNote) {
        Log.i(TAG, "Update note: id: $id, $note")
    }

    fun putInListEdit(note: InListNote) {
        Log.i(TAG, "Create note: id: $note")
    }

    fun getNextActionsListNoteById(id: Int): NextActionsListNote {
        val cal = Calendar.getInstance()
        Log.i(TAG, "Time: ${cal.time}")
        val time = cal.time
        return NextActionsListNote(
            "name$id",
            "content$id",
            time,
            time,
            null,
            null,
            null,
            null,
            1
        )
    }

    fun updateNextActionsListEdit(id: Int, note: NextActionsListNote) {
        Log.i(TAG, "Update note: id: $id, $note")
    }

    fun putNextActionsListEdit(note: NextActionsListNote) {
        Log.i(TAG, "Create note: id: $note")
    }
}