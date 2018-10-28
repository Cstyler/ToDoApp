package bmstu.ru.todoapp.entities

import java.util.*

data class WaitingForListNote(
    var name: String,
    var content: String? = null,
    var creationDate: Date,
    var updateDate: Date,
    var waitingTime: MyDate?,
    var remindTime: MyDate?,
    var contextId: Int?,
    var projectId: Int?
)