package bmstu.ru.todoapp.entities

import java.util.*

data class NextActionsListNote(
    var name: String,
    var content: String? = null,
    var creationDate: Date,
    var updateDate: Date,
    var priority: Int,
    var deadline: MyDate?,
    var remindTime: MyDate?,
    var contextId: Int?,
    var projectId: Int?
)
