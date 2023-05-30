package garcia.marco.myinterview.ui.screens.user

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.hilt.android.AndroidEntryPoint
import garcia.marco.myinterview.BuildConfig
import garcia.marco.myinterview.data.remote.request.AddUserRequest
import garcia.marco.myinterview.data.remote.request.Datos
import garcia.marco.myinterview.databinding.ActivityAddUserBinding
import garcia.marco.myinterview.ui.bases.BaseActivity
import garcia.marco.myinterview.ui.utils.DialogUtils
import garcia.marco.myinterview.ui.utils.onTextChangeEvents
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.*
import java.util.*

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AddUserActivity : BaseActivity() {

    private lateinit var binding: ActivityAddUserBinding
    private val viewModel: AddUserViewModel by viewModels()

    companion object {
        const val REQUEST_CODE_PERMISSIONS = 10
    }

    private var photo: Bitmap? = null

    override fun createView() {
        binding = ActivityAddUserBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
    }

    override fun collectFlows() {
        with(binding) {
            with(lifecycleScope) {
                launchWhenResumed {
                    ivBack.setOnClickListener { onBackPressed() }
                }

                launchWhenResumed {
                    ivTakePicture.setOnClickListener {
                        requestPermissions()
                    }
                }

                launchWhenResumed {
                    tietName.onTextChangeEvents.collect {
                        filterData()
                    }
                }

                launchWhenResumed {
                    tietLastName1.onTextChangeEvents.collect {
                        filterData()
                    }
                }

                launchWhenResumed {
                    tietLastName2.onTextChangeEvents.collect {
                        filterData()
                    }
                }

                launchWhenResumed {
                    tietEmail.onTextChangeEvents.collect {
                        filterData()
                    }
                }

                launchWhenResumed {
                    tietBirthdate.setOnClickListener { showDatePickerDialog() }
                }

                launchWhenResumed {
                    tietStreet.onTextChangeEvents.collect {
                        filterData()
                    }
                }

                launchWhenResumed {
                    tietNumber.onTextChangeEvents.collect {
                        filterData()
                    }
                }

                launchWhenResumed {
                    tietCp.onTextChangeEvents.collect {
                        filterData()
                    }
                }

                launchWhenResumed {
                    tietCologne.onTextChangeEvents.collect {
                        filterData()
                    }
                }

                launchWhenResumed {
                    tietDelegation.onTextChangeEvents.collect {
                        filterData()
                    }
                }

                launchWhenResumed {
                    tietState.onTextChangeEvents.collect {
                        filterData()
                    }
                }

                launchWhenResumed {
                    bSave.setOnClickListener {
                        val age = viewModel.getAge(tietBirthdate.text.toString())
                        val image = viewModel.encodeBase64(photo)
                        val datos = Datos(
                            tietStreet.text.toString(),
                            tietNumber.text.toString(),
                            tietCologne.text.toString(),
                            tietDelegation.text.toString(),
                            tietState.text.toString(),
                            tietCp.text.toString(),
                            image
                        )
                        val json = ObjectMapper()
                        val data = json.writeValueAsString(datos)
                        val user = AddUserRequest(
                            tietName.text.toString(),
                            tietLastName1.text.toString(),
                            tietLastName2.text.toString(),
                            age,
                            tietEmail.text.toString(),
                            tietBirthdate.text.toString(),
                            data
                        )
                        Log.d("AddUserActivity", "User: $user")
                        viewModel.addUser(user)
                    }
                }

                launchWhenCreated {
                    viewModel.result.collect {
                        when(it) {
                            is AddUserUiState.Loading -> {
                                loader.show()
                            }
                            is AddUserUiState.Waiting -> { }
                            is AddUserUiState.Error -> {
                                loader.hide()
                                showError(it.throwable)
                            }
                            is AddUserUiState.Success -> {
                                loader.hide()
                                DialogUtils.showOneButtonDialog(
                                    this@AddUserActivity,
                                    "¡Exito!",
                                    "Usuario agregado correctamente."
                                ) { _, dialog ->
                                    dialog.dismiss()
                                    finish()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun requestPermissions(){
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED -> {
                        takePicture()
                    }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                //mostrarMensaje
            }
            else -> {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CODE_PERMISSIONS)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            REQUEST_CODE_PERMISSIONS -> {
                if (grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePicture()
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    private fun takePicture() {
        startForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result: ActivityResult ->
        if (result.resultCode != RESULT_OK) {
            // No se tomo la foto
        } else {
            val bitmap = result.data?.extras?.get("data") as Bitmap
            val uri = viewModel.getImageUri(this, bitmap)
            startCrop(uri)
        }
    }

    private fun saveImage(bitmap: Bitmap, title:String): Uri{
        // Save image to gallery
        val savedImageURL = MediaStore.Images.Media.insertImage(
            contentResolver,
            bitmap,
            title,
            "Imageof$title"
        )
        Log.d("AddUserActivity", "saveImage: ${Uri.parse(savedImageURL)}")
        // Parse the gallery image url to uri
        return Uri.parse(savedImageURL)
    }

    private fun filterData() {
        binding.bSave.isEnabled = checkFields()
    }

    private fun checkFields(): Boolean {
        if (binding.tietName.text?.trim()?.isEmpty() == true) {
            return false
        }
        if (binding.tietLastName1.text?.trim()?.isEmpty() == true) {
            return false
        }
        if (binding.tietLastName2.text?.trim()?.isEmpty() == true) {
            return false
        }
        if (binding.tietEmail.text?.trim()?.isEmpty() == true || !viewModel.validarEmail(binding.tietEmail.text.toString())) {
            return false
        }
        if (binding.tietBirthdate.text?.trim()?.isEmpty() == true) {
            return false
        }
        if (binding.tietStreet.text?.trim()?.isEmpty() == true) {
            return false
        }
        if (binding.tietNumber.text?.trim()?.isEmpty() == true) {
            return false
        }
        if (binding.tietCp.text?.trim()?.isEmpty() == true) {
            return false
        }
        if (binding.tietCologne.text?.trim()?.isEmpty() == true) {
            return false
        }
        if (binding.tietDelegation.text?.trim()?.isEmpty() == true) {
            return false
        }
        if (binding.tietState.text?.trim()?.isEmpty() == true) {
            return false
        }
        return true
    }

    private fun showDatePickerDialog(){
        val datePicker = DatePickerFragment { birthdate ->
            binding.tietBirthdate.setText(birthdate)
        }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // Use the returned uri.
            val uriContent = result.uriContent
            val uriFilePath = result.getUriFilePath(this) // optional usage
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uriContent)
            saveImage(bitmap, BuildConfig.APPLICATION_ID)
            binding.ivTakePicture.setImageURI(uriContent)
        } else {
            // An error occurred.
            val exception = result.error
        }
    }

    private fun startCrop(imageUri: Uri?) {
        cropImage.launch(
            CropImageContractOptions(
                uri = imageUri,
                cropImageOptions = CropImageOptions(),
            ),
        )
    }

}