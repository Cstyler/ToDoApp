package bmstu.ru.todoapp.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.R
import bmstu.ru.todoapp.adapters.listadapters.BaseListAdapter
import bmstu.ru.todoapp.entities.Project
import kotlinx.android.synthetic.main.project_edit_form.*

@SuppressLint("SimpleDateFormat")
class ProjectEditActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ProjectEditActivity"
    }

    private lateinit var project: Project
    private var projectId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.project_edit_form)
        syncLayoutWithDatabase()
        Log.i(TAG, "OnCreate")
    }

    private fun syncLayoutWithDatabase() {
        projectId = intent.getIntExtra(BaseListAdapter.NOTE_ID_KEY, projectId)
        Log.i(TAG, "Note id: $projectId")
        project = DatabaseLayer.getProjectById(projectId)
        project_edit_name_text_edit.setText(project.name)
        project_edit_content_edit_text.setText(project.content)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.project_edit_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.form_edit_ok_button -> {
                val noteName = project_edit_name_text_edit.text.toString()
                if (noteName == "") {
                    Toast.makeText(
                        this,
                        getString(R.string.no_empty_project_name_toast),
                        Toast.LENGTH_SHORT
                    ).show()
                    return super.onContextItemSelected(item)
                }
                val noteContent = project_edit_content_edit_text.text.toString()
                project.content = noteContent
                project.name = noteName
                DatabaseLayer.updateProject(projectId, project)
                finish()
            }
            R.id.form_edit_delete_button -> {
                Toast.makeText(this, getString(R.string.project_delete_toast), Toast.LENGTH_SHORT).show()
                DatabaseLayer.deleteProjectById(projectId)
                finish()
            }
        }
        return super.onContextItemSelected(item)
    }
}
