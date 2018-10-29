package bmstu.ru.todoapp.entities

import java.util.*

data class NextActionsListNote(
    var name: String,
    var content: String? = null,
    var creationDate: Date,
    var updateDate: Date,
    var priority: Int,
    var deadline: CustomDate?,
    var remindTime: CustomDate?,
    var contextId: Int?,
    var projectId: Int?
)
