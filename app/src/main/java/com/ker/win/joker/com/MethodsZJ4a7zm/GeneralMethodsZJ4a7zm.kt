package com.ker.win.joker.com.MethodsZJ4a7zm

import android.Manifest
import android.content.Context
import android.util.Base64
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


fun decodeBase64ZJ4a7zm (stringZJ4a7zm: String) = String (Base64.decode(stringZJ4a7zm, Base64.DEFAULT))

fun checkPermissionsZJ4a7zm (contextZJ4a7zm: Context) {
    Dexter.withContext(contextZJ4a7zm)
        .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                p1: PermissionToken?
            ) {

            }
        }).check()
}
