package bmstu.ru.todoapp.adapters.listadapters

import android.content.Context
import android.content.Intent
import android.util.Log
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.activities.InListEditActivity
import bmstu.ru.todoapp.RecyclerViewHolder

class InListAdapter(context: Context) : BaseListAdapter(context) {

    companion object {
        private const val TAG = "InListAdapter"
        const val NOTE_ID_KEY = "note_id"
    }

    override val noteNames = DatabaseLayer.getInNoteNames()
    override val totalItemCount = noteNames.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.setOnClickListener {
            startChildActivity(position)
        }
    }

    private fun startChildActivity(position: Int) {
        Log.i(TAG, "Item$position view clicked")
        val intent = Intent(context, InListEditActivity::class.java).apply {
            putExtra(NOTE_ID_KEY, noteNames[position].id)
        }
        startIntent(intent)
    }
}

