package bmstu.ru.todoapp.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.format.DateFormat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.R
import bmstu.ru.todoapp.adapters.listadapters.BaseListAdapter
import bmstu.ru.todoapp.entities.MyDate
import bmstu.ru.todoapp.entities.NextActionsListNote
import kotlinx.android.synthetic.main.next_actions_list_edit_form.*
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
class NextActionsListEditActivity : AppCompatActivity(),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    companion object {
        private const val TAG = "NextActListEditActivity"
        const val MAX_PRIORITY = 3
        val PRIORITIES = (1..MAX_PRIORITY).toList()
        val fullDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    }

    private lateinit var note: NextActionsListNote
    private var noteId: Int = 0
    private var remindYear: Int? = null
    private var remindMonth: Int? = null
    private var remindDay: Int? = null
    private var remindHour: Int? = null
    private var remindMinute: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.next_actions_list_edit_form)
        syncLayoutWithDatabase()
        Log.i(TAG, "OnCreate")
    }

    private fun syncLayoutWithDatabase() {
        noteId = intent.getIntExtra(BaseListAdapter.NOTE_ID_KEY, noteId)
        Log.i(TAG, "Note id: $noteId")
        note = DatabaseLayer.getNextActionsListNoteById(noteId)
        next_actions_list_edit_note_name_edit_text.setText(note.name)
        next_actions_list_edit_note_content_edit_text.setText(note.content)

        next_actions_list_edit_creation_date_text_view.text = getString(R.string.edit_form_creation_date)
            .format(fullDateFormat.format(note.creationDate))

        next_actions_list_edit_update_date_text_view.text = getString(R.string.edit_form_update_date)
            .format(fullDateFormat.format(note.updateDate))

        next_actions_list_edit_priority_spinner.setItems(PRIORITIES)
        next_actions_list_edit_priority_spinner.selectedIndex = PRIORITIES.indexOf(note.priority)

        next_actions_list_edit_image_button_date.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, this, year, month, day).show()
        }

        next_actions_list_edit_image_button_time.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            TimePickerDialog(
                this,
                this,
                hour,
                minute,
                DateFormat.is24HourFormat(this)
            ).show()
        }

        note.remindeTime?.let {
            remindYear = it.year
            remindMonth = it.month
            remindDay = it.day
            remindHour = it.hour
            remindMinute = it.minute
            val cal = Calendar.getInstance()
            cal.set(remindYear!!, remindMonth!!, remindDay!!, remindHour!!, remindMinute!!)
            val date = cal.time
            updateTextViewDate(
                next_actions_list_edit_reminde_time_text,
                date,
                fullDateFormat
            )
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        remindYear = year
        remindMonth = month
        remindDay = dayOfMonth
        val calendar = Calendar.getInstance()
        if (validateTime()) {
            calendar.set(year, month, dayOfMonth, remindHour!!, remindMinute!!)
            updateTextViewDate(
                next_actions_list_edit_reminde_time_text,
                calendar.time,
                fullDateFormat
            )
        } else {
            calendar.set(year, month, dayOfMonth)
            updateTextViewDate(
                next_actions_list_edit_reminde_time_text,
                calendar.time,
                dateFormat
            )
        }

    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        if (validateDate()) return
        remindHour = hourOfDay
        remindMinute = minute
        val calendar = Calendar.getInstance()
        calendar.set(remindYear!!, remindMonth!!, remindDay!!, hourOfDay, minute)
        updateTextViewDate(
            next_actions_list_edit_reminde_time_text,
            calendar.time,
            fullDateFormat
        )
    }

    @SuppressLint("SetTextI18n")
    private fun updateTextViewDate(textView: TextView, date: Date, fmt: SimpleDateFormat) {
        textView.text = getString(R.string.next_actions_list_edit_reminde_time_text_format)
            .format(fmt.format(date))
    }

    private fun validateDate(): Boolean {
        if (remindYear == null || remindMonth == null || remindDay == null) {
            return true
        }
        return false
    }

    private fun validateTime(): Boolean {
        if (remindHour != null && remindMinute != null) {
            return true
        }
        return false
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
                var updateFlag = false
                if (noteName != note.name) {
                    note.content = noteContent
                    updateFlag = true
                }
                if (noteContent != note.content) {
                    note.content = noteContent
                    updateFlag = true
                }
                val priority = PRIORITIES[next_actions_list_edit_priority_spinner.selectedIndex]
                if (priority != note.priority) {
                    note.priority = priority
                    updateFlag = true
                }
                if (validateDate() && validateTime() &&
                    ((remindYear != (note.remindeTime)?.year) ||
                            (remindMonth != (note.remindeTime)?.month) ||
                            (remindDay != (note.remindeTime)?.day) ||
                            (remindHour != (note.remindeTime)?.hour) ||
                            (remindMinute != (note.remindeTime)?.minute))
                ) {
                    note.remindeTime = MyDate(
                        remindYear!!,
                        remindMonth!!,
                        remindDay!!,
                        remindHour!!,
                        remindMinute!!
                    )
                    updateFlag = true
                }
                if (updateFlag) {
                    val cal = Calendar.getInstance()
                    note.updateDate = cal.time
                }
                DatabaseLayer.updateNextActionsListEdit(noteId, note)
                finish()
            }
        }
        return super.onContextItemSelected(item)
    }
}
