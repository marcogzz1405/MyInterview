package garcia.marco.myinterview.ui.screens.user

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import garcia.marco.myinterview.databinding.ActivityAddUserBinding
import garcia.marco.myinterview.ui.bases.BaseActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AddUserActivity : BaseActivity() {

    private lateinit var binding: ActivityAddUserBinding

    private val REQUEST_CODE_PERMISSIONS = 10

    override fun createView() {
        binding = ActivityAddUserBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
    }

    override fun collectFlows() {

    }

    fun requestPermissions(){
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

    }

}