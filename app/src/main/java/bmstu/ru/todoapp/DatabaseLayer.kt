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
        val noteNames = Array(3) {
            NoteName(it, "NextAction$it")
        }
        return noteNames
    }

    fun getNextActionsNamesFilteredByContext(contextId: Int): Array<NoteName> {
        val noteNames = Array(3) {
            NoteName(it, "NextAction$it. Context $contextId")
        }
        return noteNames
    }

    fun getNextActionsNamesFilteredByProject(projectId: Int): Array<NoteName> {
        val noteNames = Array(3) {
            NoteName(it, "NextAction$it. Proj $projectId")
        }
        return noteNames
    }

    fun getNextActionsNamesWithNoProject(): Array<NoteName> {
        val noteNames = Array(3) {
            NoteName(it, "NextAction$it. NoProj")
        }
        return noteNames
    }

    fun getNextActionsNamesWithNoDeadline(): Array<NoteName> {
        val noteNames = Array(3) {
            NoteName(it, "NextAction$it. NoDeadline")
        }
        return noteNames
    }

    fun getNextActionsNamesWithDeadline(): Array<NoteName> {
        val noteNames = Array(3) {
            NoteName(it, "NextAction$it. WithDeadline")
        }
        return noteNames
    }

    fun getWaitingForNoteNames(): Array<NoteName> {
        val noteNames = Array(3) {
            NoteName(it, "WaitingFor$it")
        }
        return noteNames
    }

    fun getWaitingForNamesFilteredByContext(contextId: Int): Array<NoteName> {
        val noteNames = Array(3) {
            NoteName(it, "WaitingFor$it. Context $contextId")
        }
        return noteNames
    }

    fun getWaitingForNamesFilteredByProject(projectId: Int): Array<NoteName> {
        val noteNames = Array(3) {
            NoteName(it, "WaitingFor$it. Proj $projectId")
        }
        return noteNames
    }

    fun getWaitingForNamesWithNoProject(): Array<NoteName> {
        val noteNames = Array(3) {
            NoteName(it, "WaitingFor$it. NoProj")
        }
        return noteNames
    }

    fun getSomedayNoteNames(): Array<NoteName> {
        val noteNames = Array(3) {
            NoteName(it, "Someday$it")
        }
        return noteNames
    }

    fun getSomedayNamesFilteredByContext(contextId: Int): Array<NoteName> {
        val noteNames = Array(3) {
            NoteName(it, "Someday$it. Context $contextId")
        }
        return noteNames
    }


    fun getCalendarNoteNames(): Array<NoteName> {
        val noteNames = Array(3) {
            NoteName(it, "Calendar$it")
        }
        return noteNames
    }

    fun getCalendarNamesFilteredByContext(contextId: Int): Array<NoteName> {
        val noteNames = Array(3) {
            NoteName(it, "Calendar$it. Context $contextId")
        }
        return noteNames
    }

    fun getCalendarNamesFilteredByProject(projectId: Int): Array<NoteName> {
        val noteNames = Array(3) {
            NoteName(it, "Calendar$it. Proj $projectId")
        }
        return noteNames
    }

    fun getCalendarNamesWithNoProject(): Array<NoteName> {
        val noteNames = Array(3) {
            NoteName(it, "Calendar$it. NoProj")
        }
        return noteNames
    }

    fun getInListNoteById(id: Int): InListNote {
        val cal = Calendar.getInstance()
        val time = cal.time
        return InListNote("INname$id", "INcontent$id", time, time)
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
//        val doTime = MyDate(2018, 11, 14, 13, 0)
        val waitingTime = null
//        val contextId = 1
        val contextId = null
        val projectId = 1
//        val projectId = null
        return WaitingForListNote(
            "NAname$id", "NAcontent$id",
            time, time,
            waitingTime, remindeTime,
            contextId, projectId
        )
    }

    fun getSomedayListNoteById(id: Int): SomedayListNote {
        val cal = Calendar.getInstance()
        val time = cal.time
        val contextId = null
        return SomedayListNote(
            "NAname$id", "NAcontent$id",
            time, time,
            contextId
        )
    }

    fun getCalendarListNoteById(id: Int): CalendarListNote {
        val cal = Calendar.getInstance()
        val time = cal.time
        val remindeTime = MyDate(2018, 9, 25, 12, 10)
        val remindTime = null
//        val doTime = MyDate(2018, 11, 14, 13, 0)
        val doTime = null
//        val contextId = 1
        val contextId = null
        val projectId = 1
//        val projectId = null
        return CalendarListNote(
            "NAname$id", "NAcontent$id",
            time, time,
            doTime, remindeTime,
            contextId, projectId
        )
    }


    fun updateInListNote(id: Int, note: InListNote) {
        Log.i(TAG, "Update note: id: $id, $note")
    }

    fun updateNextActionsListEdit(id: Int, note: NextActionsListNote) {
        Log.i(TAG, "Update note: id: $id,\n $note")
    }

    fun updateWaitingForListEdit(id: Int, note: WaitingForListNote) {
        Log.i(TAG, "Update note: id: $id,\n $note")
    }

    fun updateSomedayListEdit(id: Int, note: SomedayListNote) {
        Log.i(TAG, "Update note: id: $id,\n $note")
    }

    fun updateCalendarListEdit(id: Int, note: CalendarListNote) {
        Log.i(TAG, "Update note: id: $id,\n $note")
    }

    fun putInListNote(note: InListNote) {
        Log.i(TAG, "Create note: id: $note")
    }

    fun putNextActionNote(note: NextActionsListNote): Int {
        Log.i(TAG, "Create note: \n$note")
        return 0
    }

    fun putWaitingForNote(note: WaitingForListNote): Int {
        Log.i(TAG, "Create note: \n$note")
        return 0
    }

    fun putCalendarNote(note: CalendarListNote): Int {
        Log.i(TAG, "Create note: \n$note")
        return 0
    }

    fun putSomedayNote(note: SomedayListNote): Int {
        Log.i(TAG, "Create note: \n$note")
        return 0
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
}
