package bmstu.ru.todoapp.adapters.listadapters

import android.content.Context
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.RecyclerViewHolder

class InListAdapter(context: Context) : BaseListAdapter(context) {
    override val noteNames = DatabaseLayer.getInNoteNames()
    override val totalItemCount = noteNames.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.setOnClickListener {
            startChildActivity(position)
        }
    }
}

