package com.example.myappscan

import android.Manifest.permission.CAMERA
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myappscan.databinding.ActivityMainBinding
import android.Manifest.permission_group.CAMERA
import com.google.android.material.snackbar.Snackbar
import java.util.jar.Manifest




private lateinit var layout: View
private lateinit var binding: ActivityMainBinding



class camerapermission : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        layout = binding.scannerView
        setContentView(view)
    }
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.i("Permission: ", "Granted")
            } else {
                Log.i("Permission: ", "Denied")
            }
        }

    fun onClickRequestPermission(view: View) = when {
        ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED -> {
            layout.showSnackbar(
                view,
                getString(R.string.permission_granted),
                Snackbar.LENGTH_INDEFINITE,
                null
            ) {}
        }

        ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            android.Manifest.permission.CAMERA
        ) -> {
            layout.showSnackbar(
                view,
                getString(R.string.permission_required),
                Snackbar.LENGTH_INDEFINITE,
                getString(R.string.ok)
            ) {
                requestPermissionLauncher.launch(
                    android.Manifest.permission.CAMERA
                )
            }
        }

        else -> {
            requestPermissionLauncher.launch(
                android.Manifest.permission.CAMERA
            )
        }
    }
}

fun View.showSnackbar(
    view: View,
    msg: String,
    length: Int,
    actionMessage: CharSequence?,
    action: (View) -> Unit
) {
    val snackbar = Snackbar.make(view, msg, length)
    if (actionMessage != null) {
        snackbar.setAction(actionMessage) {
            action(this)
        }.show()
    } else {
        snackbar.show()
    }
}
