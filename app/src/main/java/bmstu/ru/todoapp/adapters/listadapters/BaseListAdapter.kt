package bmstu.ru.todoapp.adapters.listadapters

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import bmstu.ru.todoapp.R
import bmstu.ru.todoapp.RecyclerViewHolder
import bmstu.ru.todoapp.activities.*
import bmstu.ru.todoapp.entities.NoteName

abstract class BaseListAdapter(protected val context: Context) :
    RecyclerView.Adapter<RecyclerViewHolder>() {

    abstract val noteNames: Array<NoteName>
    abstract val totalItemCount: Int


    companion object {
        private const val TAG = "InListAdapter"
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

    private fun startIntent(intent: Intent) {
        if (intent.resolveActivity(context.packageManager) != null) {
            startActivity(context, intent, null)
        }
    }

    protected fun startChildActivity(position: Int) {
        Log.i(TAG, "Item$position view clicked")
        val activities = arrayOf(
            InListEditActivity::class.java,
            NextActionsListEditActivity::class.java,
            SomedayListEditActivity::class.java,
            WaitingForListEditActivity::class.java,
            CalendarListEditActivity::class.java
        )
        val intent = Intent(context, activities[position]).apply {
            putExtra(NOTE_ID_KEY, noteNames[position].id)
        }
        startIntent(intent)
    }
}

