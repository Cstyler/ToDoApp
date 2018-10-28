package bmstu.ru.todoapp.entities

import java.util.*

data class SomedayListNote(
    var name: String,
    var content: String? = null,
    var creationDate: Date,
    var updateDate: Date,
    var contextId: Int?
)
