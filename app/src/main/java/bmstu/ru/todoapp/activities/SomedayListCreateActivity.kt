package bmstu.ru.todoapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.R
import bmstu.ru.todoapp.entities.ContextName
import bmstu.ru.todoapp.entities.SomedayListNote
import kotlinx.android.synthetic.main.someday_list_edit_form.*
import java.util.*

class SomedayListCreateActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "InListCreateActivity"
    }

    private lateinit var contextNames: List<ContextName>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.someday_list_edit_form)
        setContextSpinner()
    }

    private fun setContextSpinner() {
        contextNames = DatabaseLayer.getContextNames()
        someday_list_edit_context_spinner.setItems(
            listOf(getString(R.string.no_context_spinner_item))
                    + contextNames.map { it.name })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.create_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.form_edit_ok_button -> {
                val noteName = someday_list_edit_note_name_edit_text.text.toString()
                if (noteName == "") {
                    Toast.makeText(
                        this,
                        "Нельзя сохранить заметку с пустым именем",
                        Toast.LENGTH_SHORT
                    ).show()
                    return super.onContextItemSelected(item)
                }
                val noteContent = someday_list_edit_note_content_edit_text.text.toString()
                val time = Calendar.getInstance().time
                val contextSelectedIndex = someday_list_edit_context_spinner.selectedIndex
                val contextId: Int? = if (contextSelectedIndex != 0) {
                    val contextName = contextNames[contextSelectedIndex - 1]
                    contextName.id
                } else {
                    null
                }
                val note = SomedayListNote(noteName, noteContent, time, time, contextId)
                DatabaseLayer.putSomedayNote(note)
                finish()
            }
        }
        return super.onContextItemSelected(item)
    }
}
