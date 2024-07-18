package com.bhyoo.testapp.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import com.bhyoo.testapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val isPermissionGranted = MutableLiveData(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isPermissionGranted.observe(this) { isGrant ->
            if (isGrant) {
                initFragment()
            }
        }

        checkPermission()
    }

    private fun initFragment(){
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, FirstFragment.newInstance())
            .commit()
    }

    private fun checkPermission() {
        val permissionList = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            permissionList.add(Manifest.permission.FOREGROUND_SERVICE)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissionList.add(Manifest.permission.ACTIVITY_RECOGNITION)
        }

        val requestList = ArrayList<String>()

        for (permission in permissionList) {
            if (ActivityCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestList.add(permission)
            }
        }

        isPermissionGranted.value = if (requestList.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, requestList.toTypedArray(), 0)
            false
        } else {
            true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 0) {

            val deniedPermission = ArrayList<String>()

            for ((index, result) in grantResults.withIndex()) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    deniedPermission.add(permissions[index])
                }
            }

            if (deniedPermission.isNotEmpty()) {
                Snackbar.make(
                    binding.root,
                    "Permission denied. Please allow the permission at setting.",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("To Setting") {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }.setAction("Close") {
                    finish()
                }.show()
            } else {
                isPermissionGranted.value = true
            }
        }
    }
}