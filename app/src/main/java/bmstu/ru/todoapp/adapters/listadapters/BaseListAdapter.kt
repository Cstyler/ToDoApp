package bmstu.ru.todoapp.adapters.listadapters

import android.content.Context
import bmstu.ru.todoapp.RecyclerViewHolder
import bmstu.ru.todoapp.entities.NoteName

abstract class BaseListAdapter(override val context: Context) :
    BaseAdapter(context) {

    lateinit var noteNames: Array<NoteName>

    init {
        updateData(false)
    }

    abstract fun getData(): Array<NoteName>

    fun updateData(notify: Boolean = true) {
        noteNames = getData()
        if (notify) {
            notifyDataSetChanged()
        }
    }

    companion object {
        private const val TAG = "BaseListAdapter"
        const val NOTE_ID_KEY = "note_id"
    }

    override fun getItemCount(): Int {
        return noteNames.size
    }


    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.setData(noteNames[position].name)
        holder.itemView.setOnClickListener {
            startChildActivity(position)
        }
    }

    abstract fun startChildActivity(position: Int)
}

