package bmstu.ru.todoapp.adapters.listadapters

import android.content.Context
import android.content.Intent
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.activities.SomedayListEditActivity
import bmstu.ru.todoapp.entities.NoteName

class SomedayListAdapter(context: Context) : BaseListAdapter(context) {

    companion object {
        private const val TAG = "SomedayListAdapter"
    }

    override val noteNames: Array<NoteName> = DatabaseLayer.getSomedayNoteNames()
    override val totalItemCount = noteNames.size

    override fun startChildActivity(position: Int) {
        val intent = Intent(context, SomedayListEditActivity::class.java).apply {
            putExtra(NOTE_ID_KEY, noteNames[position].id)
        }
        super.startIntent(intent)
    }
}

