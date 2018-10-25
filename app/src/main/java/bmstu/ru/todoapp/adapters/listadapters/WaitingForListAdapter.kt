package bmstu.ru.todoapp.adapters.listadapters

import android.content.Context
import android.util.Log
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.RecyclerViewHolder
import bmstu.ru.todoapp.entities.NoteName

class WaitingForListAdapter(context: Context) : BaseListAdapter(context) {

    companion object {
        private const val TAG = "WaitingForListAdapter"
    }

    override val noteNames: Array<NoteName> = DatabaseLayer.getWaitingForNoteNames()
    override val totalItemCount = noteNames.size


    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.setOnClickListener {
            Log.i(TAG, "Item view clicked")
        }
    }
}

