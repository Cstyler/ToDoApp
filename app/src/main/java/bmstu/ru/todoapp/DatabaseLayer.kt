package bmstu.ru.todoapp

import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import bmstu.ru.todoapp.dbentities.*
import bmstu.ru.todoapp.entities.*

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
        val dao = db.nextActionsListDao()
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
        val dao = db.waitingForListDao()
        return dao.getAllFromDb().map {
            NoteName(it.id, it.name)
        }.toTypedArray()
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
        val dao = db.somedayListDao()
        return dao.getAllFromDb().map {
            NoteName(it.id, it.name)
        }.toTypedArray()
    }

    fun getSomedayNamesFilteredByContext(contextId: Int): Array<NoteName> {
        val noteNames = Array(3) {
            NoteName(it, "Someday$it. Context $contextId")
        }
        return noteNames
    }

    fun getCalendarNoteNames(): Array<NoteName> {
        val dao = db.calendarListDao()
        return dao.getAllFromDb().map {
            NoteName(it.id, it.name)
        }.toTypedArray()
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
        val dao = db.nextActionsListDao()
        val note = dao.getById(id)
        return NextActionsListNote(
            note.name,
            note.content, note.creationDate, note.updateDate,
            note.priority, note.deadline, note.remindTime,
            note.contextId, note.projectId
        )
    }

    fun getWaitingForListNoteById(id: Int): WaitingForListNote {
        val dao = db.waitingForListDao()
        val note = dao.getById(id)
        return WaitingForListNote(
            note.name,
            note.content, note.creationDate, note.updateDate,
            note.waitingTime, note.remindTime,
            note.contextId, note.projectId
        )
    }

    fun getSomedayListNoteById(id: Int): SomedayListNote {
        val dao = db.somedayListDao()
        val note = dao.getById(id)
        return SomedayListNote(
            note.name, note.content,
            note.creationDate, note.updateDate, note.contextId
        )
    }

    fun getCalendarListNoteById(id: Int): CalendarListNote {
        val dao = db.calendarListDao()
        val note = dao.getById(id)
        return CalendarListNote(
            note.name,
            note.content, note.creationDate, note.updateDate,
            note.doTime, note.remindTime,
            note.contextId, note.projectId
        )
    }


    fun updateInListNote(id: Int, note: InListNote) {
        val dao = db.inListDao()
        dao.update(id, note.name, note.content, note.creationDate, note.updateDate)
    }

    fun updateNextActionsListEdit(id: Int, note: NextActionsListNote) {
        val dao = db.nextActionsListDao()
        dao.update(
            id, note.name, note.content, note.creationDate, note.updateDate,
            note.priority, note.deadline, note.remindTime, note.projectId, note.contextId
        )
    }

    fun updateWaitingForListEdit(id: Int, note: WaitingForListNote) {
        val dao = db.waitingForListDao()
        dao.update(
            id, note.name, note.content, note.creationDate, note.updateDate,
            note.waitingTime, note.remindTime, note.projectId, note.contextId
        )
    }

    fun updateSomedayListEdit(id: Int, note: SomedayListNote) {
        val dao = db.somedayListDao()
        dao.update(
            id, note.name, note.content,
            note.creationDate, note.updateDate, note.contextId
        )
    }

    fun updateCalendarListEdit(id: Int, note: CalendarListNote) {
        val dao = db.calendarListDao()
        dao.update(
            id, note.name, note.content, note.creationDate, note.updateDate,
            note.doTime, note.remindTime, note.projectId, note.contextId
        )
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
        val dao = db.nextActionsListDao()
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
        val dao = db.waitingForListDao()
        val id = dao.insert(
            WaitingForNoteDb(
                name = note.name,
                content = note.content,
                creationDate = note.creationDate,
                updateDate = note.updateDate,
                waitingTime = note.waitingTime,
                remindTime = note.remindTime,
                contextId = note.contextId,
                projectId = note.projectId
            )
        )
        return id.toInt()
    }

    fun putSomedayNote(note: SomedayListNote): Int {
        val dao = db.somedayListDao()
        val id = dao.insert(
            SomedayNoteDb(
                name = note.name,
                content = note.content,
                creationDate = note.creationDate,
                updateDate = note.updateDate,
                contextId = note.contextId
            )
        )
        return id.toInt()
    }

    fun putCalendarNote(note: CalendarListNote): Int {
        val dao = db.calendarListDao()
        val id = dao.insert(
            CalendarNoteDb(
                name = note.name,
                content = note.content,
                creationDate = note.creationDate,
                updateDate = note.updateDate,
                doTime = note.doTime,
                remindTime = note.remindTime,
                contextId = note.contextId,
                projectId = note.projectId
            )
        )
        return id.toInt()
    }

    fun deleteInNoteById(id: Int) {
        val dao = db.inListDao()
        dao.deleteById(id)
    }

    fun deleteNextActionsNoteById(id: Int) {
        val dao = db.nextActionsListDao()
        dao.deleteById(id)
    }

    fun deleteWaitingForNoteById(id: Int) {
        val dao = db.waitingForListDao()
        dao.deleteById(id)
    }

    fun deleteSomedayNoteById(id: Int) {
        val dao = db.somedayListDao()
        dao.deleteById(id)
    }

    fun deleteCalendarNoteById(id: Int) {
        val dao = db.calendarListDao()
        dao.deleteById(id)
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
