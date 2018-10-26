package bmstu.ru.todoapp.entities

import java.util.*

data class NextActionsListNote(
    var name: String,
    var content: String? = null,
    var creationDate: Date,
    var updateDate: Date,
    var priority: Int,
    var deadline: Date?,
    var remindeTime: Date?,
    var context: Context?,
    var project: Project?
)