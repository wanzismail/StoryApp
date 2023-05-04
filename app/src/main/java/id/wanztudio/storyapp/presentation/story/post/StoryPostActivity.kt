package id.wanztudio.storyapp.presentation.story.post

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import id.wanztudio.storyapp.R
import id.wanztudio.storyapp.core.base.BaseActivity
import id.wanztudio.storyapp.core.extensions.ifTrue
import id.wanztudio.storyapp.core.extensions.observeData
import id.wanztudio.storyapp.core.extensions.visibleOrInvisible
import id.wanztudio.storyapp.core.helpers.FileHelper.bitmapToFile
import id.wanztudio.storyapp.core.helpers.FileHelper.rotateBitmap
import id.wanztudio.storyapp.core.helpers.FileHelper.uriToFile
import id.wanztudio.storyapp.databinding.StoryPostActivityBinding
import id.wanztudio.storyapp.presentation.camera.CameraActivity
import id.wanztudio.storyapp.presentation.story.list.StoryListActivity
import kotlinx.coroutines.launch
import java.io.File


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

@AndroidEntryPoint
class StoryPostActivity :
    BaseActivity<StoryPostActivityBinding>(StoryPostActivityBinding::inflate) {

    private val viewModel: StoryPostViewModel by viewModels()

    private lateinit var filePhoto: File

    private val fusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    private val imageGalleryIntent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImg: Uri = result.data?.data as Uri
                val myFile = uriToFile(selectedImg, this)
                filePhoto = myFile
                binding.imagePhoto.setImageURI(selectedImg)
                viewModel.setFile(myFile)
            }
        }

    private val launcherIntentCameraX =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == CameraActivity.CAMERA_X_RESULT) {
                val myFile = it.data?.getSerializableExtra(CameraActivity.EXTRA_PICTURE) as File
                val isBackCamera =
                    it.data?.getBooleanExtra(CameraActivity.EXTRA_IS_BACK_CAMERA, true) as Boolean
                val result = rotateBitmap(BitmapFactory.decodeFile(myFile.path), isBackCamera)

                binding.imagePhoto.setImageBitmap(result)
                bitmapToFile(this, result)?.let { file ->
                    filePhoto = file
                    viewModel.setFile(file)
                }
            }
        }

    override fun StoryPostActivityBinding.initialBinding() {
        lifecycleScope.launch {
            viewModel.isPostEnabled.collect {
                buttonAdd.isEnabled = it
            }
        }

        initObserveData()
        initViewsListener()

        getCurrentLocation()
    }

    private fun StoryPostActivityBinding.initViewsListener() {
        buttonCamera.setOnClickListener { checkPermissionCamera() }
        buttonGallery.setOnClickListener { checkPermissionGallery() }
        editDescription.addTextChangedListener {
            viewModel.setDescription(it.toString())
        }
        buttonAdd.setOnClickListener {
            viewModel.postStory()
        }
    }

    private fun StoryPostActivityBinding.initObserveData() {
        observeData(viewModel.onProgress) { state ->
            buttonAdd.isEnabled = !state
            binding.progressBarAdd.visibleOrInvisible(state)
        }

        observeData(viewModel.isUploaded) {
            it.ifTrue {
                StoryListActivity::class.java.start()
            }
        }
        observeData(viewModel.errorMessage) {
            showMessage(it)
        }
    }

    private fun checkPermissionCamera() {
        checkPermission(Manifest.permission.CAMERA) { isGranted ->
            if (isGranted) startTakePhoto()
            else requestCameraPermission.launch(Manifest.permission.CAMERA)
        }
    }

    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            isGranted.ifTrue {
                startTakePhoto()
            }
        }

    private fun startTakePhoto() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun checkPermissionGallery() {
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE) { isGranted ->
            if (isGranted) startGallery()
            else requestStoragePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private val requestStoragePermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            isGranted.ifTrue {
                startGallery()
            }
        }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        imageGalleryIntent.launch(chooser)
    }

    private fun getCurrentLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) && checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                viewModel.setLocation(location?.latitude ?: 0.0, location?.longitude ?: 0.0)
                binding.textLocation.text =
                    String.format(getString(R.string.coordinate_string_format),
                        location?.latitude,
                        location?.longitude)
            }
        } else {
            requestLocationPermission.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION))
        }
    }

    private val requestLocationPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true -> getCurrentLocation()
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true -> getCurrentLocation()
            }
        }

}