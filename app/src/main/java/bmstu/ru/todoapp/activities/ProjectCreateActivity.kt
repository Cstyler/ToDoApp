package bmstu.ru.todoapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.R
import bmstu.ru.todoapp.entities.Project
import kotlinx.android.synthetic.main.project_edit_form.*

class ProjectCreateActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ProjectCreateActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.project_edit_form)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.create_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.form_edit_ok_button -> {
                val noteName = project_edit_name_text_edit.text.toString()
                if (noteName == "") {
                    Toast.makeText(
                        this,
                        getString(R.string.empty_project_name_toast),
                        Toast.LENGTH_SHORT
                    ).show()
                    return super.onContextItemSelected(item)
                }
                val noteContent = project_edit_content_edit_text.text.toString()
                val note = Project(noteName, noteContent)
                DatabaseLayer.putProject(note)
                finish()
            }
        }
        return super.onContextItemSelected(item)
    }
}
