package garcia.marco.myinterview.ui.custom

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import garcia.marco.myinterview.R
import garcia.marco.myinterview.databinding.InterviewLoaderBinding

class InterviewLoader(context: Context) : AlertDialog(context, R.style.InterviewStyleLoaderDialog) {

    lateinit var binding : InterviewLoaderBinding

    companion object {

        fun instance(context: Context) : AlertDialog =
            InterviewLoader(context).apply {
                setCancelable(false)
            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InterviewLoaderBinding.inflate(LayoutInflater.from(context)).apply {
            setContentView(root)

        }
    }

    override fun show() {
        super.show()
        binding.ivAnimation.playAnimation()
    }

    override fun hide() {
        super.hide()
        if(isShowing) {
            dismiss()
            binding.ivAnimation.cancelAnimation()
        }

    }

}