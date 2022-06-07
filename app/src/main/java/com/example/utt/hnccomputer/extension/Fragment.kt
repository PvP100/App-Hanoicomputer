package com.example.utt.hnccomputer.extension

import android.Manifest
import android.content.Intent
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import com.example.utt.hnccomputer.base.permission.PermissionHelper

fun Fragment.openGallery(requestCode : Int){
    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    startActivityForResult(intent,requestCode)
}

fun Fragment.goToGallery(listener:() -> Unit, permissionHelper: PermissionHelper){
    permissionHelper.withFragment(this)
        .check(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .onSuccess(Runnable {
            listener()
        })
        .onDenied(Runnable {
        })
        .onNeverAskAgain(Runnable {

        })
        .run()
}