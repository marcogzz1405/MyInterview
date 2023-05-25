package garcia.marco.myinterview.ui.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import garcia.marco.myinterview.R
import garcia.marco.myinterview.databinding.ViewAlertDialogBinding

object DialogUtils {

    fun showSimpleMessage(activity: Activity, message: Int, onConfirm: OnClose? = null) {
        this.showSimpleMessage(activity, activity.getString(message), onConfirm)
    }

    fun showSimpleMessage(activity: Activity, message: String, onConfirm: OnClose? = null) {
        this.showOneButtonDialog(activity, null, message, null) { _, dialog ->
            dialog.dismiss()
            onConfirm?.onConfirm()
        }
    }

    fun showOneButtonDialog(
        activity: Activity,
        title: String? = null,
        message: String? = null,
        titleButton: String? = null,
        onConfirm: OnConfirmClicked
    ) {
        val dialog = Dialog(activity, android.R.style.Theme_Material_Light_Dialog_Alert)
        val layoutInflater = LayoutInflater.from(activity)
        val binding = ViewAlertDialogBinding.inflate(layoutInflater, null, false).apply {
            // Title
            if (title != null) {
                tvTitle.visibility = View.VISIBLE
                tvTitle.text = title
            } else {
                tvTitle.visibility = View.GONE
            }
            // Message
            if (message != null) {
                tvBody.visibility = View.VISIBLE
                tvBody.text = message
            } else {
                tvBody.visibility = View.GONE
            }
            acceptButton.text = titleButton ?: activity.getString(R.string.lbl_accept)
            acceptButton.setOnClickListener { onConfirm.onConfirm(this.root, dialog) }
        }
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(binding.root)
            window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        }.show()
    }

    fun interface OnConfirmClicked {
        fun onConfirm(view: View, dialog: Dialog)
    }

    fun interface OnCancelClicked {
        fun onCancel(view: View, dialog: Dialog)
    }

    fun interface OnClose {
        fun onConfirm()
    }
}