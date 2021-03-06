package bmstu.ru.todoapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.R
import bmstu.ru.todoapp.adapters.listadapters.BaseListAdapter
import bmstu.ru.todoapp.adapters.listadapters.BaseListAdapter.Companion.NOTE_ID_KEY
import bmstu.ru.todoapp.entities.*
import kotlinx.android.synthetic.main.in_list_edit_form.*
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
class InListEditActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "InListEditActivity"
        private const val DIALOG_TEXT_SIZE = 20f
    }

    private lateinit var note: InListNote
    private var noteId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.in_list_edit_form)
        syncLayoutWithDatabase()
        Log.i(TAG, "OnCreate")
    }

    private fun syncLayoutWithDatabase() {
        noteId = intent.getIntExtra(BaseListAdapter.NOTE_ID_KEY, noteId)
        note = DatabaseLayer.getInListNoteById(noteId)
        in_list_edit_note_name_edit_text.setText(note.name)
        in_list_edit_note_content_edit_text.setText(note.content)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
        in_list_edit_creation_date_text_view.text = getString(R.string.creation_date)
            .format(dateFormat.format(note.creationDate))
        in_list_edit_update_date_text_view.text = getString(R.string.update_date)
            .format(dateFormat.format(note.updateDate))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.in_list_edit_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.form_edit_ok_button -> {
                val noteName = in_list_edit_note_name_edit_text.text.toString()
                if (noteName == "") {
                    Toast.makeText(
                        this,
                        "Нельзя сохранить заметку с пустым именем",
                        Toast.LENGTH_SHORT
                    ).show()
                    return super.onContextItemSelected(item)
                }
                val noteContent = in_list_edit_note_content_edit_text.text.toString()
                var updateFlag = false
                if (noteContent != note.content) {
                    note.content = noteContent
                    updateFlag = true
                }
                if (noteName != note.name) {
                    note.name = noteName
                    updateFlag = true
                }
                if (updateFlag) {
                    val cal = Calendar.getInstance()
                    note.updateDate = cal.time
                }
                DatabaseLayer.updateInListNote(noteId, note)
                finish()
            }
            R.id.form_edit_delete_button -> {
                Toast.makeText(this, getString(R.string.delete_note_dialog), Toast.LENGTH_SHORT).show()
                DatabaseLayer.deleteInNoteById(noteId)
                finish()
            }
            R.id.form_edit_move_button -> {
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle(getString(R.string.move_note_text))
                val resourceTabTitles = resources.getStringArray(R.array.tab_titles)
                val tabTitlesList = resourceTabTitles.slice(1 until resourceTabTitles.size)

                val arrayAdapter = ArrayAdapter<String>(
                    this,
                    android.R.layout.select_dialog_singlechoice,
                    tabTitlesList
                )

                dialogBuilder.setNegativeButton(
                    getString(R.string.cancel_dialog)
                ) { dialog, _ -> dialog.dismiss() }

                dialogBuilder.setAdapter(arrayAdapter) { _, which ->
                    when (which) {
                        0 -> {
                            showMoveToast(tabTitlesList, which)
                            DatabaseLayer.deleteInNoteById(noteId)
                            val nextActionNote = NextActionsListNote(
                                note.name,
                                note.content,
                                note.creationDate,
                                Calendar.getInstance().time,
                                1,
                                null, null, null, null
                            )
                            val newNoteId = DatabaseLayer.putNextActionNote(nextActionNote)
                            val intent = Intent(
                                this,
                                NextActionsListEditActivity::class.java
                            ).apply {
                                putExtra(NOTE_ID_KEY, newNoteId)
                            }
                            finish()
                            startChildActivity(intent)
                        }
                        1 -> {
                            showMoveToast(tabTitlesList, which)
                            DatabaseLayer.deleteInNoteById(noteId)
                            val waitingForListNote = WaitingForListNote(
                                note.name,
                                note.content,
                                note.creationDate,
                                Calendar.getInstance().time,
                                null, null, null, null
                            )
                            val newNoteId = DatabaseLayer.putWaitingForNote(waitingForListNote)
                            val intent = Intent(
                                this,
                                WaitingForListEditActivity::class.java
                            ).apply {
                                putExtra(NOTE_ID_KEY, newNoteId)
                            }
                            finish()
                            startChildActivity(intent)
                        }
                        2 -> {
                            showMoveToast(tabTitlesList, which)
                            DatabaseLayer.deleteInNoteById(noteId)
                            val somedayListNote = SomedayListNote(
                                note.name,
                                note.content,
                                note.creationDate,
                                Calendar.getInstance().time,
                                null
                            )
                            val newNoteId = DatabaseLayer.putSomedayNote(somedayListNote)
                            val intent = Intent(
                                this,
                                SomedayListEditActivity::class.java
                            ).apply {
                                putExtra(NOTE_ID_KEY, newNoteId)
                            }
                            finish()
                            startChildActivity(intent)
                        }
                        3 -> {
                            showMoveToast(tabTitlesList, which)
                            DatabaseLayer.deleteInNoteById(noteId)
                            val calendarListNote = CalendarListNote(
                                note.name,
                                note.content,
                                note.creationDate,
                                Calendar.getInstance().time,
                                null, null, null, null
                            )
                            val newNoteId = DatabaseLayer.putCalendarNote(calendarListNote)
                            val intent = Intent(
                                this,
                                CalendarListEditActivity::class.java
                            ).apply {
                                putExtra(NOTE_ID_KEY, newNoteId)
                            }
                            finish()
                            startChildActivity(intent)
                        }
                    }
                }
                val dialog = dialogBuilder.show()
                val textView = dialog.findViewById<TextView>(android.R.id.message)
                textView?.textSize = DIALOG_TEXT_SIZE
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun showMoveToast(tabTitlesList: List<String>, which: Int) {
        val listName = tabTitlesList[which]
        Toast.makeText(
            this,
            "Заметка перенесена в список: $listName",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun startChildActivity(intent: Intent) {
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(this, intent, null)
        }
    }
}
