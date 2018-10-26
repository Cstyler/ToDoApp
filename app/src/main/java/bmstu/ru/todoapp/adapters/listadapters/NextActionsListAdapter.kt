package bmstu.ru.todoapp.adapters.listadapters

import android.content.Context
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.entities.NoteName

class NextActionsListAdapter(context: Context) : BaseListAdapter(context) {

    companion object {
        private const val TAG = "NextActionsListAdapter"
    }

    override val noteNames: Array<NoteName> = DatabaseLayer.getNextActionsNames()
    override val totalItemCount = noteNames.size
}

