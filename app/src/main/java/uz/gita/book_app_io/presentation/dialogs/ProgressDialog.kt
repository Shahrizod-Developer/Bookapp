package uz.gita.book_app_io.presentation.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import uz.gita.book_app_io.databinding.DialogProgressBinding

class ProgressDialog(ctx: Context) : Dialog(ctx) {

    private lateinit var binging: DialogProgressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binging = DialogProgressBinding.inflate(layoutInflater)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(binging.root)
        setCancelable(false)
    }

}