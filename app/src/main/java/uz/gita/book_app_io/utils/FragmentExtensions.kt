package uz.gita.book_app_io.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import uz.gita.book_app_io.MainActivity
import uz.gita.book_app_io.presentation.dialogs.ErrorDialog
import uz.gita.book_app_io.presentation.dialogs.MessageDialog



fun Fragment.hideProgress() {
    (requireActivity() as MainActivity).hideProgress()
}

fun Fragment.showProgress() {
    (requireActivity() as MainActivity).showProgress()
}

fun Fragment.showErrorDialog(message: String,block:()->Unit) {
    val dialog = ErrorDialog(requireContext(), message)
    dialog.setCancelListener {
        block.invoke()
    }
    dialog.show()
}

fun Fragment.showMessageDialog(message: String) {
    val dialog = MessageDialog(requireContext(), message)
    dialog.show()
}


fun Activity.showErrorDialog(message: String) {
    val dialog = ErrorDialog(this, message)
    dialog.show()
}

fun Activity.showMessageDialog(message: String) {
    val dialog = MessageDialog(this, message)
    dialog.show()
}

fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.hasPermission(permission: String): Boolean {
    return requireActivity().applicationContext.hasPermission(permission)
}

fun Context.hasPermission(permission: String): Boolean {

    if (permission == Manifest.permission.ACCESS_BACKGROUND_LOCATION &&
        android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q
    ) return true

    return ActivityCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
}

fun Fragment.snackBar(message: String) {
    Snackbar.make(view!!, message, Snackbar.LENGTH_SHORT).show()

}

fun Fragment.hasPermissionApp(permission: List<String>, block: (Int) -> Unit) {
    Dexter.withContext(requireContext()).withPermissions(permission)
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                block.invoke(1)
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                p1: PermissionToken?
            ) {
                block.invoke(-1)
                p1?.continuePermissionRequest()
            }

        }).check()
}
