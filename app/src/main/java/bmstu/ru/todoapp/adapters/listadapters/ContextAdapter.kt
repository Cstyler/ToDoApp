package bmstu.ru.todoapp.adapters.listadapters

import android.content.Context
import android.content.Intent
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.RecyclerViewHolder
import bmstu.ru.todoapp.activities.ContextEditActivity
import bmstu.ru.todoapp.entities.ContextName

class ContextAdapter(override val context: Context) : BaseAdapter(context) {

    companion object {
        private const val TAG = "ContextAdapter"
        const val NOTE_ID_KEY = "note_id"
    }

    lateinit var contextNames: List<ContextName>

    init {
        updateData(false)
    }

    fun updateData(notify: Boolean = true) {
        contextNames = DatabaseLayer.getContextNames()
        if (notify) {
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return contextNames.size
    }


    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.setData(contextNames[position].name)
        holder.itemView.setOnClickListener {
            startChildActivity(position)
        }
    }

    private fun startChildActivity(position: Int) {
        val intent = Intent(context, ContextEditActivity::class.java).apply {
            putExtra(NOTE_ID_KEY, contextNames[position].id)
        }
        startIntent(intent)
    }
}
