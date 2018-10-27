package bmstu.ru.todoapp

import android.util.Log
import bmstu.ru.todoapp.entities.*
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
        val time = cal.time
        return InListNote("INname$id", "INcontent$id", time, time)
    }

    fun updateInListEdit(id: Int, note: InListNote) {
        Log.i(TAG, "Update note: id: $id, $note")
    }

    fun putInListEdit(note: InListNote) {
        Log.i(TAG, "Create note: id: $note")
    }

    fun getNextActionsListNoteById(id: Int): NextActionsListNote {
        val cal = Calendar.getInstance()
        val time = cal.time
        val remindeTime = MyDate(2018, 9, 25, 12, 10)
//        val remindTime = null
        val deadlineTime = MyDate(2018, 11, 14, 13, 0)
//        val deadlineTime = null
        val contextId = 1
//        val contextId = null
        val projectId = 1
//        val projectId = null
        return NextActionsListNote(
            "NAname$id", "NAcontent$id",
            time, time,
            2,
            deadlineTime, remindeTime,
            contextId, projectId
        )
    }

    fun getWaitingForListNoteById(id: Int): WaitingForListNote {
        val cal = Calendar.getInstance()
        val time = cal.time
        val remindeTime = MyDate(2018, 9, 25, 12, 10)
        val remindTime = null
//        val waitingTime = MyDate(2018, 11, 14, 13, 0)
        val waitingTime = null
//        val contextId = 1
        val contextId = null
//        val projectId = 1
        val projectId = null
        return WaitingForListNote(
            "NAname$id", "NAcontent$id",
            time, time,
            waitingTime, remindeTime,
            contextId, projectId
        )
    }

    fun updateNextActionsListEdit(id: Int, note: NextActionsListNote) {
        Log.i(TAG, "Update note: id: $id,\n $note")
    }

    fun updateWaitingForListEdit(id: Int, note: WaitingForListNote) {
        Log.i(TAG, "Update note: id: $id,\n $note")
    }


    fun putNextActionNote(note: NextActionsListNote): Int {
        Log.i(TAG, "Create note: \n$note")
        return 0
    }

    fun getProjectNames(): List<ProjectName> {
        val projects = Array(5) {
            ProjectName(it, "Proj $it")
        }.toList()
        return projects
    }

    fun getProjectNameById(id: Int): String {
        return "Proj $id"
    }

    fun getContextNames(): List<ContextName> {
        val contexts = Array(5) {
            ContextName(it, "Context $it")
        }.toList()
        return contexts
    }

    fun getContextNameById(id: Int): String {
        return "Context $id"
    }

    fun deleteInNoteById(id: Int) {

    }

    fun deleteNextActionsNoteById(id: Int) {

    }

    fun deleteWaitingForNoteById(id: Int) {

    }

    fun deleteSomedayNoteById(id: Int) {

    }

    fun deleteCalendarNoteById(id: Int) {

    }
}
