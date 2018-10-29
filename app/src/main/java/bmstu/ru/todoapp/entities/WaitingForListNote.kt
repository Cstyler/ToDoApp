package bmstu.ru.todoapp.entities

import java.util.*

data class WaitingForListNote(
    var name: String,
    var content: String? = null,
    var creationDate: Date,
    var updateDate: Date,
    var waitingTime: CustomDate?,
    var remindTime: CustomDate?,
    var contextId: Int?,
    var projectId: Int?
)
