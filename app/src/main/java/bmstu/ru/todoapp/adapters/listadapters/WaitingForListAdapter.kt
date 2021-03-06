package bmstu.ru.todoapp.adapters.listadapters

import android.content.Context
import android.content.Intent
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.activities.WaitingForListEditActivity
import bmstu.ru.todoapp.entities.NoteName

class WaitingForListAdapter(context: Context) : BaseListAdapter(context) {

    companion object {
        private const val TAG = "WaitingForListAdapter"
    }

    override fun getData(): Array<NoteName> {
        return DatabaseLayer.getWaitingForNoteNames()
    }

    override fun startChildActivity(position: Int) {
        val intent = Intent(context, WaitingForListEditActivity::class.java).apply {
            putExtra(NOTE_ID_KEY, noteNames[position].id)
        }
        super.startIntent(intent)
    }
}

