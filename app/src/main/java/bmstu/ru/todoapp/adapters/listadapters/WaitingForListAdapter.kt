package bmstu.ru.todoapp.adapters.listadapters

import android.content.Context
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.entities.NoteName

class WaitingForListAdapter(context: Context) : BaseListAdapter(context) {

    companion object {
        private const val TAG = "WaitingForListAdapter"
    }

    override val noteNames: Array<NoteName> = DatabaseLayer.getWaitingForNoteNames()
    override val totalItemCount = noteNames.size
}

