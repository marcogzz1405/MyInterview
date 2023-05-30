package garcia.marco.myinterview.ui.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun encodeBase64(value: String): String {
    return Base64.encodeToString(value.toByteArray(), Base64.DEFAULT).replace("\n", "")
}

@ExperimentalCoroutinesApi
val EditText.onTextChangeEvents : Flow<CharSequence>
    get() = callbackFlow {
        val callback = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //empty
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //empty
            }

            override fun afterTextChanged(p0: Editable?) {
                trySend(p0?.toString()?:"")
            }

        }
        addTextChangedListener(callback)
        awaitClose { removeTextChangedListener(callback) }
    }

@ExperimentalCoroutinesApi
val SearchView.onQueryTextListener: Flow<CharSequence> get() = callbackFlow {
    val callback = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }
        override fun onQueryTextChange(newText: String?): Boolean {
            trySend(newText.toString())
            return false
        }
    }
    setOnQueryTextListener(callback)
    awaitClose { removeCallbacks(null) }
}

fun base64ToImage(base64String: String): Bitmap? {
    try {
        val decodedImageBytes: ByteArray = Base64.decode(base64String, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedImageBytes, 0, decodedImageBytes.size)
        return bitmap
    } catch (ex: java.lang.Exception) {
        return null
    }
}