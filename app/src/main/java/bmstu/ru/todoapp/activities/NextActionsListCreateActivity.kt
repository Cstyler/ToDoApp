package bmstu.ru.todoapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.R
import bmstu.ru.todoapp.entities.NextActionsListNote
import kotlinx.android.synthetic.main.next_actions_list_edit_form.*
import java.util.*


class NextActionsListCreateActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "NextActListCreateAct"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.next_actions_list_edit_form)

        next_actions_list_edit_priority_spinner.setItems(NextActionsListEditActivity.PRIORITIES)
        Log.i(TAG, "OnCreate")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.form_edit_ok_button -> {
                val noteName = next_actions_list_edit_note_name_edit_text.text.toString()
                if (noteName == "") {
                    Toast.makeText(
                        this,
                        "Нельзя сохранить заметку с пустым именем",
                        Toast.LENGTH_SHORT
                    ).show()
                    return super.onContextItemSelected(item)
                }
                val noteContent = next_actions_list_edit_note_content_edit_text.text.toString()
                val time = Calendar.getInstance().time
                val priority = NextActionsListEditActivity.
                    PRIORITIES[next_actions_list_edit_priority_spinner.selectedIndex]
                val note = NextActionsListNote(
                    noteName,
                    noteContent,
                    time,
                    time,
                    priority,
                    null,
                    null,
                    null,
                    null
                )
                DatabaseLayer.putNextActionsListEdit(note)
                finish()
            }
        }
        return super.onContextItemSelected(item)
    }
}
