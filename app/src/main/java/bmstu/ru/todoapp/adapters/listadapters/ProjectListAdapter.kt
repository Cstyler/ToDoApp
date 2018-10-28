package bmstu.ru.todoapp.adapters.listadapters

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.R
import bmstu.ru.todoapp.RecyclerViewHolder
import bmstu.ru.todoapp.activities.ProjectEditActivity

class ProjectListAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerViewHolder>() {

    companion object {
        private const val TAG = "ProjectListAdapter"
        const val NOTE_ID_KEY = "note_id"
    }

    var projectNames = DatabaseLayer.getProjectNames()

    private fun startChildActivity(position: Int) {
        val intent = Intent(context, ProjectEditActivity::class.java).apply {
            putExtra(NOTE_ID_KEY, projectNames[position].id)
        }
        startIntent(intent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return projectNames.size
    }


    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.setData(projectNames[position].name)
        holder.itemView.setOnClickListener {
            startChildActivity(position)
        }
    }

    private fun startIntent(intent: Intent) {
        if (intent.resolveActivity(context.packageManager) != null) {
            ContextCompat.startActivity(context, intent, null)
        }
    }
}
