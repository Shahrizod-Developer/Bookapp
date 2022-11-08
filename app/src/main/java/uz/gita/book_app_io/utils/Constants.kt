package uz.gita.book_app_io.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import uz.gita.book_app_io.MainActivity

object Constants {


    fun goToPlayMarket(activity: MainActivity) {
        try {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://payme.uz/5f437be7672f9f51946869a8/100000")
                )
            )
        } catch (e: ActivityNotFoundException) {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://payme.uz/5f437be7672f9f51946869a8/100000")
                )
            )
        }
    }

    fun share(context: Context) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Baby Puzzle")
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "link: https://payme.uz/5f437be7672f9f51946869a8/100000"
        )
        context.startActivity(Intent.createChooser(intent, "UpTodo"))
    }
}