package uz.gita.book_app_io.presentation.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import uz.gita.book_app_io.databinding.DialogErrorBinding
import uz.gita.book_app_io.utils.config

// Created by Jamshid Isoqov an 10/12/2022
class ErrorDialog(ctx: Context, private val message: String) : Dialog(ctx) {

    private lateinit var binding: DialogErrorBinding

    private var cancelListener:(()->Unit)? =  null

    fun setCancelListener(block:()->Unit){
        cancelListener = block
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogErrorBinding.inflate(layoutInflater)
        config(binding)
        binding.tvErrorMessage.text = message
        binding.btnCancel.setOnClickListener {
            cancelListener?.invoke()
            dismiss()
        }
    }

}