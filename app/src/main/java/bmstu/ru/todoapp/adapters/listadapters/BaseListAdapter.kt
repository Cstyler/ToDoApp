package bmstu.ru.todoapp.adapters.listadapters

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import bmstu.ru.todoapp.R
import bmstu.ru.todoapp.RecyclerViewHolder
import bmstu.ru.todoapp.entities.NoteName

abstract class BaseListAdapter(protected val context: Context) :
    RecyclerView.Adapter<RecyclerViewHolder>() {

    abstract var noteNames: Array<NoteName>
    abstract var totalItemCount: Int

    companion object {
        private const val TAG = "BaseListAdapter"
        const val NOTE_ID_KEY = "note_id"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return totalItemCount
    }


    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.setData(noteNames[position].name)
        holder.itemView.setOnClickListener {
            startChildActivity(position)
        }
    }

    protected fun startIntent(intent: Intent) {
        if (intent.resolveActivity(context.packageManager) != null) {
            startActivity(context, intent, null)
        }
    }

    abstract fun startChildActivity(position: Int)
}

