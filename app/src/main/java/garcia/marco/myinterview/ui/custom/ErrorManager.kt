package garcia.marco.myinterview.ui.custom

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import garcia.marco.myinterview.R
import garcia.marco.myinterview.databinding.DialogErrorBinding

object ErrorManager {

    fun showMessage(context : Context, @StringRes messageId : Int, cancelAction : (DialogInterface) -> Unit = {}) : AlertDialog {
        return showMessage(context, context.resources.getString(messageId), cancelAction)
    }

    fun showMessage(context : Context, _message : String = "", cancelAction : (DialogInterface) -> Unit = {}) : AlertDialog {
        var message = _message
        if (message.isBlank()) {
            message = context.resources.getString(R.string.txt_unknown_error)
        }

        val builder = AlertDialog.Builder(context, R.style.ErrorDialogTheme)
            .setCancelable(true)
            .setOnCancelListener(cancelAction)

        val binding = DialogErrorBinding.inflate(LayoutInflater.from(context)).apply {
            tvMessage.text = message

            builder.setView(root)
        }

        return builder.show().apply {
            binding.bUnderstand.setOnClickListener { cancel() }
        }
    }

}