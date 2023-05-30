package garcia.marco.myinterview.ui.screens.user

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.util.Patterns
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import garcia.marco.myinterview.data.remote.request.AddUserRequest
import garcia.marco.myinterview.data.remote.response.AddUserResponse
import garcia.marco.myinterview.data.usescases.AddUserFlow
import garcia.marco.myinterview.domain.OperationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import javax.inject.Inject


@HiltViewModel
class AddUserViewModel @Inject constructor(private val addUserFlow: AddUserFlow): ViewModel() {

    private val _result = MutableStateFlow<AddUserUiState>(AddUserUiState.Waiting)
    val result : StateFlow<AddUserUiState> = _result

    fun addUser(user: AddUserRequest) {
        viewModelScope.launch {
            val flow = addUserFlow.invoke(user)
            _result.value = AddUserUiState.Loading
            flow.collect {
                when(it) {
                    is OperationResult.Success -> _result.value = AddUserUiState.Success(it.data!!)
                    is OperationResult.Error -> _result.value = AddUserUiState.Error(it.throwable)
                }
            }
        }
    }

    fun getAge(birthdate: String): Int{
        val format = SimpleDateFormat("YYYY/MM/DD")
        val dateBirth = format.parse(birthdate)
        val date = Date(System.currentTimeMillis())
        val dif = date.time - dateBirth.time
        val seconds = dif/1000
        val minutes = seconds/60
        val hours = minutes/60
        val days = hours/24
        val years = days/365
        return years.toInt()
    }

    fun encodeBase64(bitmap: Bitmap?): String {
        val baos = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun rotateBitmap(bitmap: Bitmap, file: File?): Bitmap {
        var exifInterface: ExifInterface? = null
        try {
            exifInterface = ExifInterface(file?.absolutePath?:"")
        } catch (ex: IOException) {

        }
        val orientation = exifInterface?.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> {
                matrix.setRotate(90F)
            }
            ExifInterface.ORIENTATION_ROTATE_180 -> {
                matrix.setRotate(180F)
            }
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    fun validarEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.getContentResolver(),
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }

}

sealed class AddUserUiState {
    data class Success(val addUserResponse: AddUserResponse): AddUserUiState()
    data class Error(val throwable: Throwable): AddUserUiState()
    object Waiting : AddUserUiState()
    object Loading : AddUserUiState()
}