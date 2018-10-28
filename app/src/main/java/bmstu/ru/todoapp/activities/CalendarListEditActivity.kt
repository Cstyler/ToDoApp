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
import bmstu.ru.todoapp.entities.*
import kotlinx.android.synthetic.main.calendar_list_edit_form.*
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
class CalendarListEditActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "CalendarLstEditAct"
        val fullDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
    }

    private lateinit var note: CalendarListNote
    private var noteId: Int = 0
    private var remindYear: Int? = null
    private var remindMonth: Int? = null
    private var remindDay: Int? = null
    private var remindHour: Int? = null
    private var remindMinute: Int? = null
    private var doYear: Int? = null
    private var doMonth: Int? = null
    private var doDay: Int? = null
    private var doHour: Int? = null
    private var doMinute: Int? = null
    private lateinit var projectNames: List<ProjectName>
    private lateinit var contextNames: List<ContextName>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar_list_edit_form)
        syncLayoutWithDatabase()
        Log.i(TAG, "OnCreate")
    }

    private fun syncLayoutWithDatabase() {
        noteId = intent.getIntExtra(BaseListAdapter.NOTE_ID_KEY, noteId)
        Log.i(TAG, "Note id: $noteId")
        note = DatabaseLayer.getCalendarListNoteById(noteId)
        calendar_list_edit_note_name_edit_text.setText(note.name)
        calendar_list_edit_note_content_edit_text.setText(note.content)

        calendar_list_edit_creation_date_text_view.text = getString(R.string.creation_date)
            .format(fullDateFormat.format(note.creationDate))

        calendar_list_edit_update_date_text_view.text = getString(R.string.update_date)
            .format(fullDateFormat.format(note.updateDate))

        setProjectSpinner()
        setContextSpinner()

        calendar_list_edit_image_button_remind_date.setOnClickListener {
            val listener = DatePickerDialog.OnDateSetListener { v, y, m, d ->
                onRemindDateSet(v, y, m, d)
            }
            dateOnClick(listener)
        }

        calendar_list_edit_image_button_remind_time.setOnClickListener {
            val listener = TimePickerDialog.OnTimeSetListener { v, h, m ->
                onRemindTimeSet(v, h, m)
            }
            timeOnClick(listener)
        }

        calendar_list_edit_image_button_do_date.setOnClickListener {
            val listener = DatePickerDialog.OnDateSetListener { v, y, m, d ->
                onDoDateSet(v, y, m, d)
            }
            dateOnClick(listener)
        }

        calendar_list_edit_image_button_do_time.setOnClickListener {
            val listener = TimePickerDialog.OnTimeSetListener { v, h, m ->
                onDoTimeSet(v, h, m)
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
                calendar_list_edit_remind_time_text_view,
                date,
                R.string.remind_time_format
            )
        }
        note.doTime?.let {
            doYear = it.year
            doMonth = it.month
            doDay = it.day
            doHour = it.hour
            doMinute = it.minute
            val cal = Calendar.getInstance()
            cal.set(doYear!!, doMonth!!, doDay!!, doHour!!, doMinute!!)
            val date = cal.time
            Log.i(TAG, "$it. ${calendar_list_edit_do_time_text_view.text}")
            updateTextViewDate(
                calendar_list_edit_do_time_text_view,
                date,
                R.string.do_time_format
            )
            Log.i(TAG, "$it. ${calendar_list_edit_do_time_text_view.text}")
        }
    }

    private fun setProjectSpinner() {
        projectNames = DatabaseLayer.getProjectNames()
        calendar_list_edit_project_spinner.setItems(listOf("Нет проекта") + projectNames.map { it.name })
        note.projectId?.let {
            val projectName = ProjectName(it, DatabaseLayer.getProjectNameById(it))
            calendar_list_edit_project_spinner.selectedIndex = projectNames.indexOf(projectName) + 1
        }
    }

    private fun setContextSpinner() {
        contextNames = DatabaseLayer.getContextNames()
        calendar_list_edit_context_spinner.setItems(listOf("Нет контекста") + contextNames.map { it.name })
        note.contextId?.let {
            val contextName = ContextName(it, DatabaseLayer.getContextNameById(it))
            calendar_list_edit_context_spinner.selectedIndex = contextNames.indexOf(contextName) + 1
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

    private fun onRemindDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        remindYear = year
        remindMonth = month
        remindDay = dayOfMonth
        remindHour = remindHour ?: 0
        remindMinute = remindMinute ?: 0
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth, remindHour!!, remindMinute!!)
        updateTextViewDate(
            calendar_list_edit_remind_time_text_view,
            calendar.time,
            R.string.remind_time_format
        )
    }

    private fun onRemindTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        if (!validateRemindDate()) return
        remindHour = hourOfDay
        remindMinute = minute
        val calendar = Calendar.getInstance()
        calendar.set(remindYear!!, remindMonth!!, remindDay!!, hourOfDay, minute)
        updateTextViewDate(
            calendar_list_edit_remind_time_text_view,
            calendar.time,
            R.string.remind_time_format
        )
    }

    private fun onDoDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        doYear = year
        doMonth = month
        doDay = dayOfMonth
        doHour = doHour ?: 0
        doMinute = doMinute ?: 0
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth, doHour!!, doMinute!!)
        updateTextViewDate(
            calendar_list_edit_do_time_text_view,
            calendar.time,
            R.string.do_time_format
        )
    }

    private fun onDoTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        if (!validateDoDate()) return
        doHour = hourOfDay
        doMinute = minute
        val calendar = Calendar.getInstance()
        calendar.set(doYear!!, doMonth!!, doDay!!, hourOfDay, minute)
        updateTextViewDate(
            calendar_list_edit_do_time_text_view,
            calendar.time,
            R.string.do_time_format
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

    private fun validateDoDate(): Boolean {
        if (doYear == null || doMonth == null || doDay == null) {
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
                val noteName = calendar_list_edit_note_name_edit_text.text.toString()
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
                val noteContent = calendar_list_edit_note_content_edit_text.text.toString()
                if (noteContent != note.content) {
                    note.content = noteContent
                    updateFlag = true
                }

                val projectSelectedIndex = calendar_list_edit_project_spinner.selectedIndex
                val projectName = if (projectSelectedIndex == 0) null else projectNames[projectSelectedIndex - 1]
                if (projectName?.id != note.projectId) {
                    note.projectId = projectName?.id
                    updateFlag = true
                }

                val contextSelectedIndex = calendar_list_edit_context_spinner.selectedIndex
                val contextName = if (contextSelectedIndex == 0) null else contextNames[contextSelectedIndex - 1]
                if (contextName?.id != note.contextId) {
                    note.contextId = contextName?.id
                    updateFlag = true
                }

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
                if (validateDoDate() &&
                    (((note.doTime != null) && doTimeParamsChanged(note.doTime!!))
                            || (note.doTime == null))
                ) {
                    note.doTime = MyDate(
                        doYear!!,
                        doMonth!!,
                        doDay!!,
                        doHour!!,
                        doMinute!!
                    )
                    updateFlag = true
                }
                if (updateFlag) {
                    val cal = Calendar.getInstance()
                    note.updateDate = cal.time
                }
                DatabaseLayer.updateCalendarListEdit(noteId, note)
                finish()
            }
            R.id.form_edit_delete_button -> {
                Toast.makeText(this, "Заметка удалена", Toast.LENGTH_SHORT).show()
                DatabaseLayer.deleteCalendarNoteById(noteId)
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

    private fun doTimeParamsChanged(date: MyDate): Boolean {
        return (doYear != date.year) ||
                (doMonth != date.month) ||
                (doDay != date.day) ||
                (doHour != date.hour) ||
                (doMinute != date.minute)
    }
}
