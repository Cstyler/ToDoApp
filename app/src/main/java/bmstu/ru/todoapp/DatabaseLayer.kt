package bmstu.ru.todoapp

import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import android.util.Log
import bmstu.ru.todoapp.dbentities.ContextDb
import bmstu.ru.todoapp.dbentities.InListNoteDb
import bmstu.ru.todoapp.dbentities.NextActionsNoteDb
import bmstu.ru.todoapp.dbentities.ProjectDb
import bmstu.ru.todoapp.entities.*
import java.util.*

object DatabaseLayer {
    private const val TAG = "DatabaseLayer"
    private lateinit var db: AppDatabase

    fun initDatabase(context: AppCompatActivity) {
        db = Room
            .databaseBuilder(context, AppDatabase::class.java, "AppDatabase")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    fun getInNoteNames(): Array<NoteName> {
        val dao = db.inListDao()
        return dao.getAllFromDb().map {
            NoteName(it.id, it.name)
        }.toTypedArray()
    }

    fun getNextActionsNames(): Array<NoteName> {
        val dao = db.nextActionsDao()
        return dao.getAllFromDb().map {
            NoteName(it.id, it.name)
        }.toTypedArray()
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
        val dao = db.inListDao()
        val note = dao.getById(id)
        return InListNote(note.name, note.content, note.creationDate, note.updateDate)
    }

    fun getNextActionsListNoteById(id: Int): NextActionsListNote {
        val dao = db.nextActionsDao()
        val note = dao.getById(id)
        return NextActionsListNote(note.name,
            note.content, note.creationDate, note.updateDate,
            note.priority, note.deadline, note.remindTime,
            note.contextId, note.projectId)
    }

    fun getWaitingForListNoteById(id: Int): WaitingForListNote {
        val cal = Calendar.getInstance()
        val time = cal.time
        val remindeTime = CustomDate(2018, 9, 25, 12, 10)
        val remindTime = null
//        val doTime = CustomDate(2018, 11, 14, 13, 0)
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
        val remindeTime = CustomDate(2018, 9, 25, 12, 10)
        val remindTime = null
//        val doTime = CustomDate(2018, 11, 14, 13, 0)
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
        val dao = db.inListDao()
        dao.update(id, note.name, note.content, note.creationDate, note.updateDate)
    }

    fun updateNextActionsListEdit(id: Int, note: NextActionsListNote) {
        val dao = db.nextActionsDao()
        dao.update(id, note.name, note.content, note.creationDate, note.updateDate,
            note.priority, note.deadline, note.remindTime, note.projectId, note.contextId)
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
        val dao = db.inListDao()
        dao.insert(
            InListNoteDb(
                name = note.name,
                content = note.content,
                creationDate = note.creationDate,
                updateDate = note.updateDate
            )
        )
    }

    fun putNextActionNote(note: NextActionsListNote): Int {
        val dao = db.nextActionsDao()
        val id = dao.insert(
            NextActionsNoteDb(
                name = note.name,
                content = note.content,
                creationDate = note.creationDate,
                updateDate = note.updateDate,
                priority = note.priority,
                deadline = note.deadline,
                remindTime = note.remindTime,
                contextId = note.contextId,
                projectId = note.projectId
            )
        )
        return id.toInt()
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
        val dao = db.inListDao()
        dao.deleteById(id)
    }

    fun deleteNextActionsNoteById(id: Int) {
        val dao = db.nextActionsDao()
        dao.deleteById(id)
    }

    fun deleteWaitingForNoteById(id: Int) {

    }

    fun deleteSomedayNoteById(id: Int) {

    }


    fun deleteCalendarNoteById(id: Int) {

    }

    fun getProjectNames(): List<ProjectName> {
        val dao = db.projectDao()
        val projectList = dao.getAllFromDb()
        return projectList.map {
            ProjectName(it.id, it.name)
        }
    }

    fun getProjectNameById(id: Int): String {
        val dao = db.projectDao()
        val projectDb = dao.getById(id)
        return projectDb.name
    }

    fun getProjectById(id: Int): Project {
        val dao = db.projectDao()
        val projectDb = dao.getById(id)
        return Project(projectDb.name, projectDb.content)
    }

    fun deleteProjectById(id: Int) {
        val dao = db.projectDao()
        dao.deleteById(id)
    }

    fun updateProject(id: Int, project: Project) {
        val dao = db.projectDao()
        dao.update(id, project.name, project.content)
    }

    fun putProject(project: Project) {
        val projectDb = ProjectDb(name = project.name, content = project.content)
        val dao = db.projectDao()
        dao.insert(projectDb)
    }

    fun getContextNames(): List<ContextName> {
        val dao = db.contextDao()
        val contextList = dao.getAllFromDb()
        return contextList.map {
            ContextName(it.id, it.name)
        }
    }

    fun getContextNameById(id: Int): String {
        val dao = db.contextDao()
        val contextDb = dao.getById(id)
        return contextDb.name
    }

    fun getContextById(id: Int): Context {
        val dao = db.contextDao()
        val contextDb = dao.getById(id)
        return Context(contextDb.name, contextDb.content)
    }

    fun deleteContextById(id: Int) {
        val dao = db.contextDao()
        dao.deleteById(id)
    }

    fun updateContext(id: Int, context: Context) {
        val dao = db.contextDao()
        dao.update(id, context.name, context.content)
    }

    fun putContext(context: Context) {
        val contextDb = ContextDb(name = context.name, content = context.content)
        val dao = db.contextDao()
        dao.insert(contextDb)
    }
}
