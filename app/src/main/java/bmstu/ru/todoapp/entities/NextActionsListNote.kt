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
    var context: Context?,
    var project: Project?
)


data class MyDate(
    var year: Int,
    var month: Int,
    var day: Int,
    var hour: Int,
    var minute: Int
)