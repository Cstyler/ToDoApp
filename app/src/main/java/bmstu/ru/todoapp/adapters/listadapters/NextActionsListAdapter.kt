package bmstu.ru.todoapp.adapters.listadapters

import android.content.Context
import android.content.Intent
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.activities.NextActionsListEditActivity
import bmstu.ru.todoapp.entities.NoteName

class NextActionsListAdapter(context: Context) : BaseListAdapter(context) {

    companion object {
        private const val TAG = "NextActionsListAdapter"
    }

    override fun getData(): Array<NoteName> {
        return DatabaseLayer.getNextActionsNames()
    }

    override fun startChildActivity(position: Int) {
        val intent = Intent(context, NextActionsListEditActivity::class.java).apply {
            putExtra(NOTE_ID_KEY, noteNames[position].id)
        }
        super.startIntent(intent)
    }
}

