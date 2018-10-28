package bmstu.ru.todoapp.adapters.listadapters

import android.content.Context
import android.content.Intent
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.activities.CalendarListEditActivity
import bmstu.ru.todoapp.entities.NoteName

class CalendarListAdapter(context: Context) : BaseListAdapter(context) {

    companion object {
        private const val TAG = "CalendarListAdapter"
    }

    override fun getData(): Array<NoteName> {
        return DatabaseLayer.getCalendarNoteNames()
    }

    override fun startChildActivity(position: Int) {
        val intent = Intent(context, CalendarListEditActivity::class.java).apply {
            putExtra(NOTE_ID_KEY, noteNames[position].id)
        }
        super.startIntent(intent)
    }
}

