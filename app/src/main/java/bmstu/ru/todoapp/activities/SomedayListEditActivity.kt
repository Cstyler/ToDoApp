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
import bmstu.ru.todoapp.entities.ContextName
import bmstu.ru.todoapp.entities.SomedayListNote
import kotlinx.android.synthetic.main.someday_list_edit_form.*
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("SimpleDateFormat")
class SomedayListEditActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "InListEditActivity"
    }

    private lateinit var note: SomedayListNote
    private var noteId: Int = 0
    private lateinit var contextNames: List<ContextName>

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.someday_list_edit_form)
        syncLayoutWithDatabase()
        Log.i(TAG, "OnCreate")
    }

    private fun syncLayoutWithDatabase() {
        noteId = intent.getIntExtra(BaseListAdapter.NOTE_ID_KEY, noteId)
        Log.i(TAG, "Note id: $noteId")
        note = DatabaseLayer.getSomedayListNoteById(noteId)
        someday_list_edit_note_name_edit_text.setText(note.name)
        someday_list_edit_note_content_edit_text.setText(note.content)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
        someday_list_edit_creation_date_text_view.text = getString(R.string.creation_date)
            .format(dateFormat.format(note.creationDate))
        someday_list_edit_update_date_text_view.text = getString(R.string.update_date)
            .format(dateFormat.format(note.updateDate))

        setContextSpinner()
    }

    private fun setContextSpinner() {
        contextNames = DatabaseLayer.getContextNames()
        someday_list_edit_context_spinner.setItems(
            listOf(getString(R.string.no_context_spinner_item))
                + contextNames.map { it.name })
        note.contextId?.let {
            val contextName = ContextName(it, DatabaseLayer.getContextNameById(it))
            someday_list_edit_context_spinner.selectedIndex = contextNames.indexOf(contextName) + 1
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_menu, menu)
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
                var updateFlag = false
                if (noteName != note.name) {
                    note.name = noteName
                    updateFlag = true
                }
                if (noteContent != note.content) {
                    note.content = noteContent
                    updateFlag = true
                }

                val contextSelectedIndex = someday_list_edit_context_spinner.selectedIndex
                val contextName = if (contextSelectedIndex == 0) null else contextNames[contextSelectedIndex - 1]
                if (contextName?.id != note.contextId) {
                    note.contextId = contextName?.id
                    updateFlag = true
                }

                if (updateFlag) {
                    val cal = Calendar.getInstance()
                    note.updateDate = cal.time
                }
                DatabaseLayer.updateSomedayListEdit(noteId, note)
                finish()
            }
            R.id.form_edit_delete_button -> {
                Toast.makeText(this, getString(R.string.delete_note_dialog), Toast.LENGTH_SHORT).show()
                DatabaseLayer.deleteInNoteById(noteId)
                finish()
            }
        }
        return super.onContextItemSelected(item)
    }
}
