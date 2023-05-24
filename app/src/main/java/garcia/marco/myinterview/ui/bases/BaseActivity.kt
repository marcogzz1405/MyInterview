package garcia.marco.myinterview.ui.bases

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import garcia.marco.myinterview.ui.screens.main.MainActivity
import garcia.marco.myinterview.ui.screens.user.AddUserActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    lateinit var loader : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createView()
        collectFlows()
    }

    open fun hideSoftKeyboard(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    abstract fun createView()
    abstract fun collectFlows()

    @ExperimentalCoroutinesApi
    fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    @ExperimentalCoroutinesApi
    fun goToAddUserActivity(){
        val intent = Intent(this, AddUserActivity::class.java)
        startActivity(intent)
    }

}