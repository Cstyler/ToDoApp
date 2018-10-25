package bmstu.ru.todoapp.entities

import java.util.*

data class InListNote(
    var name: String,
    var content: String? = null,
    var creationDate: Date
)
