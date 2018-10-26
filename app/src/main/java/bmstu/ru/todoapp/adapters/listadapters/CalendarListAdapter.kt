package bmstu.ru.todoapp.adapters.listadapters

import android.content.Context
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.entities.NoteName

class CalendarListAdapter(context: Context) : BaseListAdapter(context) {

    companion object {
        private const val TAG = "CalendarListAdapter"
    }

    override val noteNames: Array<NoteName> = DatabaseLayer.getCalendarNoteNames()
    override val totalItemCount = noteNames.size
}

