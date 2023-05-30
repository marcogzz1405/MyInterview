package garcia.marco.myinterview.ui.screens.user

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment(private val listener: (date: String) -> Unit): DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        val dayString = if (day < 10) {
            "0${day}"
        } else {
            day.toString()
        }
        val monthString = if ((month+1) < 10) {
            "0${month+1}"
        } else {
            (month+1).toString()
        }
        val birthdate = "$year/$monthString/$dayString"
        listener.invoke(birthdate)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(activity as Context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day)
        datePickerDialog.datePicker.maxDate = Date().time
        return datePickerDialog
    }
}