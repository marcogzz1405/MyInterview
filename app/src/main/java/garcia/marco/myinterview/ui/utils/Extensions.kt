package garcia.marco.myinterview.ui.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

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