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
class NextActionsListEditActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "NextActListEditActivity"
        const val MAX_PRIORITY = 3
        val PRIORITIES = (1..MAX_PRIORITY).toList()
        val fullDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
    }

    private lateinit var note: NextActionsListNote
    private var noteId: Int = 0
    private var remindYear: Int? = null
    private var remindMonth: Int? = null
    private var remindDay: Int? = null
    private var remindHour: Int? = null
    private var remindMinute: Int? = null
    private var deadlineYear: Int? = null
    private var deadlineMonth: Int? = null
    private var deadlineDay: Int? = null
    private var deadlineHour: Int? = null
    private var deadlineMinute: Int? = null

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

        next_actions_list_edit_image_button_remind_date.setOnClickListener {
            val listener = DatePickerDialog.OnDateSetListener { v, y, m, d ->
                onRemindDateSet(v, y, m, d)
            }
            dateOnClick(listener)
        }

        next_actions_list_edit_image_button_remind_time.setOnClickListener {
            val listener = TimePickerDialog.OnTimeSetListener { v, h, m ->
                onRemindTimeSet(v, h, m)
            }
            timeOnClick(listener)
        }

        next_actions_list_edit_image_button_deadline_date.setOnClickListener {
            val listener = DatePickerDialog.OnDateSetListener() { v, y, m, d ->
                onDeadlineDateSet(v, y, m, d)
            }
            dateOnClick(listener)
        }

        next_actions_list_edit_image_button_deadline_time.setOnClickListener {
            val listener = TimePickerDialog.OnTimeSetListener { v, h, m ->
                onDeadlineTimeSet(v, h, m)
            }
            timeOnClick(listener)
        }

        note.remindTime?.let {
            remindYear = it.year
            remindMonth = it.month
            remindDay = it.day
            remindHour = it.hour
            remindMinute = it.minute
            val cal = Calendar.getInstance()
            cal.set(remindYear!!, remindMonth!!, remindDay!!, remindHour!!, remindMinute!!)
            val date = cal.time
            updateTextViewDate(
                next_actions_list_edit_remind_time_text,
                date,
                R.string.next_actions_list_edit_remind_time_text_format
            )
        }
        note.deadline?.let {
            deadlineYear = it.year
            deadlineMonth = it.month
            deadlineDay = it.day
            deadlineHour = it.hour
            deadlineMinute = it.minute
            val cal = Calendar.getInstance()
            cal.set(deadlineYear!!, deadlineMonth!!, deadlineDay!!, deadlineHour!!, deadlineMinute!!)
            val date = cal.time
            updateTextViewDate(
                next_actions_list_edit_deadline_time_text,
                date,
                R.string.next_actions_list_edit_deadline_time_text_format
            )
        }
    }

    private fun timeOnClick(listener: TimePickerDialog.OnTimeSetListener) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        TimePickerDialog(
            this,
            listener,
            hour,
            minute,
            DateFormat.is24HourFormat(this)
        ).show()
    }

    private fun dateOnClick(listener: DatePickerDialog.OnDateSetListener) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, listener, year, month, day).show()
    }

    fun onRemindDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        remindYear = year
        remindMonth = month
        remindDay = dayOfMonth
        remindHour = remindHour ?: 0
        remindMinute = remindMinute ?: 0
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth, remindHour!!, remindMinute!!)
        updateTextViewDate(
            next_actions_list_edit_remind_time_text,
            calendar.time,
            R.string.next_actions_list_edit_remind_time_text_format
        )
    }

    fun onRemindTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        if (!validateRemindDate()) return
        remindHour = hourOfDay
        remindMinute = minute
        val calendar = Calendar.getInstance()
        calendar.set(remindYear!!, remindMonth!!, remindDay!!, hourOfDay, minute)
        updateTextViewDate(
            next_actions_list_edit_remind_time_text,
            calendar.time,
            R.string.next_actions_list_edit_remind_time_text_format
        )
    }

    fun onDeadlineDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        deadlineYear = year
        deadlineMonth = month
        deadlineDay = dayOfMonth
        deadlineHour = deadlineHour ?: 0
        deadlineMinute = deadlineMinute ?: 0
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth, deadlineHour!!, deadlineMinute!!)
        updateTextViewDate(
            next_actions_list_edit_deadline_time_text,
            calendar.time,
            R.string.next_actions_list_edit_deadline_time_text_format
        )
    }

    fun onDeadlineTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        if (!validateDeadlineDate()) return
        deadlineHour = hourOfDay
        deadlineMinute = minute
        val calendar = Calendar.getInstance()
        calendar.set(deadlineYear!!, deadlineMonth!!, deadlineDay!!, hourOfDay, minute)
        updateTextViewDate(
            next_actions_list_edit_deadline_time_text,
            calendar.time,
            R.string.next_actions_list_edit_deadline_time_text_format
        )
    }

    @SuppressLint("SetTextI18n")
    private fun updateTextViewDate(textView: TextView, date: Date, id: Int) {
        textView.text = getString(id)
            .format(fullDateFormat.format(date))
    }

    private fun validateRemindDate(): Boolean {
        if (remindYear == null || remindMonth == null || remindDay == null) {
            return false
        }
        return true
    }

    private fun validateDeadlineDate(): Boolean {
        if (deadlineYear == null || deadlineMonth == null || deadlineDay == null) {
            return false
        }
        return true
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
                var updateFlag = false
                if (noteName != note.name) {
                    note.name = noteName
                    updateFlag = true
                }
                val noteContent = next_actions_list_edit_note_content_edit_text.text.toString()
                if (noteContent != note.content) {
                    note.content = noteContent
                    updateFlag = true
                }
                val priority = PRIORITIES[next_actions_list_edit_priority_spinner.selectedIndex]
                if (priority != note.priority) {
                    note.priority = priority
                    updateFlag = true
                }
                Log.i(TAG, "valdate: ${validateRemindDate()}, rt: ${note.remindTime}")
                if (validateRemindDate() &&
                    (((note.remindTime != null) && remindDateParamsChanged(note.remindTime!!))
                            || (note.remindTime == null))
                ) {
                    note.remindTime = MyDate(
                        remindYear!!,
                        remindMonth!!,
                        remindDay!!,
                        remindHour!!,
                        remindMinute!!
                    )
                    updateFlag = true
                }
                if (validateDeadlineDate() &&
                    (((note.deadline != null) && deadlineDateParamsChanged(note.deadline!!))
                            || (note.deadline == null))
                ) {
                    note.deadline = MyDate(
                        deadlineYear!!,
                        deadlineMonth!!,
                        deadlineDay!!,
                        deadlineHour!!,
                        deadlineMinute!!
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

    private fun remindDateParamsChanged(date: MyDate): Boolean {
        return (remindYear != date.year) ||
                (remindMonth != date.month) ||
                (remindDay != date.day) ||
                (remindHour != date.hour) ||
                (remindMinute != date.minute)
    }

    private fun deadlineDateParamsChanged(date: MyDate): Boolean {
        return (deadlineYear != date.year) ||
                (deadlineMonth != date.month) ||
                (deadlineDay != date.day) ||
                (deadlineHour != date.hour) ||
                (deadlineMinute != date.minute)
    }
}
