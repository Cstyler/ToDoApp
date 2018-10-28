package bmstu.ru.todoapp.adapters.listadapters

import android.content.Context
import android.content.Intent
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.activities.InListEditActivity

class InListAdapter(context: Context) : BaseListAdapter(context) {
    override var noteNames = DatabaseLayer.getInNoteNames()

    override fun startChildActivity(position: Int) {
        val intent = Intent(context, InListEditActivity::class.java).apply {
            putExtra(NOTE_ID_KEY, noteNames[position].id)
        }
        super.startIntent(intent)
    }
}
